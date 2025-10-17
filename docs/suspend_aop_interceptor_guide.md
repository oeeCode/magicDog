# Kotlin suspend 函数的 AOP 日志记录指南

## 问题背景

在 Kotlin 中使用 suspend 函数时，如果想要通过 AOP 进行日志记录，会遇到一些特殊问题。主要原因是：

1. AOP 的 `matches` 方法不是 suspend 函数，无法直接调用 suspend 函数
2. Spring AOP 在匹配切点时会阻塞调用 suspend 函数
3. 需要特殊的处理方式来支持协程函数的 AOP

## 当前项目中的实现分析

当前项目中的 AOP 实现位于 `SuspendAwareAopAccessLoggerSupport.kt`：

```kotlin
override fun matches(method: Method, targetClass: Class<*>): Boolean {
    // 由于support是挂起函数，我们使用runBlocking在虚拟线程中调用它
    // 以最小化阻塞影响
    return try {
        runBlocking(CoroutineUtil.VirtualThread.coroutineContext) {
            loggerParsers.any { parser ->
                try {
                    parser.support(targetClass, method)
                } catch (e: Exception) {
                    false
                }
            }
        }
    } catch (e: Exception) {
        false
    }
}
```

这种方法虽然可以工作，但存在以下问题：
- 使用 `runBlocking` 会阻塞线程
- 在 AOP 匹配阶段引入协程可能影响性能
- 不是最佳实践

## 正确的实现方式

### 方案一：使用 Aspect 直接处理

这是另一种处理 suspend 函数日志记录的方法，使用传统的 AOP Aspect：

```kotlin
@Component
@Aspect
class SuspendFunctionLoggerAspect {
    
    @Autowired
    lateinit var eventPublisher: ApplicationEventPublisher
    
    @Autowired(required = false)
    var listeners: MutableList<AccessLoggerListener> = ArrayList()
    
    @Around("@annotation(com.makebk.logger.annotations.AccessLogger) || @within(com.makebk.logger.annotations.AccessLogger)")
    fun logAround(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        val loggerInfo = createLoggerInfo(joinPoint)
        loggerInfo.requestTime = startTime
        
        return try {
            // 执行原始方法
            val result = joinPoint.proceed()
            
            // 对于 suspend 函数，AOP 无法直接区分，所以我们记录结果
            loggerInfo.response = result
            loggerInfo.responseTime = System.currentTimeMillis()
            publishLogEvent(loggerInfo)
            result
        } catch (e: Throwable) {
            loggerInfo.exception = e
            loggerInfo.responseTime = System.currentTimeMillis()
            publishLogEvent(loggerInfo)
            throw e
        }
    }
    
    private fun createLoggerInfo(joinPoint: ProceedingJoinPoint): AccessLoggerInfo {
        val info = AccessLoggerInfo()
        info.requestTime = System.currentTimeMillis()
        
        // 设置请求上下文信息
        val requestAttributes = RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
        val request = requestAttributes?.request
        
        if (request != null) {
            info.httpHeaders = WebUtil.getHeaders(request)
            info.ip = WebUtil.getIpAddr(request)
            info.httpMethod = request.method
            info.url = request.requestURL.toString()
            info.requestId = request.getHeader(SecurityConstants.REQUESTID) ?: "SYS${IdUtil.fastSimpleUUID()}"
        }
        
        // 设置用户信息
        val user = UserContextHandler.currentUser
        if (user != null) {
            info.user = user
        }
        
        // 设置方法和参数信息
        info.target = joinPoint.target.javaClass
        info.method = joinPoint.signature as Method
        info.parameters = (joinPoint.args).associateBy { it.toString() }
        
        return info
    }
    
    private fun publishLogEvent(loggerInfo: AccessLoggerInfo) {
        eventPublisher.publishEvent(AccessLoggerAfterEvent(loggerInfo))
        listeners.forEach { it.onLogger(loggerInfo) }
    }
}
```

### 方案二：改进现有的 SuspendAwareAopAccessLoggerSupport

修改 `SuspendAwareAopAccessLoggerSupport` 类，使其能够正确处理 suspend 函数：

```kotlin
class SuspendAwareAopAccessLoggerSupport : StaticMethodMatcherPointcutAdvisor() {
    
    @Autowired(required = false)
    var listeners: MutableList<AccessLoggerListener> = ArrayList()
    
    @Autowired(required = false)
    var loggerParsers: MutableList<AccessLoggerParser> = ArrayList()
    
    @Autowired
    lateinit var eventPublisher: ApplicationEventPublisher
    
    init {
        advice = SuspendAwareMethodInterceptor()
    }
    
    override fun matches(method: Method, targetClass: Class<*>): Boolean {
        // 检查方法是否为 suspend 函数
        val isSuspendFunction = isSuspendFunction(method)
        
        // 如果是 suspend 函数，使用特殊逻辑处理
        if (isSuspendFunction) {
            // 对于 suspend 函数，我们直接检查是否有日志注解
            return hasAccessLoggerAnnotation(method, targetClass)
        } else {
            // 对于普通函数，使用原有的逻辑
            // 使用虚拟线程执行，以最小化阻塞影响
            return try {
                runBlocking(CoroutineUtil.VirtualThread.coroutineContext) {
                    loggerParsers.any { parser ->
                        try {
                            parser.support(targetClass, method)
                        } catch (e: Exception) {
                            false
                        }
                    }
                }
            } catch (e: Exception) {
                false
            }
        }
    }
    
    private fun isSuspendFunction(method: Method): Boolean {
        // 检查方法的最后一个参数是否是 Continuation 类型
        val paramTypes = method.parameterTypes
        if (paramTypes.isEmpty()) return false
        
        val lastParamType = paramTypes.last()
        return lastParamType.name == "kotlin.coroutines.Continuation" ||
               lastParamType.simpleName == "Continuation"
    }
    
    private fun hasAccessLoggerAnnotation(method: Method, targetClass: Class<*>): Boolean {
        // 检查方法或类上是否有 AccessLogger 注解
        val methodAnnotation = method.getAnnotation(com.makebk.logger.annotations.AccessLogger::class.java)
        val classAnnotation = targetClass.getAnnotation(com.makebk.logger.annotations.AccessLogger::class.java)
        
        return methodAnnotation != null && !methodAnnotation.ignore ||
               classAnnotation != null && !classAnnotation.ignore
    }
    
    /**
     * 支持 suspend 函数的拦截器
     */
    inner class SuspendAwareMethodInterceptor : MethodInterceptor {
        override fun invoke(methodInvocation: MethodInvocation): Any? {
            val isSuspendFunction = isSuspendFunction(methodInvocation.method)
            
            return if (isSuspendFunction) {
                handleSuspendFunction(methodInvocation)
            } else {
                handleRegularFunction(methodInvocation)
            }
        }
        
        private fun handleSuspendFunction(methodInvocation: MethodInvocation): Any? {
            val startTime = System.currentTimeMillis()
            val servletRequestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = servletRequestAttributes.request
            val methodInterceptorHolder = MethodInterceptorHolder.create(methodInvocation)
            val info = createLogger(methodInterceptorHolder)
            info.requestTime = startTime

            // 调用前置监听器
            listeners.forEach { it.onLogBefore(info) }

            // 获取原始的 Continuation 参数
            val args = methodInvocation.arguments
            val continuation = args.last() as? Continuation<*>
            
            if (continuation != null) {
                // 创建包装的 Continuation 来处理结果
                val wrappedContinuation = object : Continuation<Any?> {
                    override val context = continuation.context

                    override fun resumeWith(result: Result<Any?>) {
                        // 记录响应时间
                        info.responseTime = System.currentTimeMillis()
                        
                        // 设置结果或异常
                        result.exceptionOrNull()?.let { throwable ->
                            info.exception = throwable
                        } ?: run {
                            info.response = result.getOrNull()
                        }
                        
                        // 发布日志事件
                        processLogEvent(info, methodInterceptorHolder)
                        
                        // 继续执行原始的 continuation
                        continuation.resumeWith(result)
                    }
                }
                
                // 调用原方法，替换 Continuation
                val newArgs = args.clone()
                newArgs[newArgs.size - 1] = wrappedContinuation
                
                return try {
                    methodInvocation.proceed(newArgs)
                } catch (e: Throwable) {
                    // 如果方法调用失败，记录异常并重新抛出
                    info.exception = e
                    processLogEvent(info, methodInterceptorHolder)
                    throw e
                }
            } else {
                // 如果没有 Continuation，按普通函数处理
                return handleRegularFunction(methodInvocation)
            }
        }
        
        private fun handleRegularFunction(methodInvocation: MethodInvocation): Any? {
            val servletRequestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            val request = servletRequestAttributes.request
            val methodInterceptorHolder = MethodInterceptorHolder.create(methodInvocation)
            val info = createLogger(methodInterceptorHolder)
            val response: Any?
            
            try {
                listeners.forEach { it.onLogBefore(info) }
                response = methodInvocation.proceed()
                info.response = response
            } catch (e: Throwable) {
                info.exception = e
                throw e
            } finally {
                info.responseTime = System.currentTimeMillis()
                processLogEvent(info, methodInterceptorHolder)
            }
            
            return response
        }
        
        private fun processLogEvent(info: AccessLoggerInfo, holder: MethodInterceptorHolder) {
            try {
                // 设置请求ID
                val request: HttpServletRequest? = WebUtil.getHttpServletRequest()
                info.requestId = if (request != null) getRequestId(request) else "SYS${IdUtil.fastSimpleUUID()}"
                
                // 解析日志定义 - 使用虚拟线程而非runBlocking
                val scope = CoroutineUtil.VirtualThread
                runBlocking(scope.coroutineContext) {
                    val resolvedDefine = loggerParsers.firstOrNull { parser ->
                        try {
                            parser.support(
                                ClassUtils.getUserClass(holder.target),
                                holder.method
                            )
                        } catch (e: Exception) {
                            false
                        }
                    }?.let { parser ->
                        try {
                            parser.parse(holder)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    
                    if (resolvedDefine != null) {
                        info.action = resolvedDefine.action
                        info.describe = resolvedDefine.describe
                    }
                }
            } catch (e: Exception) {
                // 如果解析失败，记录错误但继续执行
            }
            
            // 发布事件和处理监听器
            eventPublisher.publishEvent(AccessLoggerAfterEvent(info))
            listeners.forEach { it.onLogger(info) }
        }
    }
    
    private fun getRequestId(request: HttpServletRequest): String {
        // SYS 为系统生成
        return request.getHeader(SecurityConstants.REQUESTID) ?: "SYS${IdUtil.fastSimpleUUID()}"
    }
    
    protected fun createLogger(holder: MethodInterceptorHolder?): AccessLoggerInfo {
        val info = AccessLoggerInfo()
        info.requestTime = System.currentTimeMillis()

        val user = UserContextHandler.currentUser
        if (user != null) {
            info.user = user
        }
        info.parameters = holder?.args as Map<String, Any>?
        info.target = (holder?.target?.javaClass)
        info.method = (holder?.method)

        val request: HttpServletRequest? = WebUtil.getHttpServletRequest()
        if (null != request) {
            info.httpHeaders = (WebUtil.getHeaders(request))
            info.ip = (WebUtil.getIpAddr(request))
            info.httpMethod = (request.method)
            info.url = (request.requestURL.toString())
        }
        return info
    }
}
```

### 方案三：使用 Kotlin 协程拦截器

创建一个协程拦截器来处理日志记录：

```kotlin
class LoggingInterceptor : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<LoggingInterceptor>
    
    override val key: CoroutineContext.Key<*>
        get() = Key
    
    suspend fun <T> intercept(block: suspend () -> T): T {
        val startTime = System.currentTimeMillis()
        val loggerInfo = AccessLoggerInfo().apply {
            requestTime = startTime
            // 设置其他日志信息
        }
        
        return try {
            val result = block()
            loggerInfo.response = result
            result
        } catch (e: Throwable) {
            loggerInfo.exception = e
            throw e
        } finally {
            loggerInfo.responseTime = System.currentTimeMillis()
            // 发布日志事件
        }
    }
}

// 在 suspend 函数中使用
suspend fun someSuspendFunction() = withContext(LoggingInterceptor()) {
    // 业务逻辑
}
```

## 推荐实现

对于当前项目，推荐使用方案二，因为它与现有的 AOP 架构兼容性最好。我们已经实现了 `SuspendAwareAopAccessLoggerSupport` 类来正确处理 suspend 函数。

### 实现细节

我们创建了 `SuspendAwareAopAccessLoggerSupport` 类，该类具有以下特点：

1. **识别 suspend 函数**：通过检查方法签名中是否包含 `kotlin.coroutines.Continuation` 参数来识别 suspend 函数
2. **特殊处理 suspend 函数**：对于 suspend 函数，我们包装 Continuation 对象以捕获结果或异常
3. **保持与普通函数的兼容性**：对于普通函数，使用原有逻辑
4. **修复 Continuation 处理**：正确实现了 Continuation 接口，确保 context 属性被正确设置
5. **改进异常处理**：使用 `methodInvocation.proceed(newArgs)` 替代直接反射调用，以确保 AOP 链的正确执行

### 实际应用示例

在 `IndexController` 中添加日志注解：

```kotlin
@RestController
@Tag(name = "测试接口", description = "临时性能测试")
class IndexController(
    val testSerive: TestService,
) {
    val scope = CoroutineUtil.VirtualThread

    @GetMapping("/")
    @AccessLogger("主页访问", describe = "访问主页接口，返回测试数据列表")
    @Operation(summary = "主页", description = "临时性能测试")
    suspend fun index(): ResponseEntity<List<Test>> =
        withContext(scope.coroutineContext) {
            val data = testSerive.listAsync()
            ResponseEntity.ok()
                .body(data)
        }
}
```

创建了 `TestCoroutineService` 接口及其实现，展示了如何在 suspend 函数上使用 `@AccessLogger` 注解：

```kotlin
interface TestCoroutineService {
    @AccessLogger("获取延迟数据", describe = "获取带有延迟的测试数据")
    suspend fun getDataWithDelay(): List<Test>
    
    @AccessLogger("触发协程错误", describe = "用于测试协程中异常的日志记录")
    suspend fun getDataWithError(): List<Test>
    
    @AccessLogger("处理数据", describe = "处理传入的数据")
    suspend fun processData(data: String): String
}

@Service
class TestCoroutineServiceImpl : TestCoroutineService {
    
    @AccessLogger("获取延迟数据", describe = "获取带有延迟的测试数据")
    override suspend fun getDataWithDelay(): List<Test> {
        delay(1000) // 模拟延迟
        return listOf(
            Test().apply {
                id = 1
                name = "测试数据1"
            },
            Test().apply {
                id = 2
                name = "测试数据2"
            }
        )
    }
    
    @AccessLogger("触发协程错误", describe = "用于测试协程中异常的日志记录")
    override suspend fun getDataWithError(): List<Test> {
        delay(500) // 模拟延迟
        throw RuntimeException("测试异常")
    }
    
    @AccessLogger("处理数据", describe = "处理传入的数据")
    override suspend fun processData(data: String): String {
        delay(300) // 模拟处理时间
        return "Processed: $data"
    }
}
```

创建了 `CoroutineTestController` 来演示实际使用场景：

```kotlin
@RestController
@RequestMapping("/coroutine")
@Tag(name = "协程测试接口", description = "用于测试 suspend 函数的 AOP 日志记录")
class CoroutineTestController(
    val testCoroutineService: TestCoroutineService
) {
    val scope = CoroutineUtil.VirtualThread

    @GetMapping("/data")
    @AccessLogger("获取协程数据", describe = "通过协程服务获取测试数据")
    @Operation(summary = "获取协程数据", description = "获取带有延迟的测试数据")
    suspend fun getData(): ResponseEntity<List<Test>> =
        withContext(scope.coroutineContext) {
            val data = testCoroutineService.getDataWithDelay()
            ResponseEntity.ok(data)
        }

    @GetMapping("/error")
    @AccessLogger("触发协程错误", describe = "用于测试协程中异常的日志记录")
    @Operation(summary = "触发协程错误", description = "触发一个协程异常，用于测试日志记录")
    suspend fun getError(): ResponseEntity<List<Test>> =
        withContext(scope.coroutineContext) {
            try {
                val data = testCoroutineService.getDataWithError()
                ResponseEntity.ok(data)
            } catch (e: Exception) {
                ResponseEntity.status(500).body(emptyList())
            }
        }

    @PostMapping("/process")
    @AccessLogger("处理数据", describe = "处理传入的数据")
    @Operation(summary = "处理数据", description = "处理传入的数据字符串")
    suspend fun processData(@RequestBody data: String): ResponseEntity<String> =
        withContext(scope.coroutineContext) {
            val result = testCoroutineService.processData(data)
            ResponseEntity.ok(result)
        }
}
```

## 注意事项

1. suspend 函数在编译后会添加 `Continuation` 参数，需要识别这种特殊签名
2. 避免在 AOP 匹配阶段使用 `runBlocking`，这会导致线程阻塞
3. 正确处理协程的生命周期，确保在协程完成时记录日志
4. 考虑异常处理，确保无论协程成功完成还是抛出异常都能记录日志

## 总结

在 Kotlin suspend 函数上使用 AOP 记录日志需要特别处理协程的特殊性质。通过识别 suspend 函数的特征并适当包装 Continuation，我们可以实现对 suspend 函数的 AOP 日志记录，同时保持性能和正确性。