package com.makebk.logger.aop

import com.makebk.logger.vo.AccessLoggerInfo

/**
 *
 * 　@类   名：AccessLoggerListener
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/21 11:31 AM
 *
 */
interface AccessLoggerListener {
    /**
     * 当产生访问日志时,将调用此方法.注意,此方法内的操作应尽量设置为异步操作,否则可能影响请求性能
     *
     * @param loggerInfo 产生的日志信息
     */
    fun onLogger(loggerInfo: AccessLoggerInfo?)

    fun onLogBefore(loggerInfo: AccessLoggerInfo?)
}