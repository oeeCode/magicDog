package com.makebk.common.permission

import java.util.*
import java.util.function.Predicate

/**
 *
 * 　@类   名：DataPermissionType
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/20 4:25 PM
 *
 */
enum class DataPermissionType(var type: Int, var desc: String) {

    ALL(1, "全部可见"),
    OWN(2, "本人可见"),
    OWN_TENANT_CHILD(3, "所在机构及子级可见"),
    OWN_TENANT(4, "所在机构可见"),
    OWN_CITY(6, "权限范围城市可见"),
    SYS_CUSTOM(5, "自定义"),
    ;

    companion object {
        fun of(type: Int?): DataPermissionType {
            return Arrays.stream(DataPermissionType.values())
                .filter(Predicate { value: DataPermissionType ->
                    value.type == type
                }).findFirst().orElse(DataPermissionType.ALL)
        }
    }
}