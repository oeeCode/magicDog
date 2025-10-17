package com.makebk.common.components

import cn.hutool.crypto.SecureUtil
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import com.makebk.common.annotations.RepeatSubmit
import com.makebk.common.constant.SecurityConstants
import com.makebk.common.exception.BaseException
import com.makebk.common.handler.UserContextHandler
import com.makebk.common.permission.JwtProperties
import com.makebk.common.util.WebUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.concurrent.TimeUnit

@Aspect
@Component
class RepeatSubmitAspect(
    val jwtProperties: JwtProperties,
    var redissonClient: RedissonClient,
) {

    private val logger: Log = LogFactory.get(javaClass)

    @Pointcut("@annotation(repeatSubmit)")
    fun pointCutNoRepeatSubmit(repeatSubmit: RepeatSubmit) {
    }

    @Around(value = "pointCutNoRepeatSubmit(repeatSubmit)")
    fun around(joinPoint: ProceedingJoinPoint, repeatSubmit: RepeatSubmit): Any? {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        val user = UserContextHandler.currentUser
        val userId = user?.id ?: 0L
        val token = user?.token ?: SecurityConstants.SALT
        val flag = when (repeatSubmit.limitType) {
            RepeatSubmit.Companion.Type.TOKEN -> {
                val lockTime: Long = repeatSubmit.lockTime
                val ipAddr = WebUtil.getIpAddr(request)
                val key = "${SecurityConstants.REPEAT_SUBMIT}${SecureUtil.md5("${ipAddr}${token}")}"
                val lock = redissonClient.getLock(key)
                lock.tryLock(0, lockTime, TimeUnit.MILLISECONDS)
            }

            else -> {
                val lockTime: Long = repeatSubmit.lockTime
                val ipAddr = WebUtil.getIpAddr(request)
                val methodSignature = joinPoint.signature as MethodSignature
                val method = methodSignature.method
                val className = method.declaringClass.name
                val key =
                    "${SecurityConstants.REPEAT_SUBMIT}${SecureUtil.md5("${ipAddr}${className}${method}${userId}")}"
                val lock = redissonClient.getLock(key)
                lock.tryLock(0, lockTime, TimeUnit.MILLISECONDS)
            }
        }
        if (!flag) {
            logger.error("操作过快，请稍候重试")
            throw BaseException("操作失败，操作过快，请稍候重试！")
        }
        return joinPoint.proceed()
    }

}