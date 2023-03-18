> `Springboot 3` 出来也有一段时间了，`Springcloud alibaba`也在适配当中，本demo使用`Springboot 3 + Spingcloud alibaba + nacos`等组件，完成一个项目工程的简单搭建

## 组件

* `Nacos` : `2.0.3`
* `Maven`：`3.0.8`

## 依赖版本

* `springboot` :`3.0.4`
* `springcloud`:`2022.0.1`
* `springcloud alibaba` :`2.2.10-RC1`
* `spring-cloud-starter-alibaba-nacos-discovery`:`2022.0.0.0-RC1`
* `spring-cloud-starter-alibaba-nacos-config`：`2022.0.0.0-RC1`

> `springcloud alibaba 2.2.10-RC1` 版本提供的`Nacos依赖`，经测试无法成功注册，故使用`2022.0.0.0-RC1`版本

## 项目

### POM

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.0.4</version>
        <relativePath/>
    </parent>
    <groupId>com.piemoe</groupId>
    <artifactId>springboot3</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springboot3</name>
    <description>springboot3</description>
    <properties>
        <java.version>17</java.version>
    </properties>
​
  <dependencyManagement>
      <dependencies>
          <!-- SpringCloud 微服务 -->
          <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-dependencies</artifactId>
              <version>2022.0.1</version>
              <type>pom</type>
              <scope>import</scope>
          </dependency>
​
          <!-- SpringCloud Alibaba 微服务 -->
          <dependency>
              <groupId>com.alibaba.cloud</groupId>
              <artifactId>spring-cloud-alibaba-dependencies</artifactId>
              <version>2.2.10-RC1</version>
              <type>pom</type>
              <scope>import</scope>
          </dependency>
      </dependencies>
  </dependencyManagement>
​
    <dependencies>
​
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
​
        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
            <version>2022.0.0.0-RC1</version>
        </dependency>
​
​
        <!-- https://mvnrepository.com/artifact/com.alibaba.cloud/spring-cloud-starter-alibaba-nacos-config -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>2022.0.0.0-RC1</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bootstrap</artifactId>
        </dependency>
​
​
    </dependencies>
​
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
​
</project>
​
```

### 启动类

```
@SpringBootApplication
public class Springboot3Application {
​
public static void main(String[] args) {
SpringApplication.run(Springboot3Application.class, args);
}
​
}
```

### 配置文件

#### bootstrap.yml

```
server:
  port: 13000
​
spring:
  main:
    banner-mode: off
  application:
    name: demo
  profiles:
    active: local
​
rpc:
  addr: 127.0.0.1:8848
​
logging:
  file:
    name: logs/${spring.application.name}/all.log
```

#### bootstrap-local.yml

```
spring:
  cloud:
    nacos:
      discovery:
        server-addr: ${rpc.addr}
        namespace: ${spring.profiles.active}
      config:
        server-addr: ${rpc.addr}
        namespace: ${spring.profiles.active}
        file-extension: yml
```

## Nacos配置

```
demo:
  name: xxxxx
```

