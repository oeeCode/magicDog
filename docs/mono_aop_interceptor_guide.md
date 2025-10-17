# Mono AOP 拦截器使用指南

## 简介

本项目实现了一个专门用于拦截返回 `Mono` 类型的方法的 AOP 拦截器，支持 `onErrorResume` 功能。该拦截器可以捕获响应式编程中的错误，并提供错误恢复机制，同时记录接口请求日志。

## 核心功能

### 1. Mono 类型方法拦截
- 自动识别返回 `Mono` 或其子类型的方法
- 在方法执行前后进行拦截处理

### 2. onErrorResume 支持
- 提供错误恢复机制
- 在发生错误时执行自定义逻辑
- 记录错误信息到日志系统

### 3. 日志记录
- 继承现有日志系统功能
- 记录请求参数、响应结果、错误信息等
- 支持与现有日志监听器集成

## 实现组件

### MonoResponseAopInterceptor
核心 AOP 拦截器，负责：
- 拦截返回 `Mono` 类型的方法
- 应用 `onErrorResume` 逻辑
- 集成日志记录功能

### 配置类
- `MonoAopAutoConfiguration`：自动配置类
- 通过 `spring.factories` 自动注册

### 示例控制器
- `MonoTestController`：演示如何使用响应式接口

## 使用方法

### 1. 业务接口实现
在服务类中添加返回 `Mono` 类型的方法：

```kotlin
interface TestService : IBaseService<Test> {
    fun listMono(): Mono<List<Test>>
}

@Service
class TestServiceImpl : IBaseServiceImpl<TestMapper, Test>(), TestService {
    override fun listMono(): Mono<List<Test>> {
        return Mono.fromCallable {
            // 业务逻辑
            list()
        }.subscribeOn(
            reactor.core.scheduler.Schedulers.fromExecutor(
                java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()
            )
        )
    }
}
```

### 2. 控制器实现
在控制器中使用 `Mono` 类型的返回值：

```kotlin
@RestController
@RequestMapping("/mono")
class MonoTestController(
    val testService: TestService,
) {
    @GetMapping("/test")
    fun getMonoTest(): Mono<ResponseEntity<List<Test>>> {
        return testService.listMono()
            .map { ResponseEntity.ok(it) }
            .onErrorResume { ex ->
                // 错误恢复逻辑
                println("Error occurred: ${ex.message}")
                Mono.just(ResponseEntity.ok(emptyList()))
            }
    }
}
```

## 拦截器执行顺序

- `MonoResponseAopInterceptor` (优先级: `HIGHEST_PRECEDENCE - 1`)
- `AopAccessLoggerSupport` (优先级: `HIGHEST_PRECEDENCE`)

`MonoResponseAopInterceptor` 会先执行，确保在日志记录之前应用响应式处理逻辑。

## 错误处理

当方法执行发生错误时：
1. `onErrorResume` 捕获异常
2. 记录异常信息到日志系统
3. 执行自定义错误恢复逻辑
4. 可选择返回默认值或重新抛出异常

## 配置自动注册

通过 `META-INF/spring.factories` 自动注册：

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.makebk.logger.config.AopAccessLoggerSupportAutoConfiguration,\
com.makebk.logger.config.MonoAopAutoConfiguration
```

## 注意事项

1. 确保方法返回类型为 `Mono` 或其子类型
2. 拦截器只对返回 `Mono` 的方法生效
3. 保持与其他 AOP 拦截器的兼容性
4. 错误恢复逻辑应避免过于复杂，以免影响性能