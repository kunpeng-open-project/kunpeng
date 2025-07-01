# 昆鹏微服务框架

基于 SpringBoot、SpringCloud、SpringCloud Alibaba 等技术栈构建的轻量级微服务框架，致力于为开发人员提供快速上手的全栈解决方案。

## 项目简介

昆鹏微服务框架是一个为满足开发团队高效开发需求而设计的开源项目。它整合了当前主流的微服务技术，提供了从代码生成到服务部署的一站式解决方案，帮助开发人员快速构建稳定、高效的微服务应用。

## 在线体验

- 官网地址(使用文档)：[http://kunpengtool.cn](http://kunpengtool.cn)
- 演示地址：[http://kunpengtool.cn/auth](http://kunpengtool.cn/auth)
- 接口文档：[http://kunpengtool.cn/gateway/doc.html](http://kunpengtool.cn/gateway/doc.html)


## 开源初衷

在寻找适合各水平开发人员的微服务框架时，发现现有框架难以满足以下需求：
- 自动生成 Controller、Service、DAO 及 PO 类
- 基于数据库表自动生成增删改查等基本功能接口
- 统一接口风格与地址规范
- 框架级事务管理，避免手动注解遗漏
- 封装常用功能与中间件工具类，降低技术门槛
- 完整的接口调用记录、错误日志、调用统计
- 核心代码开源可修改，方便二次开发

因此，利用空闲时间开发了昆鹏微服务框架，以满足上述需求。

## 核心特性

- **代码自动生成**：基于数据库表自动生成基础代码，减少重复开发
- **全栈微服务解决方案**：整合 SpringCloud 生态，支持服务注册、配置中心等完整微服务架构
- **零学习成本**：封装常用功能，提供清晰的使用示例
- **丰富工具类**：集成开发中常用的各类工具，提升开发效率
- **注解驱动开发**：通过注解简化开发流程，降低编码复杂度
- **Knife4j 扩展**：完善的接口文档支持，方便团队协作
- **高可配置性**：灵活的配置方案，适配不同业务场景


### 核心框架
- Spring Boot：快速构建应用的基础框架
- Spring Cloud：微服务架构核心组件
- Spring Cloud Alibaba：阿里系微服务组件集成
- MyBatis-Plus：增强版 MyBatis，简化数据库操作
- MyBatis-Plus-Join：支持复杂关联查询



## 支持数据库

- MySQL（默认）
- Oracle
- SQL Server
- PostgreSQL



### 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL >= 8.0（或其他支持数据库）


# 目录结构

```
kunpeng项目根目录
├── kunpeng-gateway  # 网关项目
│   ├── com.kunpeng.framework  
│   │   ├── GatewayApplication 启动文件
│   ├── resources  
│   │   ├── bootstrap.yml 切换不同环境的配置文件
│   │   ├── bootstrap-dev.yml 开发环境配置文件
├── kunpeng-auth  # 鉴权项目
│   ├── com.kunpeng.framework
│   │   ├── api 对外接口存放地方  
│   │   ├── common 存放系统公共文件 
│   │   │   ├── cache 缓存文件 
│   │   │   ├── properties 读取ymml|nacos 配置文件 
│   │   │   ├── rabbitmq rabbitmq消费
│   │   ├── config 当前项目配置文件 
│   │   ├── modules 项目功能模块代码 
│   │   │   ├── data 下拉框接口 
│   │   │   ├── dept 部门模块相关接口代码 
│   │   │   ├── logRecord 日志记录模块相关接口代码 
│   │   │   ├── menu 菜单模块相关接口代码 
│   │   │   ├── post 岗位模块相关接口代码 
│   │   │   ├── project 项目模块相关接口代码
│   │   │   ├── role 角色模块相关接口代码 
│   │   │   ├── user 用户模块相关接口代码
│   │   │   ├── welcome 欢迎页面相关接口代码
│   │   ├── AuthenticationApplication 启动文件  
├── kunpeng-common  # 系统共用配置
│   ├── framework-core 框架核心模块
│   │   │── com.kunpeng.framework
│   │   │   ├── annotation 框架自定义扩展注解
│   │   │   ├── configruation 框架配置信息
│   │   │   ├── constant 框架常量配置
│   │   │   ├── controller 框架内置的接口
│   │   │   ├── entity 框架内置实体类
│   │   │   ├── enums 框架内置枚举类
│   │   │   ├── exception 自定义异常和全局异常处理
│   │   │   ├── listener 框架监听器
│   │   │   ├── mapper mybatisPlusMapper控制信息
│   │   │   ├── transactiona 全局异常处理器
│   │   │   ├── utils 框架内置工具类
│   │   │   │   ├── kptool 鲲鹏工具封装类
│   ├── framework-security 框架权限模块
│   │   │── com.kunpeng.framework
│   │   │   ├── common 公共文件 
│   │   │   │   ├── annotation 数据权限自定义注解
│   │   │   │   ├── enums 枚举
│   │   │   │   ├── parent 父类
│   │   │   │   ├── properties 读取ymml|nacos 配置文件 
│   │   │   │   ├── security 权限模块
│   │   │   ├── config 配置信息 
├────────────── modules 模块功能 
```