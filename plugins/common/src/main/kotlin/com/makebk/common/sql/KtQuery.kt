package com.makebk.common.sql

import cn.hutool.core.util.ReflectUtil
import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.makebk.common.constant.BaseEnums
import com.makebk.common.constant.Constant
import com.makebk.common.filter.SQLFilter

/**
 *  KtQuery
 *  @description：TODO
 *  @author：gme 2023/3/6｜13:30
 *  @version：0.0.1
 */
class KtQuery<T>() {
    private val comma: String = ","
    private var `class`: Class<T>? = null

    constructor(`class`: Class<T>) : this() {
        this.`class` = `class`
    }


    fun getPage(): IPage<T> = getPage(Query(null))

    fun getPage(query: Query): IPage<T> {
        val page: Page<T> = Page(query.page ?: 1, query.limit ?: 15)
        if (query.ascs != null && query.ascs!!.trim().isNotEmpty()) {
            StrUtil.split(query.ascs, comma).forEach {
                val asc = if (query.alias != null) {
                    "${query.alias}.${SQLFilter.sqlInject(it)}"
                } else {
                    "${SQLFilter.sqlInject(it)}"
                }
                page.addOrder(OrderItem.asc(asc))
            }
        }
        if (query.descs != null && query.descs!!.trim().isNotEmpty()) {
            StrUtil.split(query.descs, comma).forEach {
                val desc = if (query.alias != null) {
                    "${query.alias}.${SQLFilter.sqlInject(it)}"
                } else {
                    "${SQLFilter.sqlInject(it)}"
                }
                page.addOrder(OrderItem.desc(desc))
            }
        }
        return page
    }

    fun getQueryWrapper(): QueryWrapper<T> = QueryWrapper<T>()

    fun getQueryWrapper(t: T): QueryWrapper<Any> =
        getQueryWrapper(t, null)

    fun getQueryWrapper(t: T, alias: String?): QueryWrapper<Any> =
        QueryWrapper<Any>(t, alias)


    fun getQueryWrapper(params: MutableMap<String, Any>?): QueryWrapper<T> =
        getQueryWrapper(params, Query(null))

    fun getQueryWrapper(params: MutableMap<String, Any>?, alias: String?): QueryWrapper<T> =
        getQueryWrapper(params, Query(alias = alias))

    fun excludeQueryKeywords(params: MutableMap<String, Any>?): MutableMap<String, Any>? {
        val exclude = arrayOf("page", "limit", "ascs", "descs", "columns", "alias", "needTotal")
        exclude.forEach { params?.remove(it) }
        return params
    }

    fun getListQueryWrapper(params: MutableMap<String, Any>?, query: Query?): QueryWrapper<T> {
        if (query != null) {
            query.page = null
            query.limit = null
        }
        return getQueryWrapper(params, query)
    }

    fun minPage(page: Long): Long {
        return if (page < 0) 0 else page
    }

    fun getQueryWrapper(params: MutableMap<String, Any>?, query: Query?): QueryWrapper<T> {
        val qw = SqlKeyword.buildCondition(excludeQueryKeywords(params), query?.alias)
        if (query != null) {
            qw.orderByAsc(query.ascs != null && query.ascs!!.trim().isNotEmpty(), StrUtil.split(query.ascs, comma))
            qw.orderByDesc(query.descs != null && query.descs!!.trim().isNotEmpty(), StrUtil.split(query.descs, comma))
            if (query.page != null && query.limit != null) {
                qw.last("limit ${minPage(query.page!! - 1) * query.limit!!}, ${query.limit}")
            }
        }
        return addDefaultStatus((qw as QueryWrapper<T>), query?.alias)
    }


    private fun addDefaultStatus(qw: QueryWrapper<T>, alias: String?): QueryWrapper<T> {
        if (`class` != null && ReflectUtil.hasField(`class`, Constant.FIELD_IS_DEL)) {
            qw.and {
                it.eq(SqlKeyword.getKey(alias, "is_del"), BaseEnums.IsDel.否.code)
            }
        }
        return qw
    }
}