package com.makebk.sdk.exception

import cn.hutool.json.JSONUtil
import com.fasterxml.jackson.annotation.JsonProperty

class SdkError {
    @JsonProperty("errcode")
    var errorCode = 0

    @JsonProperty("errmsg")
    var errorMsg: String? = null
    var json: String? = null

    companion object {
        fun fromJson(json: String?): SdkError {
            val sdkError: SdkError = JSONUtil.toBean(json,SdkError::class.java)
            sdkError.json = (json)
            return sdkError
        }
    }
}