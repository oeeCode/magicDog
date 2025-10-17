# 性能测试指南

## 测试目标

验证controller接口吞吐量优化效果

## 测试环境配置

- 使用与生产环境相似的配置
- 确保有足够的测试数据

## 测试工具

- JMeter 或 Apache Bench (ab)
- 或者使用 wrk

## 测试接口

### 1. 基础查询接口

```
GET http://localhost:777/
```

### 2. 带总数查询接口

```
GET http://localhost:777/with-count
```

### 3. 分页查询接口

```
GET http://localhost:7777/page?page=1&size=10
```

### 4. 根据ID查询接口

```
GET http://localhost:7777/{id}
```

### 5. 批量查询接口

```
GET http://localhost:777/batch?ids=1&ids=2&ids=3
```

注意：批量查询接口限制单次查询最多100个ID，超过此限制将抛出异常。

## 性能指标

### 1. 吞吐量 (Throughput)

- 每秒请求数 (RPS - Requests Per Second)
- 目标：相比优化前提升至少 50%

### 2. 响应时间 (Response Time)

- 平均响应时间
- 95% 响应时间
- 99% 响应时间

### 3. 并发能力

- 在不同并发用户数下的性能表现
- 推荐测试并发数：10, 50, 100, 200, 500

## 测试脚本示例

### 使用 Apache Bench (ab) 测试基础接口

```bash
ab -n 1000 -c 100 http://localhost:7777/
```

### 使用 wrk 测试

```bash
wrk -t12 -c400 -d30s http://localhost:7777/
```

### 批量查询接口测试示例

```bash
# 测试批量查询接口（最多100个ID）
ab -n 1000 -c 50 "http://localhost:7777/batch?ids=1&ids=2&ids=3&ids=4&ids=5"
```

## 性能优化要点

### 1. 虚拟线程

- 已配置 Tomcat 使用虚拟线程
- Controller 使用 suspend 函数配合虚拟线程协程上下文

### 2. 缓存策略

- 使用 Spring Cache 进行多级缓存
- 针对不同场景使用不同的缓存键

### 3. 数据库优化

- 分页查询减少单次数据量
- 查询优化和索引使用

### 4. 异步处理

- 批量操作使用并行处理
- 非阻塞 I/O 操作

## 预期结果

优化后应达到以下性能指标：

- 吞吐量提升 50% 以上
- 响应时间降低 30% 以上
- 在高并发场景下保持稳定性能
- 资源利用率更优（CPU、内存）

## 监控指标

应用已集成 Micrometer 指标监控，可通过以下端点查看：

- `/actuator/metrics` - 基础指标
- `/actuator/prometheus` - Prometheus 格式指标（如果配置了Prometheus）
- `/actuator/health` - 健康状况
- `/actuator/threaddump` - 线程转储（可用于分析虚拟线程状态）
- `/actuator/env` - 环境信息

## 虚拟线程性能监控

由于使用了虚拟线程，建议关注以下指标：

- CPU使用率：虚拟线程应该降低CPU竞争
- 内存使用：监控堆内存使用情况
- 响应时间：在高并发下响应时间应保持稳定
- 吞吐量：RPS（每秒请求数）应有显著提升