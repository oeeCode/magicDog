package com.makebk.security.handler

import cn.hutool.log.LogFactory
import com.auth0.jwt.exceptions.TokenExpiredException
import com.makebk.common.constant.Constant
import com.makebk.common.constant.ReturnCode
import com.makebk.common.dto.resp.RespDto
import com.makebk.common.handler.GlobalExceptionHandler
import io.swagger.v3.oas.annotations.Hidden
import jakarta.annotation.Priority
import org.apache.shiro.UnavailableSecurityManagerException
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.authz.UnauthenticatedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@Hidden
@Priority(-1)
@RestControllerAdvice(Constant.PACKAGE)
class SecurityGlobalExceptionHandler : GlobalExceptionHandler() {
    private val log = LogFactory.get(this::class.java)

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(
        TokenExpiredException::class
    )
    fun tokenExpiredException(e: TokenExpiredException): RespDto<String>? {
        log.error(e.message, e)
        //Sentry.captureException(e)
        val resp: RespDto<String> = RespDto.fail(e.message)
        resp.code(ReturnCode.UNAUTHENTICATED_ERROR.toInt())
        return resp
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(
        value = [
            UnauthenticatedException::class,
            UnavailableSecurityManagerException::class,
        ]
    )
    fun defaultErrorHandler(e: Exception): RespDto<String>? {
        log.error(e.message, e)
        //Sentry.captureException(e)
        val resp: RespDto<String> = RespDto.fail("请求失败,当前用户未登录或长时间未操作令牌已过期，请重新登录")
        resp.code(ReturnCode.UNAUTHENTICATED_ERROR.toInt())
        return resp
    }


    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = [AuthorizationException::class])
    fun defaultErrorHandler(e: AuthorizationException): RespDto<String>? {
        log.error(e.message, e)
        //Sentry.captureException(e)
        val resp: RespDto<String> = RespDto.fail("操作失败,当前用户没有获得操作权限")
        resp.code(ReturnCode.UNAUTHENTICATED_ERROR.toInt())
        return resp
    }
}