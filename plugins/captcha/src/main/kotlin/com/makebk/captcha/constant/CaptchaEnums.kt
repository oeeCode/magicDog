package com.makebk.captcha.constant

import com.fasterxml.jackson.annotation.JsonValue

object CaptchaEnums {

    enum class CaptchaCode
        (
        @JsonValue val code: String,
        @JsonValue val reason: String,
    ) {
        S("0000", "成功"),
        E_1("9999", "服务器内部异常"),
        E_2("0011", "参数不能为空"),
        E_3("6110", "验证码已失效，请重新获取"),
        E_4("6111", "验证失败"),
        E_5("6112", "获取验证码失败,请联系管理员"),
        UNKNOWN("-1", "未知错误"),
        ;

        companion object {
            fun findByCode(code: String) = entries.firstOrNull {
                it.code == code
            } ?: UNKNOWN
        }
    }

}