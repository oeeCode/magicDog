package com.makebk.common.base.service

import cn.hutool.core.util.ReflectUtil
import cn.hutool.log.LogFactory
import com.baomidou.mybatisplus.core.toolkit.StringPool
import com.baomidou.mybatisplus.extension.service.IService
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.makebk.common.base.mapper.IBaseMapper
import com.makebk.common.constant.Constant
import com.makebk.common.exception.BaseException
import com.makebk.common.handler.UserContextHandler
import com.makebk.common.sql.KtQuery
import com.makebk.common.sql.Query
import com.makebk.common.util.CoroutineUtil
import com.makebk.common.vo.Page
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.apache.ibatis.session.ResultHandler
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable
import java.lang.reflect.ParameterizedType

/**
 * 基础服务接口，扩展了 MyBatis-Plus 的 IService 接口
 * 提供了协程支持的数据库操作方法
 */
interface IBaseService<T> : IService<T> {

    /**
     * 根据ID获取实体
     * @param id 实体ID
     * @return 实体对象或null
     */
    suspend fun fetchById(id: Serializable?): T?

    /**
     * 根据ID列表删除实体
     * @param ids 逗号分隔的ID字符串
     * @return 操作是否成功
     */
    suspend fun removeByIds(ids: String): Boolean

    /**
     * 根据参数查询记录数量
     * @param params 查询参数
     * @return 记录数量
     */
    suspend fun queryCount(
        params: MutableMap<String, Any>,
    ): Long

    /**
     * 查询所有记录
     * @return 记录列表
     */
    suspend fun queryList(): List<T>

    /**
     * 根据参数查询记录列表
     * @param params 查询参数
     * @return 记录列表
     */
    suspend fun queryList(params: MutableMap<String, Any>): List<T>

    /**
     * 根据参数和结果处理器查询记录
     * @param params 查询参数
     * @param resultHandler 结果处理器
     */
    suspend fun queryList(params: MutableMap<String, Any>, resultHandler: ResultHandler<T>)

    /**
     * 根据参数和查询对象查询记录列表
     * @param params 查询参数
     * @param query 查询对象
     * @return 记录列表
     */
    suspend fun queryList(params: MutableMap<String, Any>, query: Query): List<T>

    /**
     * 根据参数、查询对象和结果处理器查询记录
     * @param params 查询参数
     * @param query 查询对象
     * @param resultHandler 结果处理器
     */
    suspend fun queryList(params: MutableMap<String, Any>, query: Query, resultHandler: ResultHandler<T>)

    /**
     * 根据参数和查询对象分页查询
     * @param params 查询参数
     * @param query 查询对象
     * @return 分页结果
     */
    suspend fun queryPage(params: MutableMap<String, Any>, query: Query): Page<T>

    /**
     * 根据别名、参数和查询对象分页查询
     * @param alias 表别名
     * @param params 查询参数
     * @param query 查询对象
     * @return 分页结果
     */
    suspend fun queryPage(
        alias: String?,
        params: MutableMap<String, Any>,
        query: Query,
    ): Page<T>

}

/**
 * 基础服务实现类，提供了协程支持的数据库操作方法
 * 继承了 MyBatis-Plus 的 ServiceImpl 并实现了 IBaseService 接口
 *
 * @param Mapper 类型
 * @param T 实体类型
 */
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = [Exception::class])
open class IBaseServiceImpl<M : IBaseMapper<T>, T> :
    ServiceImpl<M, T>(),
    IBaseService<T> {

    protected val logger = LogFactory.get(javaClass)
    protected val scope = CoroutineUtil.VirtualThread

    override suspend fun fetchById(id: Serializable?): T? {
        return withContext(
            scope.coroutineContext + UserContextHandler.context()
        ) {
            getById(id)
        }
    }

    override suspend fun removeByIds(ids: String): Boolean {
        if (ids.isBlank()) {
            return false
        }

        val idList = ids.split(StringPool.COMMA.toRegex())
            .mapNotNull {
                it.trim().toLongOrNull()
            }

        return if (idList.isNotEmpty()) {
            removeByIds(idList)
        } else {
            false
        }
    }

    override suspend fun queryCount(params: MutableMap<String, Any>): Long {
        return withContext(
            scope.coroutineContext + UserContextHandler.context()
        ) {
            baseMapper.queryListQueryWrapper_mpCount(
                KtQuery<T>(getTClass()).getQueryWrapper(
                    params, Constant.BASE_SCOPE_FIELD
                )
            )
        }
    }

    override suspend fun queryList(): List<T> = queryList(mutableMapOf(), Query(alias = Constant.BASE_SCOPE_FIELD))

    override suspend fun queryList(params: MutableMap<String, Any>): List<T> =
        queryList(params, Query(alias = Constant.BASE_SCOPE_FIELD))

    override suspend fun queryList(
        params: MutableMap<String, Any>,
        resultHandler: ResultHandler<T>,
    ) = queryList(params, Query(alias = Constant.BASE_SCOPE_FIELD), resultHandler)

    override suspend fun queryList(
        params: MutableMap<String, Any>,
        query: Query,
    ): List<T> {
        return withContext(
            scope.coroutineContext + UserContextHandler.context()
        ) {
            executeQueryList(params, query)
        }
    }

    override suspend fun queryList(
        params: MutableMap<String, Any>,
        query: Query,
        resultHandler: ResultHandler<T>,
    ) {
        executeQueryList(params, query, resultHandler)
    }

    /**
     * 执行查询列表的内部方法
     * 根据是否存在自定义查询方法选择不同的查询方式
     *
     * @param params 查询参数
     * @param query 查询对象
     * @param resultHandler 结果处理器（可选）
     * @return 查询结果列表
     */
    private suspend fun executeQueryList(
        params: MutableMap<String, Any>,
        query: Query,
        resultHandler: ResultHandler<T>? = null,
    ): List<T> {
        return withContext(CoroutineUtil.IO.coroutineContext + UserContextHandler.context()) {
            val hasQueryListMethod = haveMethod("queryListQueryWrapper")
            if (hasQueryListMethod) {
                val thatQuery = cloneQuery(query)
                    .buildAlias(Constant.BASE_SCOPE_FIELD)
                    .removeLimit()
                val queryListWrapper = KtQuery(getTClass()).getQueryWrapper(params, thatQuery)

                if (resultHandler != null) {
                    baseMapper.queryListQueryWrapper(queryListWrapper, resultHandler)
                    emptyList()
                } else {
                    baseMapper.queryListQueryWrapper(queryListWrapper)
                }
            } else {
                val thatQuery = cloneQuery(query)
                    .removeLimit()
                    .removeAlias()
                val queryListWrapper = KtQuery<T>(getTClass()).getQueryWrapper(params, thatQuery)

                if (resultHandler != null) {
                    baseMapper.selectList(queryListWrapper, resultHandler)
                    emptyList()
                } else {
                    baseMapper.selectList(queryListWrapper)
                }
            }
        }
    }

    override suspend fun queryPage(
        params: MutableMap<String, Any>,
        query: Query,
    ): Page<T> = queryPage(Constant.BASE_SCOPE_FIELD, params, query)

    override suspend fun queryPage(
        alias: String?,
        params: MutableMap<String, Any>,
        query: Query,
    ): Page<T> {
        return coroutineScope {
            val thatQuery = query.buildAlias(alias)
            // 使用克隆的查询对象进行计数，确保不包含分页限制
            val countQuery = cloneQuery(thatQuery).removeLimit().removeAlias()
            val queryCountWrapper = KtQuery(getTClass()).getQueryWrapper(params, countQuery)
            val queryListWrapper = KtQuery(getTClass()).getQueryWrapper(params, thatQuery)

            val countDeferred =
                scope.async(scope.coroutineContext + UserContextHandler.context()) {
                    withContext(CoroutineUtil.IO.coroutineContext + UserContextHandler.context()) {
                        baseMapper.queryListQueryWrapper_mpCount(queryCountWrapper)
                    }
                }
            val rowsDeferred =
                scope.async(scope.coroutineContext + UserContextHandler.context()) {
                    withContext(CoroutineUtil.IO.coroutineContext + UserContextHandler.context()) {
                        baseMapper.queryListQueryWrapper(queryListWrapper)
                    }
                }

            // 使用协程同时获取总数和数据，处理异常
            val count = try {
                countDeferred.await()
            } catch (e: Exception) {
                logger.error(e, "Error occurred while counting records: ${e.message}")
                throw BaseException("Failed to count records: ${e.message}")
            }

            val rows = try {
                rowsDeferred.await()
            } catch (e: Exception) {
                logger.error(e, "Error occurred while fetching records: ${e.message}")
                throw BaseException("Failed to fetch records: ${e.message}")
            }

            Page(
                rows,
                count.toInt(),
                thatQuery.limit ?: 15,
                thatQuery.page ?: 1
            )
        }
    }


    private fun getTClass() = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<T>

    fun haveMethod(methodName: String): Boolean {
        val methods = ReflectUtil.getMethodNames(mapperClass)
        return methods.any {
            it.contains(methodName)
        }
    }

    private fun cloneQuery(query: Query): Query =
        Query(query.alias).apply {
            page = query.page ?: 1
            limit = query.limit ?: 15
            ascs = query.ascs
            descs = query.descs
            columns = query.columns
            alias = query.alias
            needTotal = query.needTotal
        }
}