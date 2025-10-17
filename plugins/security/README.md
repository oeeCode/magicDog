# Security 模块

## 模块概述

Security 模块是 MakeBK 项目的安全认证与授权组件，提供基于 JWT（JSON Web Token）的用户认证和权限控制功能。该模块集成了 Shiro
框架，实现了细粒度的权限管理和访问控制，为系统提供全面的安全防护。

## 功能特性

- **JWT 认证**：基于 JSON Web Token 的无状态认证机制，支持分布式部署
- **权限控制**：灵活的 URL 级别权限配置，支持匿名访问、登录验证等策略
- **Token 管理**：自动化的 Token 过期和刷新机制，提升用户体验
- **多平台支持**：支持不同平台的请求头识别，便于多端统一管理
- **缓存集成**：集成 Shiro 缓存机制，提升权限验证性能
- **本地化支持**：支持本地化请求头识别

## 主要组件说明

### 配置文件

- `security.yml`：安全模块的 YAML 配置文件，定义了 JWT 参数和权限配置

### 核心配置

- **JWT 配置**：包括 Token 头部、过期时间、刷新检查时间、密钥等参数
- **权限配置**：URL 路径与访问策略的映射关系

## 配置说明

### security.yml

```yaml
jwt:
  token-header: Authorization           # Token 请求头名称
  platformHeader: platform            # 平台标识请求头
  platform: server                    # 当前平台名称
  local: X-Local                      # 本地化请求头
  expire: 86400                       # Token 过期时间（秒）
  refreshCheckTime: 604800            # Token 刷新检查时间（秒）
  shiroCacheExpireTime: 86400        # Shiro 缓存过期时间（秒）
  secretKey: xx1WET12^%3^(WE45        # JWT 密钥
permission:
  perms:
    - key: /file/uploadEditor        # 文件上传编辑器接口
      value: anon                     # 匿名访问
    - key: /captcha                  # 验证码接口
      value: anon                     # 匿名访问
    - key: /logout                   # 登出接口
      value: logout                   # 登出策略
    - key: /login                    # 登录接口
      value: anon                     # 匿名访问
    - key: /**                       # 其他所有接口
      value: auth                     # 需要认证访问
```

- `jwt.token-header`：指定用于传输 JWT Token 的 HTTP 请求头
- `jwt.expire`：JWT Token 的过期时间（单位：秒）
- `jwt.secretKey`：JWT 签名密钥，用于验证 Token 的合法性
- `permission.perms`：定义 URL 路径的访问权限策略
    - `anon`：允许匿名访问
    - `auth`：需要身份认证
    - `logout`：登出策略

## 使用方法

### 1. 模块集成

在 `application.yml` 中导入安全配置：

```yaml
spring:
  config:
    import:
      - security.yml  # 导入安全配置
```

### 2. 权限配置

根据业务需求修改 `security.yml` 中的权限配置，定义不同 URL 的访问策略：

```yaml
permission:
  perms:
    - key: /public/**
      value: anon          # 公共接口，允许匿名访问
    - key: /api/login
      value: anon          # 登录接口，允许匿名访问
    - key: /api/logout
      value: logout        # 登出接口
    - key: /**
      value: auth          # 其他接口需要认证
```

### 3. Token 使用

客户端在请求需要认证的接口时，需要在请求头中添加 JWT Token：

```
Authorization: <JWT Token>
```

### 4. 自定义权限

根据业务需求，可以在 `permission.perms` 列表中添加新的 URL 权限配置：

```yaml
permission:
  perms:
    - key: /api/admin/**
      value: auth          # 管理员接口需要认证
    - key: /api/user/profile
      value: auth          # 用户资料接口需要认证
```

## 与其他模块的关系

- **Server 模块**：Security 模块被 Server 模块依赖，为整个应用提供安全认证服务
- **Common 模块**：可能与通用模块协同工作，共享基础的安全常量和工具类
- **Captcha 模块**：登录流程可能与验证码模块集成，增强安全性
- **Spring Boot 集成**：通过 Spring Boot 的配置机制，实现自动化的安全配置

Security 模块作为系统的核心安全组件，为其他业务模块提供统一的身份认证和权限控制能力，确保整个系统的安全性和可维护性。