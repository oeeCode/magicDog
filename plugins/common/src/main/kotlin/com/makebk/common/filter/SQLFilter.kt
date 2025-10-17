package com.makebk.common.filter

import cn.hutool.core.util.StrUtil
import com.makebk.common.exception.BaseException
import java.util.*

/**
 *  SQLFilter
 *  @author：gme 2023/2/8｜16:14
 *  @version：0.0.1
 */
object SQLFilter {
    fun sqlInject(str: String): String? {
        var s = str
        if (StrUtil.isBlank(s)) {
            return null
        }
        //去掉'|"|;|\字符
        s = StrUtil.replace(s, "'", "")
        s = StrUtil.replace(s, "\"", "")
        s = StrUtil.replace(s, ";", "")
        s = StrUtil.replace(s, "\\", "")
        //转换成小写
        s = s.lowercase(Locale.getDefault())
        //非法字符
        val keywords = arrayOf("master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop")
        //判断是否包含非法字符
        for (keyword in keywords) {
            if (s.indexOf(keyword) !== -1) {
                throw BaseException("包含非法字符")
            }
        }
        return s
    }
}