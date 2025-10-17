package com.makebk.common.handler

import cn.hutool.log.LogFactory
import com.makebk.common.constant.Constant
import com.makebk.common.constant.ReturnCode
import com.makebk.common.dto.resp.RespDto
import com.makebk.common.exception.BaseException
import io.swagger.v3.oas.annotations.Hidden
import jakarta.annotation.Priority
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

abstract class GlobalExceptionHandler {

    private val log = LogFactory.get(this::class.java)

    @ExceptionHandler(BaseException::class)
    @ResponseStatus(HttpStatus.OK)
    fun businessException(e: BaseException): RespDto<String>? {
        log.error(e.message, e)
        //Sentry.captureException(e)
        return RespDto.fail(e.message)
    }

    @ExceptionHandler(java.lang.RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun runtimeException(e: java.lang.RuntimeException): RespDto<String>? {
        log.error(e.message, e)
        //Sentry.captureException(e)
        return RespDto.fail(e.message)
    }

    @ExceptionHandler(
        java.lang.IllegalArgumentException::class
    )
    @ResponseStatus(HttpStatus.OK)
    fun illegalArgumentException(e: java.lang.IllegalArgumentException): RespDto<String>? {
        log.error(e.message, e)
        //Sentry.captureException(e)
        val resp: RespDto<String> = RespDto.fail(e.message)
        resp.code(ReturnCode.INVALID_PARAMTER.toInt())
        return resp

    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.OK)
    fun exceptionHandler(e: MethodArgumentNotValidException): RespDto<String>? {
        log.error(e.message, e)
        //Sentry.captureException(e)
        val resp: RespDto<String> = RespDto.fail(e.bindingResult.fieldError?.defaultMessage)
        resp.code(ReturnCode.INVALID_PARAMTER.toInt())
        return resp
    }
}