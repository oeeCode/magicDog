# MagicDog

MagicDog 是一个基于 Kotlin 和 Spring Boot 的现代化后端服务框架，采用多模块架构设计，支持分布式部署。该项目提供了一套完整的解决方案，包括用户认证、验证码、数据权限、日志记录等企业级功能。

## 技术栈

- **编程语言**: Kotlin 2.0.0
- **JVM 版本**: Java 21
- **框架**: Spring Boot 3.5.6
- **数据库**: MySQL, MyBatis-Plus
- **缓存**: Redis, Redisson
- **构建工具**: Gradle
- **认证授权**: JWT
- **验证码**: AjCaptcha
- **日志系统**: Logback

## 项目结构

```
magicDog/
├── cluster/                 # 集群相关模块
│   ├── monitor/            # 监控模块
│   └── router/             # 路由模块
├── domain/                 # 领域模型
│   ├── analysis/           # 分析相关实体
│   └── core/               # 核心实体
├── modules/                # 主要功能模块
│   └── server/             # 服务器主模块
├── plugins/                # 插件模块
│   ├── captcha/            # 验证码插件
│   ├── common/             # 通用功能插件
│   ├── datascope/          # 数据权限插件
│   ├── logger/             # 日志插件
│   ├── ruleengine/         # 规则引擎插件
│   ├── sdk/                # SDK插件
│   └── security/           # 安全插件
├── gradle/                 # Gradle包装器
├── .gitea/                 # Gitea工作流配置
├── build.gradle.kts        # 项目构建配置
├── settings.gradle.kts     # 项目设置
└── gradle.properties       # Gradle属性配置
```

## 模块介绍

### 核心模块

#### server (modules/server)

- 项目主应用模块
- 集成所有插件功能
- 端口: 7777
- 启用虚拟线程和延迟初始化

#### 领域模块

- **domain/core**: 核心业务实体
- **domain/analysis**: 分析相关实体

#### 集群模块

- **cluster/monitor**: 系统监控模块
- **cluster/router**: 服务路由模块

### 插件系统

#### common (plugins/common)

- 提供通用功能支持
- Web、AOP、缓存、执行器等基础服务
- 数据库连接池配置（HikariCP）
- 集成Redisson作为Redis客户端
- 使用MyBatis-Plus作为ORM框架
- 集成P6Spy用于SQL监控

#### captcha (plugins/captcha)

- 基于AjCaptcha的验证码系统
- 支持滑块、点击、选字等多种验证码类型
- Redis缓存验证码信息
- 可配置的图片路径和验证参数

#### security (plugins/security)

- JWT认证授权系统
- 支持多种权限控制模式
- 预设公共接口和认证接口规则
- Token有效期管理

#### logger (plugins/logger)

- 日志管理系统
- 支持开发和生产环境不同日志配置
- 集成Logback日志框架

#### datascope (plugins/datascope)

- 数据权限控制插件
- 实现数据范围过滤功能

#### ruleengine (plugins/ruleengine)

- 规则引擎插件
- 用于业务规则的动态配置和执行

#### sdk (plugins/sdk)

- 提供SDK支持
- 便于外部系统集成

## 快速开始指南

### 环境要求

- Java 21
- Kotlin 2.0.0
- Gradle 8.x
- MySQL 5.7+
- Redis 5.0+

### 本地开发环境搭建

1. 克隆项目到本地

```bash
git clone https://github.com/your-username/magicDog.git
cd magicDog
```

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

### 配置说明

#### 数据库配置

项目使用MySQL作为主数据库，配置在 `database.yml` 文件中：

- 支持HikariCP连接池
- 集成P6Spy进行SQL监控
- 配置Redisson作为Redis客户端

#### MyBatis配置

- 实体类扫描路径：`com.makebk.domain.core.entits`, `com.makebk.domain.analysis.entits`
- 启用逻辑删除功能
- 支持驼峰命名转换

#### 安全配置

- JWT Token有效期：86400秒（24小时）
- 刷新检查时间：604800秒（7天）
- 预设公共接口（无需认证）：登录、验证码、文件上传等

## 构建说明

### 构建项目

```bash
./gradlew clean build
```

### 运行测试

项目包含多种测试类型：

- 默认测试：`./gradlew test`
- 单元测试：`./gradlew unitTest`
- 上下文测试：`./gradlew contextTest`
- 文档测试：`./gradlew restDocsTest`
- 开发测试：`./gradlew developTest`

### 打包应用

```bash
./gradlew bootJar
```

生成的JAR包位于 `modules/server/build/libs/` 目录下。

### Native 打包

项目支持使用 GraalVM 将应用编译为原生可执行文件，提供更快的启动速度和更低的内存占用。

#### 环境准备

在进行 Native 打包之前，需要安装 GraalVM 和 native-image 工具：

1. 安装 GraalVM（推荐使用 21.x 版本）
2. 安装 native-image 工具：
   ```bash
   gu install native-image
   ```

或者使用 SDKMAN 安装：

```bash
# 安装 GraalVM
sdk install java 21.0.8-graal
sdk use java 21.0.8-graal

# 安装 native-image
gu install native-image
```

#### 构建原生可执行文件

```bash
./gradlew :modules:server:nativeCompile
```

生成的原生可执行文件位于 `modules/server/build/nativeCompile/` 目录下。

运行原生可执行文件：

```bash
./modules/server/build/nativeCompile/server
```

#### 故障排除

如果遇到 "Cannot query the value of property 'javaLauncher'" 错误，请确保：

- 已正确安装 GraalVM（而非普通 JDK）
- native-image 工具已安装
- 环境变量正确配置

### Docker部署

（如果项目支持Docker）

```bash
# 构建Docker镜像
docker build -t magicdog:latest .

# 运行容器
docker run -d -p 7777:7777 --name magicdog magicdog:latest
```

## 许可证信息

本项目遵循 [MIT 许可证](LICENSE)。

MIT许可证允许：

- 商业使用
- 修改
- 分发
- 私人使用

在使用本项目时，请保留原作者的版权声明和许可证声明。

---

## 贡献指南

我们欢迎社区贡献！如果您想为项目做出贡献，请：

1. Fork 项目
2. 创建功能分支
3. 提交您的更改
4. 发起 Pull Request

## 支持

如果遇到问题，请通过以下方式寻求帮助：

- 提交 Issue
- 查看文档

## 版本历史

- v0.0.1-SNAPSHOT: 初始开发版本