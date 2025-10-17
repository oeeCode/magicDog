package com.makebk.sdk.wechat

import com.makebk.common.exception.BaseException
import java.io.IOException
import java.util.*

object WechatEndpoint {
    private var endpoints: Properties? = null
    private val PROP: String = "wechat-endpoint.properties"

    @Synchronized
    private fun loadJhProperties(prop: String) {
        if (endpoints == null) {
            try {
                val properties = Properties()
                val inputStream =
                    WechatEndpoint::class.java.classLoader.getResourceAsStream(prop)
                properties.load(inputStream)
                endpoints = properties
            } catch (e: IOException) {
                throw BaseException(999, "cannot find resource $prop from classpath.")
            }
        }
    }

    fun get(key: String?): String {
        loadJhProperties(PROP)
        return endpoints!!.getProperty(key)
    }
}