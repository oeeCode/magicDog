package com.makebk.domain.wechat.interfaces.callback

import com.makebk.domain.wechat.entits.Body
import com.makebk.domain.wechat.infrastructure.handler.MsgHandler
import com.makebk.sdk.wechat.WechatSDkFactory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("wxMsg")
class WxMsgCallbackController {

    @RequestMapping("callback/general")
    suspend fun callbackQeneral(
        @RequestBody body: Body
    ) {
        MsgHandler.msgEvent(body)
    }

    @RequestMapping("callback/qrcode")
    suspend fun callbackQrcode(
        @RequestBody body: Body
    ) {
        WechatSDkFactory.loadingScan = true
    }


    @RequestMapping("callback/public")
    suspend fun callbackPublic(
        @RequestBody body: Body
    ) {
        MsgHandler.msgEvent(body)
    }
}