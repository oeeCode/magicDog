# Captcha 验证码模块

## 模块概述

Captcha 模块是一个基于 `anji-captcha` 库开发的验证码服务插件，为系统提供图形验证码功能。该模块支持滑动拼图、点选文字等多种验证码类型，通过
Redis 缓存验证信息，具备防刷和限流功能。

## 功能特性

- **多种验证码类型**：支持滑动拼图验证码、点选文字验证码等多种验证方式
- **Redis 缓存支持**：使用 Redis 存储验证码信息，支持分布式部署
- **防刷限流机制**：提供接口请求频率限制，防止恶意刷取验证码
- **AES 加密**：支持坐标 AES 加密，增强安全性
- **水印功能**：可自定义水印文字和字体
- **历史数据清理**：支持定时清理过期验证码数据

## 主要组件说明

### 核心组件

- `EnableCaptcha`：启用验证码功能的注解，通过 `@Import` 导入自动配置
- `CaptchaServiceAutoConfiguration`：验证码服务自动配置类，根据配置创建 `CaptchaService` 实例
- `AjCaptchaProperties`：验证码配置属性类，定义了所有可配置参数
- `CaptchaCacheServiceRedisImpl`：基于 Redis 的验证码缓存服务实现
- `CaptchaEnums`：验证码相关错误码枚举

### 配置文件

- `captcha.yml`：默认配置文件，定义了验证码的各项参数
- `com.anji.captcha.service.CaptchaCacheService`：服务接口实现配置

## 配置说明

模块通过 `aj.captcha` 前缀进行配置，主要配置项如下：

```yaml
aj:
  captcha:
    jigsaw: classpath:images/jigsaw                    # 滑动拼图底图路径
    pic-click: classpath:images/pic-click              # 点选文字底图路径
    cache-type: redis                                  # 缓存类型 (redis/local/other)
    cache-number: 1000                                 # 本地缓存阈值
    timing-clear: 180                                  # 定时清理过期缓存时间（秒）
    type: default                                      # 验证码类型
    water-mark: 五常市人民法院报名系统                 # 水印文字
    slip-offset: 5                                     # 滑动拼图允许误差偏移量
    aes-status: true                                   # 是否开启AES加密坐标
    interference-options: 2                            # 滑块干扰项 (0/1/2)
    font-style: 1                                      # 字体样式
    font-size: 18                                      # 字体大小
    history-data-clear-enable: false                   # 是否启用历史数据清除
    req-frequency-limit-enable: false                  # 是否启用请求频率限制
    req-get-lock-limit: 5                              # 一分钟内获取验证码失败锁定次数
    req-get-lock-seconds: 360                          # 锁定时间（秒）
    req-get-minute-limit: 30                           # 一分钟内获取验证码限制次数
    req-check-minute-limit: 60                         # 一分钟内检查验证码限制次数
    req-verify-minute-limit: 60                        # 一分钟内验证验证码限制次数
```

## 使用方法

### 1. 启用验证码功能

在启动类或配置类上添加 `@EnableCaptcha` 注解：

```kotlin
@SpringBootApplication
@EnableCaptcha
class Application {
    // ...
}
```

### 2. 配置验证码参数

在 `application.yml` 中配置验证码参数：

```yaml
aj:
  captcha:
    cache-type: redis
    water-mark: "我的水印"
    type: default
    # ... 其他配置项
```

### 3. 使用验证码服务

通过依赖注入获取 `CaptchaService` 实例，调用相应方法生成和验证验证码：

```kotlin
@Service
class SomeService(val captchaService: CaptchaService) {
    
    fun getCaptcha() {
        val result = captchaService.get()
        // 处理验证码生成结果
    }
    
    fun verifyCaptcha(pointJson: String) {
        val result = captchaService.checkVerify(pointJson)
        // 处理验证结果
    }
}
```

## 与其他模块的关系

- **依赖关系**：依赖于 `common` 模块提供的基础功能
- **缓存集成**：与 Redis 集成，使用 Redisson 客户端进行缓存操作
- **Spring Boot 集成**：通过 Spring Boot 自动配置机制，提供开箱即用的验证码服务
- **第三方库**：基于 `anji-captcha` 库进行封装，扩展了缓存和配置功能