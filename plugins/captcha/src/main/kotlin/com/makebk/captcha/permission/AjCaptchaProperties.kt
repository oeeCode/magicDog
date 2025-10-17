package com.makebk.captcha.permission

import com.anji.captcha.model.common.CaptchaTypeEnum
import org.springframework.boot.context.properties.ConfigurationProperties
import java.awt.Font

@ConfigurationProperties("aj.captcha")
class AjCaptchaProperties {
    /**
     * 验证码类型.
     */
    private var type = CaptchaTypeEnum.DEFAULT

    /**
     * 滑动拼图底图路径.
     */
    var jigsaw: String? = ""

    /**
     * 点选文字底图路径.
     */
    var picClick: String? = ""


    /**
     * 右下角水印文字(我的水印).
     */
    var waterMark: String? = "我的水印"

    /**
     * 右下角水印字体(文泉驿正黑).
     */
    var waterFont: String? = "WenQuanZhengHei.ttf"

    /**
     * 点选文字验证码的文字字体(文泉驿正黑).
     */
    var fontType: String? = "WenQuanZhengHei.ttf"

    /**
     * 校验滑动拼图允许误差偏移量(默认5像素).
     */
    var slipOffset: String? = "5"

    /**
     * aes加密坐标开启或者禁用(true|false).
     */
    var aesStatus: Boolean? = true

    /**
     * 滑块干扰项(0/1/2)
     */
    var interferenceOptions: String? = "0"

    /**
     * local缓存的阈值
     */
    var cacheNumber: String? = "1000"

    /**
     * 定时清理过期local缓存(单位秒)
     */
    var timingClear: String? = "180"

    /**
     * 缓存类型redis/local/....
     */
    var cacheType = StorageType.redis

    /**
     * 历史数据清除开关
     */
    var isHistoryDataClearEnable: Boolean = false

    /**
     * 一分钟内接口请求次数限制 开关
     */
    var isReqFrequencyLimitEnable: Boolean = false

    /***
     * 一分钟内check接口失败次数
     */
    var reqGetLockLimit: Int = 5

    /**
     *
     */
    var reqGetLockSeconds: Int = 300

    /***
     * get接口一分钟内限制访问数
     */
    var reqGetMinuteLimit: Int = 100
    private var reqCheckMinuteLimit = 100
    var reqVerifyMinuteLimit: Int = 100

    /**
     * 点选字体样式
     */
    var fontStyle: Int = Font.BOLD

    /**
     * 点选字体大小
     */
    var fontSize: Int = 25

    /**
     * 点选文字个数，存在问题，暂不要使用
     */
    var clickWordCount: Int = 4

    fun getReqFrequencyLimitEnable(): Boolean {
        return this.isReqFrequencyLimitEnable
    }

    fun getReqCheckMinuteLimit(): Int {
        return reqGetMinuteLimit
    }

    fun setReqCheckMinuteLimit(reqCheckMinuteLimit: Int) {
        this.reqCheckMinuteLimit = reqCheckMinuteLimit
    }

    enum class StorageType {
        /**
         * 内存.
         */
        local,

        /**
         * redis.
         */
        redis,

        /**
         * 其他.
         */
        other,
    }

    fun getType(): CaptchaTypeEnum {
        return type
    }

    fun setType(type: CaptchaTypeEnum) {
        this.type = type
    }
}