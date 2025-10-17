package com.makebk.sdk

import cn.hutool.log.LogFactory
import com.makebk.sdk.exception.SdkRuntimeException
import io.vertx.core.Future
import io.vertx.core.MultiMap
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpRequest
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import io.vertx.ext.web.multipart.MultipartForm
import io.vertx.kotlin.core.vertxOptionsOf
import io.vertx.kotlin.coroutines.await
import java.io.File

/**
 *  SdkClient
 *  @description：TODO
 *  @author：gme 2023/3/24｜10:16
 *  @version：0.0.1
 */

class SdkClient {

    private val logger = LogFactory.get(javaClass)

    private val client: WebClient by lazy {

        val defaultEventLoopPoolSize = Runtime.getRuntime().availableProcessors()
        val eventLoopPoolSize = System.getProperty("vertx.eventLoopPoolSize")
            ?.toIntOrNull()
            ?.takeIf { it > 0 } ?: defaultEventLoopPoolSize
        val vertxOptions = vertxOptionsOf(
            eventLoopPoolSize = eventLoopPoolSize,
            preferNativeTransport = true,
        )
        val vertx = Vertx.vertx(vertxOptions)
        val options = WebClientOptions().apply {
            userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
            isKeepAlive = false
        }
        WebClient.create(vertx, options)
    }

    suspend fun get(host: String, uri: String, params: Map<*, *>?): String? {
        return get(443, host, uri, params)
    }

    suspend fun get(port: Int, host: String, uri: String, params: Map<*, *>?): String? {
        return get(port, host, uri, mutableMapOf(), params)
    }


    suspend fun get(
        port: Int,
        host: String,
        uri: String,
        headers: MutableMap<String, String>?,
        params: Map<*, *>?,
    ): String? {
        val request = client.get(port, host, uri)
        request.headers().addAll(headers)
        request.timeout(3000).ssl(port == 443)
        params?.keys?.forEach {
            request.addQueryParam("$it", "${params[it]}")
        }
        try {
            val future = request.send().await()
            return if (future.statusCode() == 200) {
                future.bodyAsString()
            } else {
                logger.error(future.bodyAsString())
                throw SdkRuntimeException(999, "$host ==> $uri 接口调用失败")
            }

        } catch (e: Exception) {
            logger.error(e.message, e)
            throw SdkRuntimeException(999, "$host ==> $uri 接口调用失败 ${e.message}")
        }
    }

    suspend fun post(host: String, uri: String, body: Map<String, Any>?): String {
        return post(443, host, uri, body)
    }

    suspend fun post(port: Int = 443, host: String, uri: String, body: Map<String, Any>?): String {
        return post(port, host, uri, mutableMapOf(), body, null)
    }

    suspend fun post(
        port: Int = 443,
        host: String,
        uri: String,
        headers: MutableMap<String, String>?,
        body: Map<String, Any>?,
    ): String {
        return post(port, host, uri, headers, body, null)
    }

    suspend fun postForm(port: Int = 443, host: String, uri: String, form: MultiMap?): String {
        return post(port, host, uri, mutableMapOf(), null, form)
    }

    suspend fun post(
        port: Int = 443,
        host: String,
        uri: String,
        headers: MutableMap<String, String>?,
        body: Map<String, Any>?,
        form: MultiMap?,
    ): String {
        var request = client.post(port, host, uri).timeout(3000).ssl(port == 443)
        request = setHeaders(request, headers)
        val future = if (form != null) {
            request.sendForm(form).await()
        } else {
            request.sendJson(body).await()
        }
        return if (future.statusCode() == 200) {
            future.bodyAsString()
        } else {
            throw SdkRuntimeException(999, "$host ==> $uri 接口调用失败 ${future.body()}")
        }
    }

    fun postFile(
        port: Int = 80,
        host: String,
        uri: String,
        headers: MutableMap<String, String>?,
        file: File,
    ): Future<HttpResponse<Buffer>> {
        val form = MultipartForm.create()
            .attribute("filename", file.name)
            .binaryFileUpload(
                "binFile",
                file.name,
                file.path,
                "Application/json"
            )

        val request = client.post(port, host, uri)
            .timeout(3000).ssl(port == 443)
        return setHeaders(request, headers)
            .putHeader("content-type", "multipart/form-data")
            .sendMultipartForm(form)
    }

    suspend fun delete(
        port: Int = 443,
        host: String,
        url: String,
        headers:
        MutableMap<String, String>?,
    ): String {
        val delete = client.delete(port, host, url).ssl(port == 443)
        delete.headers().addAll(headers)
        val future =
            delete.timeout(3000).send().await()
        return if (future.statusCode() == 200) {
            future.bodyAsString()
        } else {
            throw SdkRuntimeException(999, "$url 接口调用失败 ${future.body()}")
        }
    }


    /**
     * 设置请求头
     *
     * @param request 请求对象
     * @param headers 请求头参数
     */
    private fun setHeaders(request: HttpRequest<Buffer>, headers: MutableMap<String, String>?): HttpRequest<Buffer> {
        if (headers != null) {
            for ((key, value) in headers) {
                request.putHeader(key, value)
            }
        } else {
            request.putHeader("Content-Type", "application/json")
            request.putHeader("Connection", "keep-alive")
            request.putHeader("Accept-Encoding", "gzip, deflate, br")
            request.putHeader("Accept", "*/*")
            request.putHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36"
            )
        }
        return request
    }
}