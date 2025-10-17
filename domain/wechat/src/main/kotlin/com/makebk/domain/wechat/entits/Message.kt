package com.makebk.domain.wechat.entits

import com.fasterxml.jackson.annotation.JsonProperty

class Body {

    @JsonProperty("total")
    var total: Long? = null

    var data: List<Message>? = null

    @JsonProperty("wxid")
    var wxid: String? = null
}

class Message {
    @JsonProperty("BytesExtra")
    var bytesExtra: String? = null

    @JsonProperty("BytesTrans")
    var bytesTrans: String? = null

    @JsonProperty("CompressContent")
    var compressContent: String? = null

    @JsonProperty("CreateTime")
    var createTime: String? = null

    @JsonProperty("DisplayContent")
    var displayContent: String? = null

    @JsonProperty("FlagEx")
    var flagEx: String? = null

    @JsonProperty("IsSender")
    @JvmField
    var isSender: String? = null

    @JsonProperty("MsgSequence")
    var msgSequence: String? = null

    @JsonProperty("MsgServerSeq")
    var msgServerSeq: String? = null

    @JsonProperty("MsgSvrID")
    var msgSvrId: String? = null

    @JsonProperty("Reserved0")
    var reserved0: String? = null

    @JsonProperty("Reserved1")
    var reserved1: String? = null

    @JsonProperty("Reserved2")
    var reserved2: String? = null

    @JsonProperty("Reserved3")
    var reserved3: String? = null

    @JsonProperty("Reserved4")
    var reserved4: String? = null

    @JsonProperty("Reserved5")
    var reserved5: String? = null

    @JsonProperty("Reserved6")
    var reserved6: String? = null

    @JsonProperty("Sender")
    var sender: String? = null

    @JsonProperty("Sequence")
    var sequence: String? = null

    @JsonProperty("Status")
    var status: String? = null

    @JsonProperty("StatusEx")
    var statusEx: String? = null

    @JsonProperty("StrContent")
    var strContent: String? = null

    @JsonProperty("StrTalker")
    var strTalker: String? = null

    @JsonProperty("SubType")
    var subType: String? = null

    @JsonProperty("TalkerId")
    var talkerId: String? = null

    @JsonProperty("Type")
    var type: String? = null
    var localId: String? = null
}
