package com.makebk.common.util

import cn.hutool.core.util.StrUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.*

/**
 *
 * 　@类   名：WebUtil
 * 　@描   述：
 * 　@作   者：gme
 * 　@版   本：1.0
 * 　@创建时间：2022/10/18
 *
 */
class WebUtil {

    companion object {
        val ipHeaders = arrayOf(
            "X-Requested-For",
            "X-Forwarded-For",
            "X-Real-IP",
            "HTTP_CLIENT_IP",
            "Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "WL-Proxy-Client-IP"
        )

        fun queryStringToMap(queryString: String?, charset: String?): Map<String, String>? {
            return try {
                val map: MutableMap<String, String> = HashMap()
                val decode = URLDecoder.decode(queryString, charset).split("&")
                for (keyValue in decode) {
                    val kv = keyValue.split("[=]".toRegex(), limit = 2).toTypedArray()
                    map[kv[0]] = if (kv.size > 1) kv[1] else ""
                }
                map
            } catch (e: UnsupportedEncodingException) {
                throw UnsupportedOperationException(e)
            }
        }

        fun getParameters(request: HttpServletRequest): Map<String, String> {
            val parameters: MutableMap<String, String> = HashMap()
            val enumeration: Enumeration<*> = request.parameterNames
            while (enumeration.hasMoreElements()) {
                val name = enumeration.nextElement().toString()
                parameters[name] = request.getParameter(name)
            }
            return parameters
        }

        fun getHttpServletRequest(): HttpServletRequest? {
            return try {
                (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        fun getHeaders(request: HttpServletRequest): Map<String, String> {
            val map: MutableMap<String, String> = LinkedHashMap()
            val enumeration: Enumeration<String> = request.headerNames
            while (enumeration.hasMoreElements()) {
                val key = enumeration.nextElement()
                val value: String = request.getHeader(key)
                map[key] = value
            }
            return map
        }

        /**
         * 获取请求客户端的真实ip地址
         *
         * @param request 请求对象
         * @return ip地址
         */
        fun getIpAddr(request: HttpServletRequest): String? {
            for (ipHeader in ipHeaders) {
                if (StrUtil.isNotBlank(ipHeader)) {
                    val ip: String? = request.getHeader(ipHeader)
                    if (!StrUtil.isEmpty(ip) && !ip!!.contains("unknown")) {
                        return ip
                    }
                }
            }
            return request.remoteAddr
        }

        /**
         * web应用绝对路径
         *
         * @param request 请求对象
         * @return 绝对路径
         */
        fun getBasePath(request: HttpServletRequest): String? {
            val path: String = request.contextPath
            return ((request.scheme + "://" + request.serverName).toString() + ":" + request.serverPort).toString() + path + "/"
        }
    }

}