package com.makebk.common.constant

import com.baomidou.mybatisplus.annotation.EnumValue
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * 　@类   名：BaseEnums
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/18
 *
 */
object BaseEnums {

    enum class Method {
        ADD, REMOVE, DELETE, UPDATE, OTHER
    }

    enum class ResourceType(@EnumValue val code: Int, val component: String, @JsonValue val text: String) {
        目录(0, "basic", "目录"),
        菜单(1, "self", "菜单"),
        按钮(2, "self", "按钮");

        companion object {

            fun findByCode(code: Int?) =
                entries.firstOrNull {
                    it.code == code
                } ?: 菜单
        }
    }

    enum class Status(@EnumValue val code: Int, @JsonValue val text: String, val type: String) {
        停用(0, "停用", "warning"),
        正常(1, "正常", "success");

        companion object {
            @JsonCreator
            fun getStatus(code: Int?) =
                entries.firstOrNull {
                    it.code == code
                } ?: 正常
        }
    }

    /**
     * 是否删除
     */
    enum class IsDel(@EnumValue val code: Int) {
        否(0),
        是(1);

        companion object {
            fun getStatus(code: Int?) =
                entries.firstOrNull {
                    it.code == code
                } ?: 否
        }
    }
}



