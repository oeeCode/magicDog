# Logger Plugin

响应式 AOP 拦截器插件，支持拦截接口请求数据并返回 MonoOnErrorResume。

## 功能特性

- 拦截返回 `Mono` 类型的方法
- 支持 `onErrorResume` 错误恢复机制
- 与现有日志系统集成
- 响应式编程友好

## 核心组件

### MonoResponseAopInterceptor

专门用于拦截返回 `Mono` 类型的方法的 AOP 拦截器，支持 `onErrorResume` 功能。

- 自动识别返回 `Mono` 类型的方法
- 提供错误恢复机制
- 集成日志记录功能

### 配置

通过 `MonoAopAutoConfiguration` 自动配置，利用 `META-INF/spring.factories` 自动注册。

## 使用示例

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
                Mono.just(ResponseEntity.ok(emptyList()))
            }
    }
}
```

## 拦截器执行顺序

- `MonoResponseAopInterceptor` (优先级: `HIGHEST_PRECEDENCE - 1`)
- `AopAccessLoggerSupport` (优先级: `HIGHEST_PRECEDENCE`)

确保响应式处理逻辑在日志记录之前执行。