微服务项目，使用 gradle 构建

### 模块说明

Common模块：存放公共使用的类

Gateway模块：网关

Modules模块：存放微服务

PurplePoison模块：Modules模块的子模块

TaskService模块：Modules模块的子模块


### .gradle 文件说明
build.gradle：控制各个模块的依赖

config.gradle：用来控制依赖的版本信息

settings.gradle：项目的模块结构