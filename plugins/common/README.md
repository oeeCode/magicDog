# Common 通用功能模块

## 模块概述

Common 模块是 MagicDog 项目的基础功能模块，为整个系统提供通用的基础设施和公共服务。该模块集成了 Spring Boot
Web、AOP、缓存、执行器等基础服务，同时提供了数据库连接、Redis 缓存、MyBatis-Plus ORM 等核心功能支持，是其他插件模块和主应用模块的底层依赖。

## 功能特性

- **Web 支持**：集成 Spring Boot Web 框架，提供 RESTful API 支持
- **AOP 面向切面编程**：支持面向切面编程，便于实现日志、权限等功能
- **缓存管理**：集成 Spring Boot 缓存机制，支持多种缓存实现
- **异步任务执行**：提供异步任务执行服务，支持并发处理
- **数据库支持**：集成 HikariCP 连接池、MyBatis-Plus、P6Spy SQL 监控
- **Redis 集成**：使用 Redisson 作为 Redis 客户端，支持分布式缓存
- **系统监控**：集成 Spring Boot Actuator，提供健康检查和监控功能
- **数据验证**：集成 Bean Validation，支持参数校验功能

## 主要组件说明

### 核心组件

- `Constant`：常量定义类，定义了项目的基础常量
- `AsyncTaskExecutorService`：异步任务执行服务，提供异步方法执行能力
- `package-info.java`：包声明文件，定义了模块的包结构

### 依赖库

- `spring-boot-starter-web`：Web 应用开发基础
- `spring-boot-starter-aop`：面向切面编程支持
- `spring-boot-starter-cache`：缓存抽象支持
- `spring-boot-starter-actuator`：应用监控和管理
- `spring-boot-starter-jdbc`：JDBC 支持
- `spring-boot-starter-validation`：Bean 验证支持
- `aspectj`：AOP 编程框架
- `hutool-all`：Java 工具类库
- `mybatis-plus`：增强的 MyBatis 框架
- `p6spy`：SQL 监控和日志工具
- `redisson-spring-boot-starter`：Redis 客户端

## 配置说明

Common 模块本身不提供独立的配置文件，其配置主要通过以下方式实现：

### 数据库配置

通过 `application.yml` 或 `database.yml` 配置数据源：

```yaml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/magicdog
    username: root
    password: password
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 60000
      max-lifetime: 1800000
```

### Redis 配置

通过 `application.yml` 配置 Redis 连接：

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    timeout: 2000ms
    database: 0
```

### 缓存配置

通过 `application.yml` 配置缓存类型：

```yaml
spring:
  cache:
    type: redis  # 或其他缓存类型
```

## 使用方法

### 1. 作为依赖引入

在其他模块的 `build.gradle.kts` 中添加依赖：

```kotlin
dependencies {
    implementation(project(":plugins:common"))
    // 或使用 api 依赖
    api(project(":plugins:common"))
}
```

### 2. 使用异步任务功能

通过依赖注入使用异步任务执行服务：

```kotlin
@Service
class SomeService(val asyncTaskExecutorService: AsyncTaskExecutorService) {
    
    fun triggerAsyncTask() {
        asyncTaskExecutorService.run()
    }
}
```

### 3. 使用缓存功能

在服务类中使用缓存注解：

```kotlin
@Service
class SomeService {
    
    @Cacheable(value = "cacheName", key = "#id")
    fun getData(id: Long): Data {
        // 实现数据获取逻辑
        return data
    }
    
    @CacheEvict(value = "cacheName", key = "#id")
    fun updateData(id: Long, data: Data) {
        // 实现数据更新逻辑
    }
}
```

### 4. 使用 AOP 功能

创建切面类实现横切关注点：

```kotlin
@Aspect
@Component
class LogAspect {
    
    @Around("@annotation(Log)")
    fun logExecution(joinPoint: ProceedingJoinPoint): Any? {
        // 实现日志记录逻辑
        return joinPoint.proceed()
    }
}
```

## 与其他模块的关系

- **依赖关系**：作为基础模块，被其他插件模块（如 captcha、security、logger 等）和主应用模块（server）依赖
- **数据库集成**：为整个项目提供数据库连接和 ORM 支持，与 MyBatis-Plus 框架深度集成
- **缓存集成**：通过 Redisson 为整个系统提供分布式缓存能力
- **AOP 支持**：为其他模块提供面向切面编程能力，支持日志、权限、事务等横切关注点
- **Web 基础**：为整个项目提供 Web 开发基础，包括 RESTful API、参数校验等功能
- **系统监控**：通过 Actuator 为整个系统提供监控和健康检查功能