package com.makebk.sdk.wechat

import java.io.Serializable


class TxtMsg(
    var wxid: String,
    var content: String,
) : Serializable {

    var atlist: List<String>? = null

    constructor(
        wxid: String,
        content: String,
        atlist: List<String>,
    ) : this(wxid, content) {
        this.atlist = atlist
    }
}

class ImgMsg(
    var wxid: String,
    var content: String,
) : Serializable {

    var atlist: List<String>? = null

    constructor(
        wxid: String,
        content: String,
        atlist: List<String>,
    ) : this(wxid, content) {
        this.atlist = atlist
    }
}

class FileMsg(
    var wxid: String,
    var content: String,
) : Serializable {

    var atlist: List<String>? = null

    constructor(
        wxid: String,
        content: String,
        atlist: List<String>,
    ) : this(wxid, content) {
        this.atlist = atlist
    }
}


//-------------------------------------
class Resp {
    var code: Int? = null
    var msg: String? = null
    var data: Any? = null
}

class CheckloginResp {
    var status: String? = null
    var wxid: String? = null
}

class UserInfoResp {
    var customAccount: String? = null //  微信号
    var city: String? = null //  城市
    var country: String? = null // 国家
    var dbKey: String? = null // 数据库加密key，可解密读取数据库
    var nickname: String? = null //  微信昵称
    var phone: String? = null // 手机号
    var phoneSystem: String? = null //  手机系统
    var privateKey: String? = null // 私钥
    var profilePicture: String? = null //  头像
    var province: String? = null // 省
    var publicKey: String? = null // 公钥
    var signature: String? = null // 个性签名
    var wxid: String? = null
}

class ContactsResps {
    var contacts: List<ContactsResp>? = null
}

class ContactsResp {
    var customAccount: String? = null //  微信号
    var nickname: String? = null //  昵称
    var v3: String? = null //
    var note: String? = null //  备注
    var notePinyin: String? = null // 备注拼音首字母大写
    var notePinyinAll: String? = null //  备注拼音全
    var pinyin: String? = null //  昵称拼音首字母大写
    var pinyinAll: String? = null //  昵称拼音全
    var profilePicture: String? = null // 头像
    var profilePictureSmall: String? = null // 小头像
    var reserved1: String? = null //
    var type: String? = null //
    var verifyFlag: String? = null //
    var wxid: String? = null //
}

class DbContactsResps {
    var contacts: List<DbContactsResp>? = null
}

class DbContactsResp {
    var Alias: String? = null // 微信号
    var NickName: String? = null // 昵称
    var EncryptUserName: String? = null //v3
    var Remark: String? = null //备注
    var RemarkPYInitial: String? = null //： 备注拼音首字母大写
    var RemarkQuanPin: String? = null // 备注拼音全
    var PYInitial: String? = null // 昵称拼音首字母大写
    var QuanPin: String? = null // 昵称拼音全
    var profilePicture: String? = null //头像
    var profilePictureSmall: String? = null //小头像
    var type: String? = null //
    var UserName: String? = null //
    var wxid: String? = null //
}