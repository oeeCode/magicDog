package com.makebk.common.vo

import com.makebk.common.constant.BaseEnums
import java.io.Serializable

open class JwtRespBody() : Serializable {

    var userId: Long? = null
    var username: String? = null
    var token: String? = null
    var refreshToken: String? = null
    var groupIds: String? = null
    var avatar: String? = null
    var initPwd: BaseEnums.Status? = null

    constructor(
        userId: Long?,
        username: String?,
        token: String?,
        refreshToken: String?,
        groupIds: String?,
    ) : this() {
        this.userId = userId
        this.username = username
        this.token = token
        this.refreshToken = refreshToken
        this.groupIds = groupIds
    }

    constructor(
        userId: Long?,
        username: String?,
        token: String?,
        refreshToken: String?,
        groupIds: String?,
        initPwd: BaseEnums.Status?,
    ) : this() {
        this.userId = userId
        this.username = username
        this.token = token
        this.refreshToken = refreshToken
        this.groupIds = groupIds
        this.initPwd = initPwd
    }

}


