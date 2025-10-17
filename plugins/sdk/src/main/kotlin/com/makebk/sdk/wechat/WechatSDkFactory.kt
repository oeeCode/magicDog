package com.makebk.sdk.wechat

import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.lang.Assert
import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import com.makebk.common.util.CoroutineUtil
import com.makebk.sdk.SdkClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

object WechatSDkFactory {

    private val logger = LogFactory.get(javaClass)
    private var sdkClient: SdkClient = SdkClient()
    private var host = WechatEndpoint.get("url.host")
    private var scope = CoroutineUtil.VirtualThread
    var loadingScan = false


    /**
     * 检查登陆状态
     */
    suspend fun checklogin(): CheckloginResp? {
        return withContext(scope.coroutineContext) {
            val params = mutableMapOf<String, String>()
            val result = sdkClient.get(
                8080, host, WechatEndpoint.get("url.checklogin"), params

            )
            val resp = JSONUtil.toBean(result, Resp::class.java)
            Assert.isFalse(resp.code != 200, "操作失败.${resp.msg}")
            JSONUtil.toBean("${resp.data}", CheckloginResp::class.java)
        }
    }

    /**
     * 获取登陆用户信息
     */
    suspend fun userinfo(): UserInfoResp {
        return withContext(scope.coroutineContext) {
            val params = mutableMapOf<String, String>()
            val result = sdkClient.get(
                8080, host, WechatEndpoint.get("url.userinfo"), params
            )
            val resp = JSONUtil.toBean(result, Resp::class.java)
            Assert.isFalse(resp.code != 200, "操作失败.${resp.msg}")
            JSONUtil.toBean("${resp.data}", UserInfoResp::class.java)
        }
    }

    /**
     * 从数据库中获取通讯录信息（wxid从这个接口获取）
     */
    suspend fun dbcontacts(): List<DbContactsResp>? {
        return withContext(scope.coroutineContext) {
            val params = mutableMapOf<String, String>()
            val result = sdkClient.get(
                8080, host, WechatEndpoint.get("url.dbcontacts"), params
            )
            val resp = JSONUtil.toBean(result, Resp::class.java)
            Assert.isFalse(resp.code != 200, "操作失败.${resp.msg}")
            val obj = JSONUtil.toBean("${resp.data}", DbContactsResps::class.java)
            obj.contacts
        }
    }

    /**
     * 发送文本消息（好友和群聊组都可通过此接口发送，群聊组消息支持艾特）
     */
    suspend fun sendtxtmsg(msg: TxtMsg): Resp {
        return withContext(scope.coroutineContext) {
            val result = sdkClient.post(
                8080,
                host,
                WechatEndpoint.get("url.sendtxtmsg"),
                BeanUtil.beanToMap(msg, false, true).toMap()
            )
            val resp = JSONUtil.toBean(result, Resp::class.java)
            Assert.isFalse(resp.code != 200, "操作失败.${resp.msg}")
            resp
        }
    }

    /**
     * 发送图片消息（支持json和form-data表单上传两种方式，json方式请将二进制数据使用base64编码后发送）
     */
    suspend fun sendimgmsg(msg: ImgMsg): Resp {
        return withContext(scope.coroutineContext) {
            val result = sdkClient.post(
                8080,
                host,
                WechatEndpoint.get("url.sendimgmsg"),
                BeanUtil.beanToMap(msg, false, true).toMap()
            )
            val resp = JSONUtil.toBean(result, Resp::class.java)
            Assert.isFalse(resp.code != 200, "操作失败.${resp.msg}")
            resp
        }
    }

    /**
     * 发送文件消息（支持json和form-data表单上传两种方式，json方式请将二进制数据使用base64编码后发送）
     */
    suspend fun sendfilemsg(msg: FileMsg): Resp {
        return withContext(scope.coroutineContext) {
            val result = sdkClient.post(
                8080,
                host,
                WechatEndpoint.get("url.sendfilemsg"),
                BeanUtil.beanToMap(msg, false, true).toMap()
            )
            val resp = JSONUtil.toBean(result, Resp::class.java)
            Assert.isFalse(resp.code != 200, "操作失败.${resp.msg}")
            resp
        }
    }

    /**
     * 从数据库中获取群聊组信息和成员列表
     */
    suspend fun dbchatroom(wxid: String): Any? {
        return withContext(scope.coroutineContext) {
            val params = mutableMapOf<String, String>()
            params.put("wxid", wxid)
            val result = sdkClient.get(
                8080, host, WechatEndpoint.get("url.dbchatroom"), params
            )
            val resp = JSONUtil.toBean(result, Resp::class.java)
            Assert.isFalse(resp.code != 200, "操作失败.${resp.msg}")
            val obj = JSONUtil.toBean("${resp.data}", DbContactsResps::class.java)
            resp.data
        }
    }

    /**
     * 从数据库中通过WXID反查微信昵称（支持好友、群聊组和群聊组内成员等）
     */
    suspend fun dbaccountbywxid() {}

    /**
     * 消息转发
     */
    suspend fun forwardmsg() {}

    /**
     * 获取支持查询的数据库句柄
     * 可执行的SQL例子：
     * PublicMsg.db
     * 查询指定公众号的所有文章（本地已接受的） SELECT * FROM PublicMsg WHERE StrTalker=='gh_13508120ed24'
     * 查询指定时间范围的所有订阅号文章（20230115全天的） SELECT * FROM PublicMsg WHERE CreateTime>1705248000 AND CreateTime<1705334399;
     * 分页查询，从第21行开始，累加20条数据进行检索 SELECT * FROM PublicMsg ORDER BY localId DESC limit 20,20;
     * MicroMsg.db
     * 查询通讯录所有成员（包括好友、群聊组、订阅号等） SELECT * FROM Contact
     */
    suspend fun dbs() {}

    /**
     * 通过数据库句柄执行SQL语句
     */
    suspend fun execsql() {}

    /**
     * 将lz4压缩的数据进行解码（请求数据需要base64）
     */
    suspend fun lz4decode() {}

    /**
     * 停止 wxbot-sidecar（此命令用来停止http server，并中止程序运行）
     */
    suspend fun close(): Resp {
        return withContext(scope.coroutineContext) {
            val params = mutableMapOf<String, String>()
            val result = sdkClient.get(
                8080, host, WechatEndpoint.get("url.close"), params
            )
            val resp = JSONUtil.toBean(result, Resp::class.java)
            Assert.isFalse(resp.code != 200, "操作失败.${resp.msg}")
            resp
        }
    }

    private fun exec(command: String) = Runtime.getRuntime().exec(command)

    private fun getTaskList(): Process? {
        return when {
            isWindows() -> exec("tasklist")
            isMac() -> exec("ps aux")
            isLinux() -> exec("ps aux")
            else -> {
                // 不支持的操作系统
                null
            }
        }
    }

    private fun isWindows(): Boolean {
        return System.getProperty("os.name").lowercase().contains("windows")
    }

    private fun isMac(): Boolean {
        return System.getProperty("os.name").lowercase().contains("mac")
    }

    private fun isLinux(): Boolean {
        return System.getProperty("os.name").lowercase().contains("linux")
    }

    suspend fun startBot(pids:MutableList<String>){
        try {
            val loginResp = checklogin()
            if (!loadingScan) {
                if (loginResp?.status != "1") {
                    loadingScan = true
                    // 掉线了 尝试重启
                    logger.debug("bot 掉线了正在尝试重启服务 ")
                    clearCallCache()
                    pids.map {  pid ->
                        exec("${WechatEndpoint.get("command.wxbot.start")} -p $pid -s")
                    }
                }else{
                    loadingScan = false
                    logger.debug("bot server success")
                }
            }else{
                if( loginResp?.status == "1") {
                    loadingScan = false
                }else{
                    logger.debug("等待用户确认..")
                }
            }
        } catch (e: Exception) {
            clearCallCache()
            pids.map {  pid ->
                exec("${WechatEndpoint.get("command.wxbot.start")} -p $pid -s")
            }
        }
    }


    fun clearCallCache(){
        val process = getTaskList()
        if (process == null) {
            logger.warn("Unsupported operating system for process checking")
            return
        }
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val lines = mutableListOf<String>()
        while (true) {
            val line = reader.readLine() ?: break
            val result = when {
                isWindows() -> line.contains("wxbot-sidecar.exe")
                else -> {
                    false
                }
            }
            if (result) {
                lines.add(line)
            }
        }

        if(lines.isNotEmpty()){
            lines.forEach { line ->
                val cols = line.split("\\s+".toRegex())
                val pid= cols[1]
                when {
                    isWindows() ->  exec("taskkill /PID $pid /F")
                    else -> {
                        exec("kill -9 $pid ")
                    }
                }

            }
        }
    }

    /**
     * 验证服务是否正常
     */
    suspend fun checkService(count :Int = 0 ) {
        val pids = mutableListOf<String>()
        try {
            //判断微信是否已经启动
            val process = getTaskList()
            if (process == null) {
                logger.warn("Unsupported operating system for process checking")
                return
            }
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val lines = mutableListOf<String>()
            while (true) {
                val line = reader.readLine() ?: break
                val result = when {
                    isWindows() -> line.contains("WeChat.exe")
                    isMac() -> line.contains("WeChat.app")
                    isLinux() -> line.contains("WeChat.app")
                    else -> {
                        false
                    }
                }
                if (result) {
                    lines.add(line)
                }
            }
            if(lines.isNotEmpty()){
                lines.forEach { line ->
                    val cols = line.split("\\s+".toRegex())
                    val pid= cols[1]
                    pids.add(pid)
                }
                println( "微信已启动 pid =  $pids")
                delay(3000)
                startBot(pids)
            }else{
                exec(WechatEndpoint.get("command.wx.start"))
                checkService(0)
            }

        } catch (e: Exception) {
            logger.error("Failed to execute process command", e)
            val c = count+1
            logger.debug("尝试重新拉起服务,第 $c 次")
            delay(5000)
            checkService(c)
        }


    }

    fun disableUpdateWx(){
        exec(WechatEndpoint.get("command.wx.disable.update"))
    }

}

suspend fun main() {
    WechatSDkFactory.checkService()
//    if (resp1?.status != "1") { // 掉线了 尝试重启
//        WechatSDkFactory.close() // 关闭http
//        Runtime.getRuntime().exec("C:\\wechat.exe ")
//        delay(3000) // 三秒后重启服务
//        Runtime.getRuntime().exec("C:\\wechat.exe ")
//    }
//
//    val resp2 = WechatSDkFactory.userinfo()
//    println(JSONUtil.toJsonStr(resp2))
//
//    val resp3 = WechatSDkFactory.dbcontacts()
//    println(JSONUtil.toJsonStr(resp3))

//    val resp4 = WechatSDkFactory.sendtxtmsg(
//        TxtMsg(
//            "44045013442@chatroom",
//            "测试一下",
////            listOf("wxid_42nivg7m6zf312")
//        )
//    )
//    println(JSONUtil.toJsonStr(resp4))

//    val resp5 = WechatSDkFactory.dbchatroom("44045013442@chatroom")
//    println(JSONUtil.formatJsonStr(JSONUtil.toJsonStr(resp5)))

}