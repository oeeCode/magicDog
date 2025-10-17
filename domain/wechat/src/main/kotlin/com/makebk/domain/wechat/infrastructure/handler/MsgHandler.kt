package com.makebk.domain.wechat.infrastructure.handler

import cn.hutool.extra.spring.SpringUtil
import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import com.makebk.common.util.CoroutineUtil
import com.makebk.domain.wechat.entits.Body
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel

object MsgHandler {

    val logger = LogFactory.get(javaClass)
    val scope = CoroutineUtil.VirtualThread
    suspend fun msgEvent(body: Body) =
        coroutineScope {
            logger.debug("消息数量: ${body.total}")
            logger.debug("wxid: ${body.wxid}")
            val chatModel = SpringUtil.getBean(VertexAiGeminiChatModel::class.java)
            body.data?.map { d ->
                logger.debug(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(d)))
                scope.async {
                    val result = chatModel.call(d.strContent)
                    logger.debug(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(result)))
//                    WechatSDkFactory.sendtxtmsg(
//                        TxtMsg(
//                            body.wxid!!,
//                            ""
//                        )
//                    )
                }
            }?.awaitAll()
        }

}



