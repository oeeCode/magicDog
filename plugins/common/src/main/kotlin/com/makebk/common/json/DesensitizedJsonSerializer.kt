package com.makebk.common.json

import cn.hutool.core.util.DesensitizedUtil
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.ContextualSerializer
import com.makebk.common.annotations.Sensitive
import com.makebk.common.util.EntityUtils
import java.util.*

class DesensitizedJsonSerializer : JsonSerializer<String>(), ContextualSerializer {
    var sensitive: Sensitive? = null

    companion object {
        private var desensitizedType: DesensitizedUtil.DesensitizedType? = null
        var noSensitiveFlag: ThreadLocal<Boolean> = ThreadLocal()
    }

    override fun serialize(value: String, gen: JsonGenerator, serializers: SerializerProvider) {
        val sensitiveFlag = noSensitiveFlag
        if ("${sensitiveFlag.get()}".uppercase() == "TRUE") {
            gen.writeString(value);
        } else {
            gen.writeString(EntityUtils.getDesensitizedValue(sensitive!!, value));
        }
    }

    override fun createContextual(prov: SerializerProvider, property: BeanProperty): JsonSerializer<*> {
        sensitive = property.getAnnotation(Sensitive::class.java)
        return if (Objects.nonNull(sensitive) && Objects.equals(String::class.java, property.type.rawClass)) {
            desensitizedType = sensitive!!.strategy
            this
        } else
            prov.findValueSerializer(property.getType(), property)
    }
}