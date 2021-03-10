
<p align="center">
	<strong>一个基于springboot的项目快速集成脚手架</strong>
</p>

<p align="center">
    <a >
        <img src="https://github.com/baomidou/dynamic-datasource-spring-boot-starter/workflows/CodeQL/badge.svg?branch=master" >
    </a>
    <a href="http://mvnrepository.com/artifact/com.baomidou/dynamic-datasource-spring-boot-starter" target="_blank">
        <img src="https://img.shields.io/maven-central/v/com.baomidou/dynamic-datasource-spring-boot-starter.svg" >
    </a>
    <a href="http://www.apache.org/licenses/LICENSE-2.0.html" target="_blank">
        <img src="http://img.shields.io/:license-apache-brightgreen.svg" >
    </a>
    <a>
        <img src="https://img.shields.io/badge/JDK-1.7+-green.svg" >
    </a>
    <a>
        <img src="https://img.shields.io/badge/springBoot-1.5.x__2.x.x-green.svg" >
    </a>
</p>

# 简介

spring-boot-starters是一个基于springboot的项目快速集成脚手架

其支持 **Jdk 1.7+,    SpringBoot 1.4.x  1.5.x   2.x.x**。模块列表如下：

|     模块    |                  说明                  |
| :------------ | :--------------------------------------- |
| common-spring-boot-starter |公共模块|
| dynamic-datasource-spring-boot-starter | 动态数据源模块 |
| demo-project | 用例模块 |
| db-spring-boot-starter | 集成druid数据源、集成mybatis-plus、动态数据源切换、pagehelper分页处理、Guava |
| log-spring-boot-starter | 定义logback格式，统一log输出 、定义业务log通用json格式、sleuth日志埋点，链路跟踪 |
| swagger-spring-boot-starter | API文档 |
| uaa-server-spring-boot-starter | oauth2.0认证中心服务端 |
| uaa-client-spring-boot-starter | oauth2.0认证中心客户端 |
| ribbon-spring-boot-starter | ribbon客户端负载均衡 |
| redis-spring-boot-starter | redis快速集成 |
| rabbitmq-spring-boot-starter  | rabbitmq消息中间间快速集成 |

## 1、动态数据源模块:dynamic-datasource-spring-boot-starter
### 特性

1. 本模块继承于苞米豆的ynamic-datasource-spring-boot-starter模块
2. 添加自定义p6spy SQL日志输出格式
3. 支持 **数据源分组** ，适用于多种场景 纯粹多库  读写分离  一主多从  混合模式。
4. 支持数据库敏感配置信息 **加密**  ENC()。
5. 支持每个数据库独立初始化表结构schema和数据库database。
6. 支持 **自定义注解** ，需继承DS(3.2.0+)。
7. 提供对Druid，Mybatis-Plus，P6sy，Jndi的快速集成。
8. 简化Druid和HikariCp配置，提供 **全局参数配置** 。配置一次，全局通用。
9. 提供 **自定义数据源来源** 方案。
10. 提供项目启动后 **动态增加移除数据源** 方案。
11. 提供Mybatis环境下的  **纯读写分离** 方案。
12. 提供使用 **spel动态参数** 解析数据源方案。内置spel，session，header，支持自定义。
13. 支持  **多层数据源嵌套切换** 。（ServiceA >>>  ServiceB >>> ServiceC）。
14. 提供对shiro，sharding-jdbc,quartz等第三方库集成的方案,注意事项和示例。
15. 提供  **基于seata的分布式事务方案。** 附：不支持原生spring事务。
16. 提供  **本地多数据源事务方案。** 附：不支持原生spring事务。

### 约定

1. 本框架只做 **切换数据源** 这件核心的事情，并**不限制你的具体操作**，切换了数据源可以做任何CRUD。
2. 配置文件所有以下划线 `_` 分割的数据源 **首部** 即为组的名称，相同组名称的数据源会放在一个组下。
3. 切换数据源可以是组名，也可以是具体数据源名称。组名则切换时采用负载均衡算法切换。
4. 默认的数据源名称为  **master** ，你可以通过 `spring.datasource.dynamic.primary` 修改。
5. 方法上的注解优先于类上注解。
6. 强烈建议只在service的类和方法上添加注解，不建议在mapper上添加注解。

### 使用方法

1. 引入dynamic-datasource-spring-boot-starter。

```xml
<dependency>
  <groupId>com.zsx</groupId>
  <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```
2. 配置数据源。

```yaml
spring:
  datasource:
    dynamic:
      primary: master #设置默认的数据源或者数据源组,默认值即为master
      strict: false #设置严格模式,默认false不启动. 启动后在未匹配到指定数据源时候会抛出异常,不启动则使用默认数据源.
      datasource:
        master:
          url: jdbc:mysql://xx.xx.xx.xx:3306/dynamic
          username: root
          password: 123456
          driver-class-name: com.mysql.jdbc.Driver # 3.2.0开始支持SPI可省略此配置
        slave_1:
          url: jdbc:mysql://xx.xx.xx.xx:3307/dynamic
          username: root
          password: 123456
          driver-class-name: com.mysql.jdbc.Driver
        slave_2:
          url: ENC(xxxxx) # 内置加密,使用请查看详细文档
          username: ENC(xxxxx)
          password: ENC(xxxxx)
          driver-class-name: com.mysql.jdbc.Driver
          schema: db/schema.sql # 配置则生效,自动初始化表结构
          data: db/data.sql # 配置则生效,自动初始化数据
          continue-on-error: true # 默认true,初始化失败是否继续
          separator: ";" # sql默认分号分隔符
          
       #......省略
       #以上会配置一个默认库master，一个组slave下有两个子库slave_1,slave_2
```

```yaml
# 多主多从                      纯粹多库（记得设置primary）                   混合配置
spring:                               spring:                               spring:
  datasource:                           datasource:                           datasource:
    dynamic:                              dynamic:                              dynamic:
      datasource:                           datasource:                           datasource:
        master_1:                             mysql:                                master:
        master_2:                             oracle:                               slave_1:
        slave_1:                              sqlserver:                            slave_2:
        slave_2:                              postgresql:                           oracle_1:
        slave_3:                              h2:                                   oracle_2:
```

3. 使用  **@DS**  切换数据源。

**@DS** 可以注解在方法上或类上，**同时存在就近原则 方法上注解 优先于 类上注解**。

|     注解      |                   结果                   |
| :-----------: | :--------------------------------------: |
|    没有@DS    |                默认数据源                |
| @DS("dsName") | dsName可以为组名也可以为具体某个库的名称 |

```java
@Service
@DS("slave")
public class UserServiceImpl implements UserService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List selectAll() {
    return  jdbcTemplate.queryForList("select * from user");
  }
  
  @Override
  @DS("slave_1")
  public List selectByCondition() {
    return  jdbcTemplate.queryForList("select * from user where age >10");
  }
}
```
---

