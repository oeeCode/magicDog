package com.makebk.common.base.ctrl

import com.makebk.common.annotations.NoSensitive
import com.makebk.common.annotations.Permission
import com.makebk.common.annotations.RepeatSubmit
import com.makebk.common.base.service.IBaseService
import com.makebk.common.dto.req.ReqDto
import com.makebk.common.dto.resp.RespDto
import com.makebk.common.handler.UserContextHandler
import com.makebk.common.sql.Query
import com.makebk.common.util.CoroutineUtil
import com.makebk.common.vo.IUser
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.async
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.Serializable
abstract class IBaseController<S : IBaseService<E>, E : Any> {

    @Autowired
    lateinit var service: S

    /**
     * 根据主键查询记录。
     *
     * @param id 主键值，用于查询特定记录。
     * @return 返回一个包含查询结果的RespDto对象。
     */
    @NoSensitive
    @GetMapping("/{id}")
    @Operation(summary = "根据主键查询")
    @Permission(":find")
    open suspend fun find(@PathVariable id: Serializable): RespDto<Any> = RespDto.success(service.fetchById(id))


    /**
     * 新增或修改记录。
     *
     * @param dto 包含要保存的数据的请求DTO。
     * @return 返回一个包含操作结果的RespDto对象。
     */
    @PostMapping
    @Permission(":submit")
    @Operation(summary = "新增或修改")
    @RepeatSubmit(limitType = RepeatSubmit.Companion.Type.PARAM, lockTime = 1500L)
    open fun submit(@Valid @RequestBody dto: ReqDto<E>): RespDto<Any> {
        return RespDto.success(service.saveOrUpdate(dto.body))
    }

    @PostMapping("submitBatch")
    @Permission(":submitBatch")
    @Operation(summary = "批量新增或修改")
    @RepeatSubmit(limitType = RepeatSubmit.Companion.Type.PARAM, lockTime = 1500L)
    open suspend fun submitBatch(@Valid @RequestBody dto: ReqDto<List<E>>): RespDto<Any> {
        val user = UserContextHandler.currentUser ?: IUser()
        val scope = CoroutineUtil.VirtualThread
        val rel = dto.body.map {
            scope.async(UserContextHandler.current.asContextElement(value = user)) {
                service.saveOrUpdate(it)
            }.await()
        }.all { it }
        return RespDto.success(rel)
    }

    /**
     * 删除记录。
     *
     * @param ids 要删除的记录的主键值，多个主键值用逗号分隔。
     * @return 返回一个包含操作结果的RespDto对象。
     */
    @DeleteMapping("/remove/{ids}")
    @Operation(summary = "删除")
    @Permission(":remove")
    @RepeatSubmit(limitType = RepeatSubmit.Companion.Type.PARAM, lockTime = 3000L)
    open suspend fun remove(@PathVariable ids: String): RespDto<Boolean> =
        RespDto.success(service.removeByIds(ids))


    /**
     * 查询所有记录。
     *
     * @param params 查询参数。
     * @param query 查询对象，包含分页和排序信息。
     * @return 返回一个包含查询结果的RespDto对象。
     */
    @GetMapping("/list")
    @Permission(":list")
    @Operation(summary = "查询所有")
    open suspend fun list(@RequestParam params: MutableMap<String, Any>, query: Query): RespDto<Any> =
        RespDto.success(service.queryList(params, query))


    /**
     * 分页查询数据。
     *
     * @param params 查询参数。
     * @param query 查询对象，包含分页和排序信息。
     * @return 返回一个包含分页查询结果的RespDto对象。
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询数据")
    @Permission(":page")
    open suspend fun page(@RequestParam params: MutableMap<String, Any>, query: Query): RespDto<Any> =
        RespDto.success(service.queryPage(params, query))
}