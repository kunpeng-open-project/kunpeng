## **介绍**

[//]: #
<center style="font-size: 40px; font-weight: bold">易鹏(KoaPower)</center>
<center>基于springboot、springcloud、springcloud-alibaba、mybatisPlus、MyBatis-Plus-Join</center>

::: tip 开源初衷
一直想找一个轻量级、适合所有水平开发人员能够快速上手的微服务框架、并且要求：
- 可以自动生成controller、service、dao，以及最为繁琐的po类。
- 可以自动通过数据库表生成新增、修改、删除、查询、详情等基本功能接口。无须手动编写代码。
- 可以自动生成接口地址、保证项目团队所有人接口风格一致。
- 框架自动加事务、而不是程序员在每个接口上手动加注解，避免遗忘造成重大损失
- 可以把开发过程中经常使用的功能全部封装成工具类、加快开发速度。例如 经常用到的各种功能 以及中间件全部封装、开发人员即使不会相应的技术也可进开发
- 有完整的接口调用记录、错误日志、接口调用次数、接口调用耗时等
- 所有代码都在框架里面、包括提供工具类、配置信息 方便用户二次开发。而不是把核心代码封装成独立jar包。开发人员无法修改

但是没有找到合适的。于是利用空闲休息时间开始自己写了一套微服务系统框架。如此有了易鹏。
:::

## 项目名称

中文名称：易鹏  
—— 取"易"字，寓意简单易学、轻松上手，降低微服务开发门槛；含"鹏"意，象征高效简洁、性能卓越，助力项目快速腾飞。

英文名称：KoaPower  
—— "Koa"呼应轻量简洁的设计哲学，"Power"彰显高效赋能的技术特性，二者结合传递框架"简单即强大"的核心主张。

两者相映，既承载技术理念，又兼顾认知友好，为开源社区提供清晰易记的标识。

## 在线体验

<p>官网地址：<a href="/">http://kpopen.cn</a></p>
<p>演示地址：<a target="_blank" href="http://kpopen.cn/auth">http://kpopen.cn/auth</a></p>
<p>接口文档地址：<a target="_blank" href="http://kpopen.cn/gateway/doc.html">http://kpopen.cn/gateway/doc.html</a></p>

## 特性
- 自动生成代码
- 微服务全栈解决方案
- 无学习成本
- 提供大量常用工具
- 注解支持
- knife4j扩展
- 可配置性高

## 支持数据库
- MySQL （默认）
- Oracle
- SQL Server
- PostgreSQL


## 使用的技术栈
springBoot、springCloud、springcloud-alibaba、mybatis-plus、mybatis-plus-join、pagehelper、druid、flywaydb、jwt、minio、knife4j、alibaba-json、okhttp、easyexcel、tika、staxon、oshi、bitwalker、smiley、pinyin4j、zxing、hutool、lombok

## 代码托管

码云

# 目录结构

```
yipeng项目根目录
├── yipeng-gateway  # 网关项目
│   ├── com.kp.framework  
│   │   ├── GatewayApplication 启动文件
│   ├── resources  
│   │   ├── bootstrap.yml 切换不同环境的配置文件
│   │   ├── bootstrap-dev.yml 开发环境配置文件
├── yipeng-auth  # 鉴权项目
│   ├── com.kp.framework
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
├── yipeng-common  # 系统共用配置
│   ├── framework-core 框架核心模块
│   │   │── com.kp.framework
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
│   │   │── com.kp.framework
│   │   │   ├── common 公共文件 
│   │   │   │   ├── annotation 数据权限自定义注解
│   │   │   │   ├── enums 枚举
│   │   │   │   ├── parent 父类
│   │   │   │   ├── properties 读取ymml|nacos 配置文件 
│   │   │   │   ├── security 权限模块
│   │   │   ├── config 配置信息 
├────────────── modules 模块功能 
```







##快速开始

[快速开始](http://kpopen.cn/kpBack/002_%E5%BF%AB%E9%80%9F%E5%BC%80%E5%A7%8B.html)
