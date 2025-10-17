package com.makebk.logger.vo


import cn.hutool.json.JSONUtil
import com.makebk.common.vo.IUser
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.Serializable
import java.lang.reflect.Method

/**
 *
 * 　@类   名：AccessLoggerInfo
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:27 AM
 *
 */
class AccessLoggerInfo : Serializable {

    var requestId: String? = null

    /**
     * 访问的操作
     */
    var action: String? = null

    /**
     * 描述
     */
    var describe: String? = null

    /**
     * 访问对应的java方法
     */
    var method: Method? = null

    /**
     * 访问对应的java类
     */
    var target: Class<*>? = null

    /**
     * 请求的参数,参数为java方法的参数而不是http参数,key为参数名,varue为参数值.
     */
    var parameters: Map<String, Any>? = null

    /**
     * 请求者ip地址
     */
    var ip: String? = null

    /**
     * 请求的url地址
     */
    var url: String? = null

    /**
     * http 请求头集合
     */
    var httpHeaders: Map<String, String>? = null

    /**
     * http 请求方法, GET,POST...
     */
    var httpMethod: String? = null

    /**
     * 响应结果,方法的返回值
     */
    var response: Any? = null

    /**
     * 请求时间戳
     *
     * @see System.currentTimeMillis
     */
    var requestTime: Long = 0

    /**
     * 响应时间戳
     *
     * @see System.currentTimeMillis
     */
    var responseTime: Long = 0

    /**
     * 异常信息,请求对应方法抛出的异常
     */
    var exception: Throwable? = null

    var user: IUser? = null

}