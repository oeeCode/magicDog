# DataScope 模块

## 模块概述

DataScope 模块是 MagicDog
项目的数据范围控制组件，提供基于用户权限的细粒度数据访问控制功能。该模块通过拦截数据库查询操作，自动为查询条件添加数据范围过滤，实现多租户、部门级数据隔离、角色级数据权限控制等功能，确保用户只能访问其权限范围内的数据。

## 功能特性

- **数据权限控制**：基于用户角色和组织架构的细粒度数据访问控制
- **多租户支持**：支持多租户环境下的数据隔离，确保租户间数据安全
- **动态过滤**：在查询执行时自动添加数据范围过滤条件，对业务代码透明
- **灵活配置**：支持多种数据范围策略配置，适应不同业务场景需求
- **高性能**：通过 MyBatis-Plus 拦截器机制实现，性能开销小
- **易于集成**：与 Spring Boot 和 MyBatis-Plus 深度集成，使用简单

## 主要组件说明

### 核心组件

- **DataScopeInterceptor**：数据范围拦截器，拦截 MyBatis 查询操作并添加范围过滤
- **DataScopeService**：数据范围服务，提供数据范围规则的计算和应用
- **DataScopeConfig**：数据范围配置类，定义数据范围控制的配置参数
- **DataScopeProperties**：配置属性类，从配置文件读取数据范围相关配置

### 配置组件

- **DataScopeAutoConfiguration**：自动化配置类，自动装配数据范围相关组件
- **DataScopeAspect**：AOP 切面，用于方法级别的数据范围控制

## 配置说明

### application.yml

```yaml
# 数据范围配置
datascope:
  enabled: true                    # 是否启用数据范围控制
  default-filter: true             # 是否启用默认过滤
  exclude-tables:                 # 排除数据范围控制的表列表
    - "sys_user"                  # 系统用户表
    - "sys_dept"                  # 系统部门表
  include-tables:                 # 包含数据范围控制的表列表（为空表示全部表）
  filter-column: "dept_id"        # 数据范围过滤字段（默认为部门ID）
  tenant-column: "tenant_id"      # 租户标识字段
  user-column: "create_user"      # 用户数据过滤字段
```

### 数据范围策略配置

```yaml
# 数据范围策略配置
datascope:
  strategy:
    # 全部数据策略
    all: 
      code: "1"
      name: "全部数据"
      description: "可以访问全部数据"
    
    # 本部门数据策略
    dept_only:
      code: "2"
      name: "本部门数据"
      description: "仅能访问本部门数据"
    
    # 本部门及子部门数据策略
    dept_sub:
      code: "3"
      name: "本部门及子部门数据"
      description: "可访问本部门及子部门数据"
    
    # 本人数据策略
    self:
      code: "4"
      name: "本人数据"
      description: "仅能访问本人创建的数据"
    
    # 自定义数据策略
    custom:
      code: "5"
      name: "自定义数据"
      description: "可访问自定义范围的数据"
```

## 使用方法

### 1. 模块集成

在 `application.yml` 中添加数据范围配置：

```yaml
spring:
  config:
    import:
      - datascope.yml  # 导入数据范围配置
```

### 2. 启用数据范围

在主应用类中启用数据范围功能：

```kotlin
@SpringBootApplication
@EnableDataScope  // 启用数据范围控制
class Application {
    fun main(args: Array<String>) {
        SpringApplication.run(Application::class.java, *args)
    }
}
```

### 3. 数据范围注解使用

在需要数据范围控制的接口或方法上使用注解：

```kotlin
@RestController
@RequestMapping("/api/data")
class DataController {
    
    @DataScope  // 启用数据范围控制
    @GetMapping("/list")
    fun getDataList(): List<Data> {
        // 查询逻辑，数据范围会自动应用
        return dataService.findAll()
    }
    
    @DataScope(filter = false) // 禁用当前方法的数据范围控制
    @GetMapping("/all")
    fun getAllData(): List<Data> {
        // 查询所有数据，不应用数据范围过滤
        return dataService.findAll()
    }
}
```

### 4. 自定义数据范围

通过实现自定义数据范围处理器：

```kotlin
@Component
class CustomDataScopeHandler : DataScopeHandler {
    
    override fun filter(sql: String, tableAlias: String): String {
        // 根据当前用户信息构建数据范围过滤条件
        val currentUser = getCurrentUser()
        val deptIds = getUserDeptIds(currentUser.id)
        
        // 构建过滤SQL
        return "$sql AND $tableAlias.dept_id IN (${deptIds.joinToString(",")})"
    }
}
```

### 5. 临时跳过数据范围

在特定场景下临时跳过数据范围控制：

```kotlin
@Service
class DataService {
    
    fun getDataWithoutScope() {
        // 临时禁用数据范围
        DataScopeContextHolder.setIgnore(true)
        try {
            return dataMapper.selectAll()
        } finally {
            // 恢复数据范围控制
            DataScopeContextHolder.clear()
        }
    }
}
```

## 与其他模块的关系

- **Common 模块**：依赖 Common 模块的数据库连接和 MyBatis-Plus 支持，与其数据库组件深度集成
- **Security 模块**：与 Security 模块协作，基于用户认证信息和权限信息实现数据范围控制
- **Server 模块**：被 Server 模块依赖，为整个应用提供统一的数据范围控制服务
- **MyBatis-Plus 集成**：通过 MyBatis-Plus 的拦截器机制实现数据范围过滤，与 ORM 框架紧密协作
- **ApplicationConfig**：与应用配置模块协作，通过配置文件控制数据范围的行为

DataScope 模块作为系统的核心数据安全组件，为整个项目提供统一的数据访问控制能力，确保数据安全性和合规性，同时保持对业务代码的透明性。