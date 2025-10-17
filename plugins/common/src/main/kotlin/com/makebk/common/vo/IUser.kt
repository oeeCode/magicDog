package com.makebk.common.vo

import com.makebk.common.constant.BaseEnums

open class IUser() {
    var id: Int? = null
    var token: String? = null
    var username: String? = null
    var groupIds: String? = null
    var type: String? = null
    var initPwd: BaseEnums.Status? = null

    constructor(id: Int?, username: String?, type: String?) : this() {
        this.id = id
        this.username = username
        this.type = type
    }


    constructor(id: Int?, token: String?, username: String?, type: String?) : this() {
        this.id = id
        this.token = token
        this.username = username
        this.type = type
    }

    constructor(
        id: Int?,
        token: String?,
        username: String?,
        groupIds: String?,
        type: String?,
        initPwd: BaseEnums.Status?,
    ) : this() {
        this.id = id
        this.token = token
        this.groupIds = groupIds
        this.username = username
        this.type = type
        this.initPwd = initPwd
    }

}