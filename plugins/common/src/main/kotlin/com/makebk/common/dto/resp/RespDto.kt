package com.makebk.common.dto.resp

import cn.hutool.json.JSONUtil
import com.makebk.common.constant.ReturnCode
import com.makebk.common.dto.UIDto

/**
 * 　@类   名：RespDto
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 2:03 PM
 */

data class RespDto<T>(
    var code: Int = 200,
    var message: String? = null,
    var body: T? = null,
) : UIDto {

    fun body(body: T?): RespDto<T> {
        this.body = body
        return this
    }

    fun message(message: String?): RespDto<T> {
        this.message = message
        return this
    }

    fun code(code: Int): RespDto<T> {
        this.code = code
        return this
    }

    fun success(): RespDto<T> {
        return RespDto<T>(ReturnCode.SUCCESS.toInt(), "操作成功")
    }

    fun success(body: T?): RespDto<T> {
        val respDto = RespDto<T>(ReturnCode.SUCCESS.toInt(), "操作成功")
        respDto.body(body)
        return respDto
    }

    fun success(body: T?, message: String): RespDto<T> {
        val respDto = RespDto<T>(ReturnCode.SUCCESS.toInt(), message)
        respDto.body(body)
        return respDto
    }

    fun fail(): RespDto<T> {
        return RespDto<T>(ReturnCode.ERROR.toInt(), "操作失败")
    }

    fun fail(message: String?): RespDto<T> {
        return RespDto<T>(ReturnCode.ERROR.toInt(), message)
    }

    fun fail(body: T?, message: String?): RespDto<T> {
        val respDto = RespDto<T>(ReturnCode.ERROR.toInt(), message)
        respDto.body(body)
        return respDto
    }

    fun isSuccess(): Boolean {
        return code == 200
    }

    companion object {
        fun success(): RespDto<Any> {
            return RespDto(ReturnCode.SUCCESS.toInt(), "操作成功")
        }

        fun <T> success(body: T?): RespDto<T> {
            val respDto = RespDto<T>(ReturnCode.SUCCESS.toInt(), "操作成功")
            respDto.body(body)
            return respDto
        }

        fun <T> success(body: T?, message: String): RespDto<T> {
            val respDto = RespDto<T>(ReturnCode.SUCCESS.toInt(), message)
            respDto.body(body)
            return respDto
        }

        fun <T> fail(): RespDto<T> {
            return RespDto(ReturnCode.ERROR.toInt(), "操作失败")
        }

        fun <T> fail(message: String?): RespDto<T> {
            return RespDto(ReturnCode.ERROR.toInt(), message)
        }

        fun <T> fail(body: T?, message: String?): RespDto<T> {
            val respDto = RespDto<T>(ReturnCode.ERROR.toInt(), message)
            respDto.body(body)
            return respDto
        }
    }

    override fun toString(): String {
        return "{code:${this.code},message:${this.message},body:${JSONUtil.toJsonStr(this.body)}}"
    }
}