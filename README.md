# transactionMgmt



### **技术栈**:

* **后端**: Java 21, Spring Boot
* **存储**: h2数据库, caffine本地缓存

* **构建工具**: Maven

*   **容器化**: Docker
***




### **全面测试**：

#### 1、单元测试： 
    使用 JUnit 和 Mockito 对服务层和控制器层进行单元测试


#### 2、压力测试：

    使用Gatling 进行接口性能测试并生成测试报告。
    
    ================================================================================
    2025-02-15 21:57:45                                          31s elapsed
    ---- Requests ------------------------------------------------------------------
    > Global                                                   (OK=900    KO=0     )
    > Create Transaction                                       (OK=300    KO=0     )
    > Get All Transactions                                     (OK=300    KO=0     )
    > Delete Transaction                                       (OK=300    KO=0     )

    ---- Transaction Load Test -----------------------------------------------------
    [##########################################################################]100%
              waiting: 0      / active: 0      / done: 300   
    ================================================================================

    


### **部署方案**：

#### 1、 Docker部署
- 配置 Dockerfile 构建镜像，并启动。


- 后台打镜像指令：
```
 mvn clean package 

 docker build  -t transaction-mgmt:latest .
```
- 后台docker run指令：
```
docker run -d --network=host --name transaction-mgmt -p 8080:8080  -e spring.profiles.active=prod  transaction-mgmt:latest
```


- 前台打镜像指令：
```
 yarn
 yarn build

 docker build -t transaction-mgm-web:latest .
```
- 后台docker run指令：
```
docker run -d --name transaction-mgmt-web -p 3000:80  transaction-mgmt-web:latest
```
- 前台效果:
![效果](/images/fe.jpg "")


##  后期扩展方向

    *   数据存数到数据库mysql等数据库中。
    *   使用Redis作为缓存，并处理其分布式系统中的三缓问题及双写一致问题。
    *   基于Spring Security 安全框架，结合JWT，实现认证、授权、细粒度权限控制等。
    *   集成ELK实现日志采集，方便日志查看及问题定位。

