微服务项目，使用 gradle 构建

### 模块说明

Api模块：存放微服务的远程调用接口

Common模块：存放公共使用的类

CommonCore模块：公共模块的核心模块

CommonDatasource模块：公共模块的数据源模块

CommonRedis模块：公共模块的缓存模块

CommonSecurity模块：公共模块的安全模块

CommonWeb模块：公共模块的网络传输模块

Gateway模块：网关

Modules模块：存放微服务

AuthService模块：Modules模块的子模块，负责登陆权限校验

FileService模块：Modules模块的子模块，负责文件上传与下载

JobService模块：Modules模块的子模块，负责定时任务

MessageService模块：Modules模块的子模块，负责消息队列

SystemService模块：Modules模块的子模块，负责用户的管理

TaskService模块：Modules模块的子模块，负责任务的管理

WebSocketService模块：Modules模块的子模块，负责与前端进行消息传递

### .gradle 文件说明

build.gradle：控制各个模块的依赖

config.gradle：用来控制依赖的版本信息

settings.gradle：项目的模块结构

### 技术栈

微服务框架：

- Spring Cloud
- Spring Boot

数据库访问：

- Mybatis

缓存：

- Redis

网关：

- spring-cloud-gateway

服务远程调用：

- spring-cloud-open-feign

服务注册与发现：

- spring-cloud-alibaba-nacos

消息队列：

- Kafka

文件存储：

- Minio

轻量级全双工消息通信：

- WebSocket

分布式任务调度：

- Xxl-Job