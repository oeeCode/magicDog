package com.makebk.captcha.config

import com.anji.captcha.model.common.Const
import com.anji.captcha.service.CaptchaService
import com.anji.captcha.service.impl.CaptchaServiceFactory
import com.anji.captcha.util.Base64Utils
import com.anji.captcha.util.FileCopyUtils
import com.anji.captcha.util.ImageUtils
import com.anji.captcha.util.StringUtils
import com.makebk.captcha.permission.AjCaptchaProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import java.util.*

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AjCaptchaProperties::class)
class CaptchaServiceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun captchaService(prop: AjCaptchaProperties): CaptchaService {
        val config = Properties().apply {
            put(Const.CAPTCHA_CACHETYPE, prop.cacheType.name)
            put(Const.CAPTCHA_WATER_MARK, prop.waterMark)
            put(Const.CAPTCHA_FONT_TYPE, prop.fontType)
            put(Const.CAPTCHA_TYPE, prop.getType().codeValue)
            put(Const.CAPTCHA_INTERFERENCE_OPTIONS, prop.interferenceOptions)
            put(Const.ORIGINAL_PATH_JIGSAW, prop.jigsaw)
            put(Const.ORIGINAL_PATH_PIC_CLICK, prop.picClick)
            put(Const.CAPTCHA_SLIP_OFFSET, prop.slipOffset)
            put(Const.CAPTCHA_AES_STATUS, prop.aesStatus.toString())
            put(Const.CAPTCHA_WATER_FONT, prop.waterFont)
            put(Const.CAPTCHA_CACAHE_MAX_NUMBER, prop.cacheNumber)
            put(Const.CAPTCHA_TIMING_CLEAR_SECOND, prop.timingClear)
            put(Const.HISTORY_DATA_CLEAR_ENABLE, if (prop.isHistoryDataClearEnable) "1" else "0")
            put(Const.REQ_FREQUENCY_LIMIT_ENABLE, if (prop.getReqFrequencyLimitEnable()) "1" else "0")
            put(Const.REQ_GET_LOCK_LIMIT, prop.reqGetLockLimit.toString() + "")
            put(Const.REQ_GET_LOCK_SECONDS, prop.reqGetLockSeconds.toString() + "")
            put(Const.REQ_GET_MINUTE_LIMIT, prop.reqGetMinuteLimit.toString() + "")
            put(Const.REQ_CHECK_MINUTE_LIMIT, prop.getReqCheckMinuteLimit().toString() + "")
            put(Const.REQ_VALIDATE_MINUTE_LIMIT, prop.reqVerifyMinuteLimit.toString() + "")
            put(Const.CAPTCHA_FONT_SIZE, prop.fontSize.toString() + "")
            put(Const.CAPTCHA_FONT_STYLE, prop.fontStyle.toString() + "")
            put(Const.CAPTCHA_WORD_COUNT, prop.clickWordCount.toString() + "")
        }


        if ((StringUtils.isNotBlank(prop.jigsaw) && prop.jigsaw!!.startsWith("classpath:"))
            || (StringUtils.isNotBlank(prop.picClick) && prop.picClick!!.startsWith("classpath:"))
        ) {
            config.put(Const.CAPTCHA_INIT_ORIGINAL, "true")
            initializeBaseMap(prop.jigsaw, prop.picClick)
        }
        val s = CaptchaServiceFactory.getInstance(config)
        return s
    }

    companion object {
        private fun initializeBaseMap(jigsaw: String?, picClick: String?) {
            ImageUtils.cacheBootImage(
                getResourcesImagesFile("$jigsaw/original/*.png"),
                getResourcesImagesFile("$jigsaw/slidingBlock/*.png"),
                getResourcesImagesFile("$picClick/*.png")
            )
        }

        fun getResourcesImagesFile(path: String): MutableMap<String?, String?> {
            val imgMap: MutableMap<String?, String?> = HashMap<String?, String?>()
            val resolver: ResourcePatternResolver = PathMatchingResourcePatternResolver()
            try {
                val resources = resolver.getResources(path)
                for (resource in resources) {
                    val bytes = FileCopyUtils.copyToByteArray(resource.inputStream)
                    val string = Base64Utils.encodeToString(bytes)
                    val filename = resource.filename
                    imgMap.put(filename, string)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return imgMap
        }
    }
}