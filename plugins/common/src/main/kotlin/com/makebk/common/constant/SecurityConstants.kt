package com.makebk.common.constant

/**
 *  SecurityConstants
 *  @description：TODO
 *  @author：gme 2023/2/9｜09:55
 *  @version：0.0.1
 */
object SecurityConstants {
    const val REQUESTID = "X-Request-Id"
    const val SALT = "makebk"
    const val JWT_KEY_USER_ID = "userId"
    const val JWT_KEY_GROUP_ID = "groupId"
    const val JWT_KEY_USER_NAME = "username"
    const val JWT_KEY_USER_TYPE = "type"
    const val PREFIX_SHIRO_CACHE = "$SALT:cache:"
    const val PREFIX_SHIRO_REFRESH_TOKEN = "$SALT:refresh_token:"
    const val PREFIX_SHIRO_REFRESH_CHECK = "refresh_check:"
    const val CURRENT_TIME_MILLIS = "$SALT:currentTimeMillis"
    const val REPEAT_SUBMIT = "$SALT:repeat_submit:"
    const val TOKEN = "$SALT:token:"
    const val CAPTCHA_CODE_KEY = "$SALT:captcha_codes:"
    const val CAPTCHA_SMS_CODE_KEY = "$SALT:captcha_sms_codes:"
    const val SCOPE_CACHE_CODE = "dataScope:code:"
    const val SCOPE_CACHE_CLASS = "dataScope:class:"

    /**
     * 过期时间
     */
    object ExpireTime {
        const val THREE_SEC: Long = 3 //3s
        const val TEN_SEC: Long = 10 //10s
        const val THIRTY_SEC: Long = 30 //30s
        const val ONE_MINUTE: Long = 60 //一分钟
        const val THREE_MINUTE: Long = 60 * 3 //三分钟
        const val FIVE_MINUTE: Long = 60 * 5 //五分钟
        const val THIRTY_MINUTES: Long = 60 * 30 //30分钟
        const val ONE_HOUR: Long = 60 * 60 //一小时
        const val THREE_HOURS: Long = 60 * 60 * 3 //三小时
        const val TWELVE_HOURS: Long = 60 * 60 * 12 //十二小时，单位s
        const val ONE_DAY: Long = 60 * 60 * 24 //二十四小时
    }
}