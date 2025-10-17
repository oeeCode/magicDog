package com.makebk.common.util

import cn.hutool.core.map.MapUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.*
import java.net.JarURLConnection
import java.net.URISyntaxException
import java.net.URL
import java.util.jar.JarEntry
import java.util.jar.JarFile


/**
 * 敏感词处理工具 - DFA算法实现
 */
object SensitiveWordUtils {
    private var log: Log = LogFactory.get(this.javaClass)
    private var SENSITIVE_WORD_PATH: String? = null
    const val DEFAULT_SENSITIVE_DIR = "sensitive/"

    /**
     * 敏感词匹配规则
     */
    const val MinMatchTYpe = 1 //最小匹配规则，如：敏感词库["中国","中国人"]，语句："我是中国人"，匹配结果：我是[中国]人
    const val MaxMatchType = 2 //最大匹配规则，如：敏感词库["中国","中国人"]，语句："我是中国人"，匹配结果：我是[中国人]

    /**
     * 敏感词集合
     */
    var sensitiveWordMap: HashMap<Any, Any>? = null

    init {
        initDefault()
    }

    private fun initDefault() {
        val url = SensitiveWordUtils::class.java.classLoader.getResource(DEFAULT_SENSITIVE_DIR)
        init(getPath(url))
    }

    private fun getPath(url: URL): String? {
        val path: String
        val connection = url.openConnection()
        path = if (connection is JarURLConnection) {
            val jarFile = connection.jarFile
            loadSenstiveWordSet(jarFile)
            return null
        } else {
            url.path
        }
        return path
    }

    private fun loadSenstiveWordSet(jarFile: JarFile) {
        val sensitiveWordSet: MutableSet<String> = HashSet()
        jarFile.stream().filter { jarEntry: JarEntry ->
            (jarEntry.name.startsWith(DEFAULT_SENSITIVE_DIR)
                    && DEFAULT_SENSITIVE_DIR != jarEntry.name)
        }.forEach { jarEntry: JarEntry ->
            try {
                log.info("正在初始化系统默认敏感字库....{}", jarEntry.name)
                val inputStream = jarFile.getInputStream(jarEntry)
                loadSenitiveWordSet(sensitiveWordSet, inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        log.info("加载{}个默认敏感字", sensitiveWordSet.size)
        initSensitiveWordMap(sensitiveWordSet)
    }

    /**
     * 初始化敏感词库，构建DFA算法模型
     * @param filePath 字库路径
     */
    fun init(filePath: String?) {
        if (StrUtil.isBlank(filePath)) {
            return
        }
        SENSITIVE_WORD_PATH = filePath
        val sensitiveWordSet: MutableSet<String> = HashSet()

        // 读取指定路径下的敏感字库
        try {
            log.info("正在初始化敏感字库....{}", SENSITIVE_WORD_PATH)
            val wordFileDir = File(SENSITIVE_WORD_PATH)
            initSensitiveWordSet(sensitiveWordSet, wordFileDir)
        } catch (e: IOException) {
            e.printStackTrace()
            log.error("初始化敏感字库失败，未加载字库....")
        }
        log.info("加载{}个敏感字", sensitiveWordSet.size)
        initSensitiveWordMap(sensitiveWordSet)
    }

    /**
     * 初始化敏感词库，构建DFA算法模型
     * @param filePath 字库路径
     */
    fun loadByClassPathFile(filePath: String?) {
        SENSITIVE_WORD_PATH = filePath
        val sensitiveWordSet: MutableSet<String> = HashSet()

        // 读取指定路径下的敏感字库
        try {
            log.info("正在初始化敏感字库....{}", SENSITIVE_WORD_PATH)
            val url = SensitiveWordUtils::class.java.classLoader.getResource(SENSITIVE_WORD_PATH)
            val wordFile = File(url.toURI())
            loadSenitiveWordSet(sensitiveWordSet, wordFile)
        } catch (e: IOException) {
            e.printStackTrace()
            log.error("初始化敏感字库失败，未加载字库....")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            log.error("初始化敏感字库失败，未加载字库....")
        }
        log.info("加载{}个敏感字", sensitiveWordSet.size)
        initSensitiveWordMap(sensitiveWordSet)
    }

    /**
     * 词库初始化
     * @param sensitiveWordSet
     * @param wordFileDir
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun initSensitiveWordSet(sensitiveWordSet: MutableSet<String>, wordFileDir: File) {
        if (wordFileDir.isDirectory) {
            val wordFiles = wordFileDir.listFiles()
            for (wordFile in wordFiles) {
                loadSenitiveWordSet(sensitiveWordSet, wordFile)
            }
        }
    }

    /**
     * 加载词库
     * @param sensitiveWordSet
     * @param wordFile
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun loadSenitiveWordSet(sensitiveWordSet: MutableSet<String>, wordFile: File) {
        val reader = BufferedReader(InputStreamReader(FileInputStream(wordFile), "utf-8"))
        var line: String? = ""
        while (reader.readLine().also { line = it } != null) {
            sensitiveWordSet.add(line!!)
            log.trace("加载敏感字{}", line)
        }
        reader.close()
    }

    @Throws(IOException::class)
    private fun loadSenitiveWordSet(sensitiveWordSet: MutableSet<String>, inputStream: InputStream) {
        val reader = BufferedReader(InputStreamReader(inputStream, "utf-8"))
        var line: String
        while (reader.readLine().also { line = it } != null) {
            sensitiveWordSet.add(line)
            log.trace("加载敏感字{}", line)
        }
        reader.close()
    }

    /**
     * 初始化敏感词库，构建DFA算法模型
     *
     * @param sensitiveWordSet 敏感词库
     */
    private fun initSensitiveWordMap(sensitiveWordSet: Set<String>) {
        //初始化敏感词容器，减少扩容操作
        if (MapUtil.isEmpty(sensitiveWordMap)) {
            sensitiveWordMap = HashMap<Any, Any>(sensitiveWordSet.size)
        }
        var key: String
        var nowMap: MutableMap<Any, Any>?
        var newWorMap: MutableMap<Any, Any>?
        //迭代sensitiveWordSet
        val iterator = sensitiveWordSet.iterator()
        while (iterator.hasNext()) {
            //关键字
            key = iterator.next()
            nowMap = sensitiveWordMap
            for (i in 0 until key.length) {
                //转换成char型
                val keyChar = key[i]
                //库中获取关键字
                val wordMap = nowMap!![keyChar]
                //如果存在该key，直接赋值，用于下一个循环获取
                if (wordMap != null) {
                    nowMap = wordMap as MutableMap<Any, Any>?
                } else {
                    //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = HashMap()
                    //不是最后一个
                    newWorMap["isEnd"] = "0"
                    nowMap[keyChar] = newWorMap
                    nowMap = newWorMap
                }
                if (i == key.length - 1) {
                    //最后一个
                    nowMap!!["isEnd"] = "1"
                }
            }
        }
    }
    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     */
    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt 文字
     * @return 若包含返回true，否则返回false
     */
    @JvmOverloads
    fun contains(txt: String, matchType: Int = MaxMatchType): Boolean {
        var flag = false
        val scope = CoroutineUtil.VirtualThread
        return runBlocking {
            for (i in 0 until txt.length) {
                scope.async {
                    val matchFlag = checkSensitiveWord(txt, i, matchType) //判断是否包含敏感字符
                    if (matchFlag > 0) {    //大于0存在，返回true
                        flag = true
                    }
                }.await()
            }
            flag
        }
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return
     */
    fun getSensitiveWord(txt: String, matchType: Int): Set<String> {
        val sensitiveWordList: MutableSet<String> = HashSet()
        var i = 0
        while (i < txt.length) {

            //判断是否包含敏感字符
            val length = checkSensitiveWord(txt, i, matchType)
            if (length > 0) { //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length))
                i = i + length - 1 //减1的原因，是因为for会自增
            }
            i++
        }
        return sensitiveWordList
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt 文字
     * @return
     */
    fun getSensitiveWord(txt: String): Set<String> {
        return getSensitiveWord(txt, MaxMatchType)
    }

    /**
     * 替换敏感字字符
     *
     * @param txt         文本
     * @param replaceChar 替换的字符，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符：*， 替换结果：我爱***
     * @param matchType   敏感词匹配规则
     * @return
     */
    @JvmOverloads
    fun replaceSensitiveWord(txt: String, replaceChar: Char, matchType: Int = MaxMatchType): String {
        var resultTxt = txt
        //获取所有的敏感词
        val set = getSensitiveWord(txt, matchType)
        val iterator = set.iterator()
        var word: String
        var replaceString: String
        while (iterator.hasNext()) {
            word = iterator.next()
            replaceString = getReplaceChars(replaceChar, word.length)
            resultTxt = resultTxt.replace(word.toRegex(), replaceString)
        }
        return resultTxt
    }

    /**
     * 替换敏感字字符
     *
     * @param txt        文本
     * @param replaceStr 替换的字符串，匹配的敏感词以字符逐个替换，如 语句：我爱中国人 敏感词：中国人，替换字符串：[屏蔽]，替换结果：我爱[屏蔽]
     * @param matchType  敏感词匹配规则
     * @return
     */
    @JvmOverloads
    fun replaceSensitiveWord(txt: String, replaceStr: String?, matchType: Int = MaxMatchType): String {
        var resultTxt = txt
        //获取所有的敏感词
        val set = getSensitiveWord(txt, matchType)
        val iterator = set.iterator()
        var word: String
        while (iterator.hasNext()) {
            word = iterator.next()
            resultTxt = resultTxt.replace(word.toRegex(), replaceStr!!)
        }
        return resultTxt
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar
     * @param length
     * @return
     */
    private fun getReplaceChars(replaceChar: Char, length: Int): String {
        var resultReplace = replaceChar.toString()
        for (i in 1 until length) {
            resultReplace += replaceChar
        }
        return resultReplace
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br></br>
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     */
    private fun checkSensitiveWord(txt: String, beginIndex: Int, matchType: Int): Int {
        //敏感词结束标识位：用于敏感词只有1位的情况
        var flag = false
        //匹配标识数默认为0
        var matchFlag = 0
        var word: Char
        var nowMap: Map<*, *>? = sensitiveWordMap
        for (i in beginIndex until txt.length) {
            word = txt[i]
            //获取指定key
            nowMap = nowMap!![word] as Map<*, *>?
            if (nowMap != null) { //存在，则判断是否为最后一个
                //找到相应key，匹配标识+1
                matchFlag++
                //如果为最后一个匹配规则,结束循环，返回匹配标识数
                if ("1" == nowMap["isEnd"]) {
                    //结束标志位为true
                    flag = true
                    //最小规则，直接返回,最大规则还需继续查找
                    if (MinMatchTYpe == matchType) {
                        break
                    }
                }
            } else { //不存在，直接返回
                break
            }
        }
        if (matchFlag < 2 || !flag) { //长度必须大于等于1，为词
            matchFlag = 0
        }
        return matchFlag
    }
}
