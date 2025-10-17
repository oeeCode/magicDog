package com.makebk.domain.wechat.infrastructure.handler

import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import com.makebk.common.util.CoroutineUtil
import com.makebk.domain.wechat.entits.Body
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

object MsgHandler {

    val logger = LogFactory.get(javaClass)
    val scope = CoroutineUtil.VirtualThread
    suspend fun msgEvent(body: Body) =
        coroutineScope {
            logger.debug("消息数量: ${body.total}")
            logger.debug("wxid: ${body.wxid}")

            body.data?.map { d ->
                scope.async {
                    logger.debug(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(d)))
                }
            }?.awaitAll()
        }

}



