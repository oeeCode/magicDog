package com.makebk.captcha

import com.makebk.captcha.config.CaptchaServiceAutoConfiguration
import org.springframework.context.annotation.Import
import java.lang.annotation.Inherited

@Inherited
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Import(CaptchaServiceAutoConfiguration::class)
@Retention(AnnotationRetention.RUNTIME)
annotation class EnableCaptcha()
