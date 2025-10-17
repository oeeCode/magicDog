package com.makebk.logger.components

import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import com.makebk.logger.events.LogEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component


/**
 * @Package com.makebk.logger.components
 * @author GME
 * @date 2024/1/14 21:44
 * @version V1.0
 * @Copyright © 2014-2023 码克布克网络工作室
 */
@Component
class LogEventListener {

    /** logger */
    private val logger = LogFactory.get(javaClass)

    @Async
    @EventListener
    fun eventLogListener(event: LogEvent) {
        val log = JSONUtil.toJsonStr(event)
        when (event.type) {
            LogEvent.EventType.Error -> {
                logger.error(JSONUtil.formatJsonStr(log))
            }

            LogEvent.EventType.Usual -> {
                logger.debug(JSONUtil.formatJsonStr(log))
            }

            else -> {
                logger.debug(JSONUtil.formatJsonStr(log))
            }
        }
    }
}