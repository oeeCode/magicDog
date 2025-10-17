package com.makebk.logger.events

import cn.hutool.core.annotation.Alias
import cn.hutool.json.JSONUtil
import com.makebk.logger.vo.AccessLoggerInfo
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * 　@类   名：AccessLoggerEvent
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:29 AM
 *
 */
class LogEvent {
    enum class EventType {
        Api, Error, Usual
    }

    /**
     * 服务ID
     */

    @Alias("服务ID")
    var serviceId: String? = null

    /**
     * 服务器名
     */
    @Alias("服务器名")
    var serverHost: String? = null

    /**
     * 服务器IP地址
     */

    @Alias("服务器IP地址")
    var serverIp: String? = null

    /**
     * 服务器环境
     */
    @Alias("服务器环境")
    var env: String? = null

    /**
     * 日志类型
     */
    @Alias("日志类型")
    var type: EventType = EventType.Api

    /**
     * 日志标题
     */
    @Alias("日志标题")
    var title: String? = null

    /**
     * 操作方式
     */
    @Alias("操作方式")
    var method: String? = null

    /**
     * 请求URI
     */
    @Alias("请求UR")
    var requestUri: String? = null

    /**
     * 用户代理
     */
    @Alias("用户代理")
    var userAgent: String? = null

    /**
     * 操作IP地址
     */
    @Alias("操作IP地址")
    var remoteIp: String? = null

    /**
     * 方法类
     */
    @Alias("方法类")
    var methodClass: String? = null

    /**
     * 方法名
     */
    @Alias("方法名")
    var methodName: String? = null

    /**
     * 操作提交的数据
     */
    @Alias("操作提交的数据")
    var params: String? = null

    /**
     * 执行时间
     */
    @Alias("执行时间")
    var time: String? = null

    /**
     * 创建者
     */
    @Alias("请求用户")
    var createBy: String? = null

    /**
     * 创建者昵称（用户名）
     */
    @Alias("创建者昵称（用户名）")
    var createByName: String? = null

    /**
     * 请求时间
     */
    @Alias("发生时间")
    var createTime: Date? = null

    /**
     * 描述
     */
    @Alias("描述")
    var describe: String? = null

    /**
     * 响应结果
     */
    @Alias("响应结果")
    var response: String? = toResponseStr()
    fun toResponseStr(): String? {
        if (response != null) {
            return JSONUtil.formatJsonStr(JSONUtil.toJsonStr(response))
        }
        return null
    }
    /**
     * 堆栈
     */
    @Alias("堆栈")
    var stackTrace: String? = null

    /**
     * 异常名
     */
    @Alias("异常名")
    var exceptionName: String? = null

    /**
     * 异常信息
     */
    @Alias("异常信息")
    var message: String? = null

    /**
     * 错误行数
     */
    @Alias("错误行数")
    var lineNumber: Int? = null

    /**
     * 日志级别
     */
    @Alias("日志级别")
    var logLevel: String? = null

    /**
     * 日志业务request id
     */
    @Alias("request_id")
    var id: String? = null

    /**
     * 日志数据
     */
    @Alias("日志数据")
    var logData: String? = null
}

class AccessLoggerAfterEvent(val logger: AccessLoggerInfo)
class AccessLoggerBeforeEvent(val logger: AccessLoggerInfo)