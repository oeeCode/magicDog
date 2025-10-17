package com.makebk.common.util

import cn.hutool.core.util.DesensitizedUtil
import cn.hutool.core.util.DesensitizedUtil.DesensitizedType
import cn.hutool.core.util.StrUtil
import cn.hutool.log.LogFactory
import com.makebk.common.annotations.Sensitive
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field
import java.util.*

object EntityUtils {
    var logger = LogFactory.get(this::class.java)

    fun desensitized(entity: Any) {
        ReflectionUtils.doWithFields(entity::class.java, {
            ReflectionUtils.makeAccessible(it)
            val value: Any = it.get(entity)
            doDesensitized(entity, it, value)
        })
    }

    fun doDesensitized(entity: Any, field: Field, value: Any) {
        if (String::class.java.isAssignableFrom(field.type)
            && field.isAnnotationPresent(Sensitive::class.java)
            && !Objects.isNull(value)
        ) {
            val sensitive = field.getAnnotation(Sensitive::class.java)
            val desensitizedValue: String = getDesensitizedValue(sensitive, value.toString())
            field.set(entity, desensitizedValue)
        }
    }

    fun getDesensitizedValue(sensitive: Sensitive, orginalValue: String): String {
        val desensitizedType: DesensitizedType = sensitive.strategy
        val desensitizedValue = if (desensitizedType == DesensitizedType.USER_ID && sensitive.useDFA) {
            val replaceChar: String =
                StrUtil.repeat(sensitive.dfaReplaceChar, sensitive.dfaReplaceCharRepeatCount)
            SensitiveWordUtils.replaceSensitiveWord(orginalValue, replaceChar)
        } else {
            DesensitizedUtil.desensitized(orginalValue, desensitizedType)
        }

        return desensitizedValue
    }

}