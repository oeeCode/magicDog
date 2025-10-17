package com.makebk.common.sql

import cn.hutool.core.date.DateField
import cn.hutool.core.date.DateTime
import cn.hutool.core.date.DateUtil
import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.StringPool
import com.makebk.common.constant.SqlConstant
import com.makebk.common.exception.BaseException
import java.util.*

class Result() {
    var startTime: Long? = null
    var endTime: Long? = null

    constructor(startTime: Long, endTime: Long) : this() {
        this.startTime = startTime
        this.endTime = endTime
    }
}

data class DateResult(val startTime: Date, val endTime: Date)
data class DateStrResult(val startTime: String, val endTime: String)


infix fun Result.format(values: List<String>): Result {
    try {
        val start = values[0]
        val end = values[1]
        val date = DateUtil.date(start.toLong())
        val endDate = DateUtil.date(end.toLong())
        if (start == end && SqlKeyword.isZoreTime(date)) {
            val startTime = start.toLong() / 1000
            val endTime = DateUtil.endOfDay(DateUtil.parseDate(endDate.toDateStr())).time / 1000
            return Result(startTime, endTime)
        } else {
            val startTime = start.toLong() / 1000
            val endTime = if (SqlKeyword.isZoreTime(endDate)) {
                DateUtil.endOfDay(DateUtil.parseDate(endDate.toDateStr())).time / 1000
            } else {
                end.toLong() / 1000
            }
            return Result(startTime, endTime)
        }
    } catch (e: Exception) {
        throw BaseException("操作失败,查询参数日期格式不正确,请使用时间戳格式.")
    }
}

/**
 *  SqlKeyword
 *  @description：TODO
 *  @author：gme 2023/5/20｜14:35
 *  @version：0.0.1
 */
object SqlKeyword {
    fun buildCondition(query: MutableMap<String, Any>?, alias: String?): QueryWrapper<Any> {
        val qw = QueryWrapper<Any>()
        query?.forEach {
            val key = it.key
            val value = it.value
            if (StrUtil.startWith(key, SqlConstant.NULL) || (!StrUtil.hasEmpty(key, "$value") && !StrUtil.endWith(
                    key,
                    SqlConstant.IGNORE
                )) && ("$value" != "null" && "$value" != "NULL")
            ) {
                if (StrUtil.endWith(key, SqlConstant.EQUAL)) {
                    qw.eq(getColumn(key, alias, SqlConstant.EQUAL), value)
                } else if (StrUtil.endWith(key, SqlConstant.IN)) {
                    qw.`in`(
                        getColumn(key, alias, SqlConstant.IN),
                        "$value".replace("[", "").replace("]", "").split(StringPool.COMMA)
                    )
                } else if (StrUtil.endWith(key, SqlConstant.NOTIN)) {
                    qw.notIn(
                        getColumn(key, alias, SqlConstant.NOTIN),
                        "$value".replace("[", "").replace("]", "").split(StringPool.COMMA)
                    )
                } else if (StrUtil.endWith(key, SqlConstant.NOT_EQUAL)) {
                    qw.ne(getColumn(key, alias, SqlConstant.NOT_EQUAL), value)
                } else if (StrUtil.endWith(key, SqlConstant.LIKE)) {
                    qw.like(getColumn(key, alias, SqlConstant.LIKE), value)
                } else if (StrUtil.endWith(key, SqlConstant.NOT_LIKE)) {
                    qw.notLike(getColumn(key, alias, SqlConstant.NOT_LIKE), value)
                } else if (StrUtil.endWith(key, SqlConstant.RIGHT_LIKE)) {
                    qw.likeRight(getColumn(key, alias, SqlConstant.RIGHT_LIKE), value)
                } else if (StrUtil.endWith(key, SqlConstant.LEFT_LIKE)) {
                    qw.likeLeft(getColumn(key, alias, SqlConstant.LEFT_LIKE), value)
                } else if (StrUtil.endWith(key, SqlConstant.GT)) {
                    qw.gt(getColumn(key, alias, SqlConstant.GT), value)
                } else if (StrUtil.endWith(key, SqlConstant.LT)) {
                    qw.lt(getColumn(key, alias, SqlConstant.LT), value)
                } else if (StrUtil.endWith(key, SqlConstant.GE)) {
                    qw.ge(getColumn(key, alias, SqlConstant.GE), value)
                } else if (StrUtil.endWith(key, SqlConstant.LE)) {
                    qw.le(getColumn(key, alias, SqlConstant.LE), value)
                } else if (StrUtil.endWith(key, SqlConstant.TIMESTAMP_GT)) {
                    qw.gt(getColumn(key, alias, SqlConstant.TIMESTAMP_GT), value)
                } else if (StrUtil.endWith(key, SqlConstant.TIMESTAMP_EQUAL)) {
                    qw.eq(getColumn(key, alias, SqlConstant.TIMESTAMP_EQUAL), value)
                } else if (StrUtil.endWith(key, SqlConstant.TIMESTAMP_LT)) {
                    qw.lt(getColumn(key, alias, SqlConstant.TIMESTAMP_LT), value)
                } else if (StrUtil.endWith(key, SqlConstant.TIMESTAMP_BT)) {
                    val result = Result()
                    if (value is ArrayList<*>) {
                        val timeRange = result.format(value.map { "$it" })
                        qw.between(
                            getColumn(key, alias, SqlConstant.TIMESTAMP_BT),
                            timeRange.startTime,
                            timeRange.endTime
                        )
                    } else if (value is String) {
                        val values = value.split(StringPool.COMMA)
                        val timeRange = result.format(values)
                        qw.between(
                            getColumn(key, alias, SqlConstant.TIMESTAMP_BT),
                            timeRange.startTime,
                            timeRange.endTime
                        )
                    } else {
                        qw.eq(getColumn(key, alias, SqlConstant.TIMESTAMP_BT), value)
                    }
                } else if (StrUtil.endWith(key, SqlConstant.DATE_GT)) {
                    qw.gt(getColumn(key, alias, SqlConstant.DATE_GT), DateUtil.date("$value".toLong()))
                } else if (StrUtil.endWith(key, SqlConstant.DATE_EQUAL)) {
                    qw.eq(getColumn(key, alias, SqlConstant.DATE_EQUAL), DateUtil.date("$value".toLong()))
                } else if (StrUtil.endWith(key, SqlConstant.DATE_LT)) {
                    qw.lt(getColumn(key, alias, SqlConstant.DATE_LT), DateUtil.date("$value".toLong()))
                } else if (StrUtil.endWith(key, SqlConstant.DATE_BT)) {
                    if ("$value".contains(StringPool.COMMA)) {
                        val vales = "$value".split(StringPool.COMMA)
                        val timeRange = try {
                            val start = vales[0]
                            val end = vales[1]
                            val date = DateUtil.date(start.toLong())
                            if (start == end && isZoreTime(date)) {
                                val startTime = date
                                val endTime = DateUtil.endOfDay(DateUtil.date(end.toLong()))
                                DateResult(startTime, endTime)
                            } else {
                                val startTime = date
                                val endTime = DateUtil.date(end.toLong())
                                DateResult(startTime, endTime)
                            }
                        } catch (e: Exception) {
                            throw BaseException("操作失败,查询参数日期格式不正确,请使用时间戳格式.")
                        }
                        qw.between(getColumn(key, alias, SqlConstant.DATE_BT), timeRange.startTime, timeRange.endTime)
                    } else {
                        qw.eq(getColumn(key, alias, SqlConstant.DATE_BT), DateUtil.date("$value".toLong()))
                    }

                } else if (StrUtil.endWith(key, SqlConstant.DATE_STR_GT)) {
                    qw.gt(
                        getColumn(key, alias, SqlConstant.DATE_STR_GT),
                        DateUtil.formatDateTime(DateUtil.date("$value".toLong()))
                    )
                } else if (StrUtil.endWith(key, SqlConstant.DATE_STR_EQUAL)) {
                    qw.eq(
                        getColumn(key, alias, SqlConstant.DATE_STR_EQUAL),
                        DateUtil.formatDateTime(DateUtil.date("$value".toLong()))
                    )
                } else if (StrUtil.endWith(key, SqlConstant.DATE_STR_LT)) {
                    qw.lt(
                        getColumn(key, alias, SqlConstant.DATE_STR_LT),
                        DateUtil.formatDateTime(DateUtil.date("$value".toLong()))
                    )
                } else if (StrUtil.endWith(key, SqlConstant.DATE_STR_BT)) {
                    if ("$value".contains(StringPool.COMMA)) {
                        val vales = "$value".split(StringPool.COMMA)
                        val timeRange = try {
                            val start = vales[0]
                            val end = vales[1]
                            val date = DateUtil.date(start.toLong())
                            if (start == end && isZoreTime(date)) {
                                val startTime = date
                                val endTime = DateUtil.endOfDay(DateUtil.date(end.toLong()))
                                DateStrResult(DateUtil.formatDateTime(startTime), DateUtil.formatDateTime(endTime))
                            } else {
                                val startTime = date
                                val endTime = DateUtil.date(end.toLong())
                                DateStrResult(DateUtil.formatDateTime(startTime), DateUtil.formatDateTime(endTime))
                            }
                        } catch (e: Exception) {
                            throw BaseException("操作失败,查询参数日期格式不正确,请使用时间戳格式.")
                        }
                        qw.between(
                            getColumn(key, alias, SqlConstant.DATE_STR_BT),
                            timeRange.startTime,
                            timeRange.endTime
                        )
                    } else {
                        qw.eq(
                            getColumn(key, alias, SqlConstant.DATE_STR_BT),
                            DateUtil.formatDateTime(DateUtil.date("$value".toLong()))
                        )
                    }
                } else if (StrUtil.endWith(key, SqlConstant.IS_NULL)) {
                    qw.isNull(getColumn(key, alias, SqlConstant.IS_NULL))
                } else if (StrUtil.endWith(key, SqlConstant.NOT_NULL)) {
                    qw.isNotNull(getColumn(key, alias, SqlConstant.NOT_NULL))
                } else {
                    qw.eq(getColumn(key, alias, SqlConstant.EQUAL), value)
                }
            }
        }
        return qw
    }

    fun isZoreTime(dateTime: DateTime): Boolean {
        val hour = dateTime.getField(DateField.HOUR)
        val minute = dateTime.getField(DateField.MINUTE)
        val second = dateTime.getField(DateField.SECOND)
        return hour == 0 && minute == 0 && second == 0
    }


    private fun getColumn(key: String, alias: String?, prefix: String): String {
        return getKey(alias, StrUtil.removeSuffix(key, prefix))
    }

    fun getKey(alias: String?, key: String): String {
        return if (Objects.nonNull(alias) && key.indexOf(StringPool.DOT) < 0) {
            "$alias.$key"
        } else {
            key
        }
    }

    fun filter(param: String): String? {
        return if (Objects.nonNull(param)) param.replace(
            "(?i)${SqlConstant.SQL_REGEX}",
            ""
        ) else return null
    }
}

