package com.makebk.common.annotations

import cn.hutool.core.util.DesensitizedUtil
import com.baomidou.mybatisplus.core.toolkit.StringPool
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.makebk.common.json.DesensitizedJsonSerializer
import java.lang.annotation.Inherited

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
@MustBeDocumented
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizedJsonSerializer::class)
annotation class Sensitive(
    val strategy: DesensitizedUtil.DesensitizedType = DesensitizedUtil.DesensitizedType.USER_ID,
    /**
     * 是否使用dfa算法
     * @return
     */
    val useDFA: Boolean = false,
    /**
     * dfa敏感字符替换，默认替换成 "*"
     * @return
     */
    val dfaReplaceChar: String = StringPool.ASTERISK,
    /**
     * dfa敏感字符替换次数
     * @return
     */
    val dfaReplaceCharRepeatCount: Int = 1,
)
