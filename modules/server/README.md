# Server 模块

## 模块概述

Server模块是MagicDog项目的核心应用模块，基于Kotlin和Spring
Boot构建，提供了一个现代化的后端服务框架。该模块集成了项目中的所有插件功能，作为主要的应用入口点，支持分布式部署，并提供了一套完整的解决方案，包括用户认证、验证码、数据权限、日志记录等企业级功能。

## 功能特性

- **现代化技术栈**: 基于Kotlin 2.0.0和Spring Boot 3.5.6构建
- **虚拟线程支持**: 启用Java 21的虚拟线程功能，提高并发性能
- **延迟初始化**: 支持Spring Boot的延迟初始化，加快启动速度
- **多数据库支持**: 集成MySQL数据库和MyBatis-Plus ORM框架
- **Redis缓存**: 使用Redisson作为Redis客户端，提供高性能缓存
- **安全认证**: 集成JWT认证授权系统，支持Token自动刷新
- **验证码系统**: 集成AjCaptcha，支持滑块、点击、选字等多种验证码类型
- **日志管理**: 完整的日志管理系统，支持开发和生产环境不同配置
- **数据权限控制**: 实现数据范围过滤功能
- **文件上传**: 支持文件上传功能
- **异步任务执行**: 集成异步任务执行服务

## 主要组件说明

### Application.kt

主应用启动类，使用`@SpringBootApplication`注解标记为Spring Boot应用，并通过`@Import(ApplicationConfig::class)`导入应用配置。

### ApplicationConfig.kt

应用配置类，负责以下配置：

- 组件扫描配置（扫描`com.makebk`包下的组件）
- Jackson JSON序列化配置（启用枚举toString序列化）
- 异步任务执行器初始化

### 配置文件

- **application.yml**: 主应用配置，包括端口、线程池、文件上传限制等
- **database.yml**: 数据库和Redis配置，包含MySQL连接和Redisson配置
- **mybatis.yml**: MyBatis-Plus配置，包括实体扫描、逻辑删除等
- **security.yml**: JWT认证和权限配置
- **logger.yml**: 日志系统配置

## 配置说明

### 应用配置 (application.yml)

- 应用名称: server
- 激活配置文件: dev
- 服务器端口: 7777
- 最大连接数: 20000
- 线程池: 最大600个线程，最小空闲100个
- 文件上传限制: 单文件最大10MB，请求最大50MB
- 日期格式: yyyy-MM-dd HH:mm:ss (GMT+8时区)

### 数据库配置 (database.yml)

- 数据库: MySQL，连接池使用HikariCP
- Redis: 使用Redisson，地址为100.78.145.96:6379，数据库4
- 集成P6Spy进行SQL监控

### MyBatis配置 (mybatis.yml)

- 实体扫描包: `com.makebk.domain.core.entits`, `com.makebk.domain.analysis.entits`
- 启用逻辑删除功能，字段名为`is_del`
- 支持驼峰命名转换

### 安全配置 (security.yml)

- JWT Token有效期: 86400秒（24小时）
- 刷新检查时间: 604800秒（7天）
- 公共接口（无需认证）: 登录、验证码、文件上传等
- 默认所有接口需要认证

## 使用方法

### 本地开发环境搭建

1. 确保环境要求满足：
    - Java 21
    - Kotlin 2.0.0
    - Gradle 8.x
    - MySQL 5.7+
    - Redis 5.0+

2. 配置数据库连接
    - 修改 `modules/server/src/main/resources/database.yml` 中的数据库连接信息
    - 确保MySQL和Redis服务正在运行

3. 构建项目
   ```bash
   ./gradlew build
   ```

4. 运行应用
   ```bash
   ./gradlew bootRun
   ```

5. 访问应用
    - 应用启动后访问 `http://localhost:7777`

### 打包和部署

1. 打包应用
   ```bash
   ./gradlew bootJar
   ```
   生成的JAR包位于 `modules/server/build/libs/` 目录下。

2. 运行打包后的应用
   ```bash
   java -jar modules/server/build/libs/server-*.jar
   ```

### 配置文件管理

- 开发环境配置: 使用 `spring.profiles.active=dev`
- 生产环境配置: 使用 `spring.profiles.active=prod`
- 可通过环境变量或启动参数修改配置

## 与其他模块的关系

### 依赖的插件模块

Server模块依赖以下插件模块：

- **captcha**: 提供验证码功能，支持滑块、点击等多种验证码类型
- **common**: 提供通用功能支持，包括Web、AOP、缓存、执行器等基础服务，以及数据库连接池配置
- **datascope**: 提供数据权限控制功能，实现数据范围过滤
- **logger**: 提供日志管理功能，支持开发和生产环境不同日志配置
- **security**: 提供JWT认证授权系统，支持多种权限控制模式

### 与领域模块的关系

- **domain/core**: Server模块使用核心业务实体
- **domain/analysis**: Server模块使用分析相关实体

### 与集群模块的关系

- **cluster/monitor**: 提供系统监控功能
- **cluster/router**: 提供服务路由功能

Server模块作为整个项目的核心应用，整合了所有插件模块的功能，为上层业务提供统一的服务接口。