package com.makebk.domain.wechat.infrastructure.task

import com.makebk.sdk.wechat.WechatSDkFactory
import cn.hutool.log.LogFactory
import com.makebk.common.util.CoroutineUtil
import kotlinx.coroutines.launch
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration(proxyBeanMethods = false)
@EnableScheduling
class BotTask {

    private val logger = LogFactory.get(javaClass)
    private val scope = CoroutineUtil.VirtualThread

    @Async
    @Scheduled(cron = "0/30 * * * * ?")
    fun orderTask() {
        scope.launch {
            WechatSDkFactory.checkService()
        }
    }

}