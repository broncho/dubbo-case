## 1. 说明

该工程示例使用最新的Dubbo版本，Dubbo Starter以及SpringBoot版本,通过`Gradle`进行工程管理和构建，输出可执行程序。

## 2. 框架版本

### 2.1  `Dubbo`版本

+ `org.apache.dubbo:dubbo:2.7.1`
+ `org.apache.dubbo:dubbo-spring-boot-starter:2.7.1`

### 2.2 `SpringBoot`版本

+ `org.springframework.boot:spring-boot-dependencies:2.1.1.RELEASE`

## 3. 模块关系

+  根工程（`dubbo-case`），仅用来定义构建任务，工程信息
+  子工程（`dubbo-api`） ，定义RPC服务的接口，参数和响应结果的数据传输对象
+  子工程（`dubbo-client`）， RPC服务的消费端，实际开发过程中实际情况是一些服务调用其它服务的RPC服务
+  子工程（`dubbo-server`）,RPC服务的提供端

![](https://s1.51cto.com/images/blog/201908/04/cbdb905346a77f922eb408cab004d970.png?x-oss-process=image/watermark,size_16,text_QDUxQ1RP5Y2a5a6i,color_FFFFFF,t_100,g_se,x_10,y_10,shadow_90,type_ZmFuZ3poZW5naGVpdGk=)

## 4. 工程详情

### 4.1 工程信息

+ 代码仓库：https://github.com/broncho/dubbo-case
+ 项目目录结构

![](https://s1.51cto.com/images/blog/201908/04/134f14a252b9b61f83fcdcc4a9ae816a.png?x-oss-process=image/watermark,size_16,text_QDUxQ1RP5Y2a5a6i,color_FFFFFF,t_100,g_se,x_10,y_10,shadow_90,type_ZmFuZ3poZW5naGVpdGk=)

> 备注：script目录归档执行脚本模版信息


### 4.2 接口定义（dubbo-api）


```
public interface HelloService {
    
    String sayHello(String name);
}
```

### 4.3 服务提供者（dubbo-server）

+ 接口实现

```
import com.github.broncho.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Author: secondriver
 * Created: 2019/8/4
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Welcome " + name + " at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
```


> 备注：@Service注解是由Dubbo框架提供的

+ 启动类

```

@SpringBootApplication
public class DubboServerApplication {
    
    public static void main(String[] args) {
        new EmbeddedZooKeeper(2181, true).start();
        SpringApplication.run(DubboServerApplication.class, args);
    }
}

```

> 备注：此处使用嵌入式Zookeeper，实现详情参见源码；或者可以直接采用独立Zookeeper服务

+ 配置文件`application.properties`

```
# 应用名称
dubbo.application.name=dubbo-app1

dubbo.application.qosEnable=true
dubbo.application.qosPort= 22222
dubbo.application.qosAcceptForeignIp=true

# 接口实现者（服务实现）包
dubbo.scan.base-packages=com.github.broncho.dubbo.server.impl

# 注册中心信息
dubbo.registry.id=my-zk
dubbo.registry.address=localhost:2181
dubbo.registry.protocol=zookeeper
dubbo.registry.client=curator
```

关于`Dubbo`的配置参见：https://dubbo.apache.org/zh-cn/docs/user/references/api.html


###  4.4 服务消费者（dubbo-client）

+ 服务消费示例

```
@Component
public class BusinessComponent {
    
    @Reference
    private HelloService helloService;
    

    public void greeting(String name) {
        System.out.println(helloService.sayHello(name));
    }
   
}
```

+ 启动类

```
@SpringBootApplication
public class DubboClientApplication {
    
    
    public static void main(String[] args) {
        
        ConfigurableApplicationContext context = SpringApplication.run(DubboClientApplication.class, args);
        
        BusinessComponent component = context.getBean(BusinessComponent.class);
        
        //RPC
        component.greeting("Dubbo RPC");
        
    }
}
```

+ 配置文件(`application.properties`)

```
# 应用程序名
dubbo.application.name=dubbo-app2

# 注册中心信息
dubbo.registry.id=my-zk
dubbo.registry.address=localhost:2181
dubbo.registry.protocol=zookeeper
dubbo.registry.client=curator
```


## 5. 打包部署

###  5.1 Gradle配置

+ `gradle.properties`

```
systemProp.activeProfile=dev
systemProp.javaVersion=1.8
```

+ `build.gradle`

```
import org.springframework.boot.gradle.plugin.SpringBootPlugin

buildscript {
    repositories {
        mavenLocal()
        maven {
            name "alimaven"
            url "http://maven.aliyun.com/nexus/content/groups/public/"

        }
        mavenCentral()
    }
}

plugins {
    id 'java'
    id 'idea'
    id 'org.springframework.boot' version '2.1.1.RELEASE' apply false
    id 'io.spring.dependency-management' version '1.0.8.RELEASE' apply false
}

//所有工程定义
allprojects {

    sourceCompatibility = System.properties["javaVersion"]
    targetCompatibility = System.properties["javaVersion"]

    repositories {
        mavenLocal()
        maven {
            name "alimaven"
            url "http://maven.aliyun.com/nexus/content/groups/public/"

        }
        mavenCentral()
    }
    group 'com.github.broncho'
    version '1.0.0'
}

//子工程定义
subprojects {
    apply plugin: 'java'
    apply plugin: 'io.spring.dependency-management'
    apply plugin: 'distribution'

    if (!name.contains("api")) {
        apply plugin: 'org.springframework.boot'
        apply plugin: 'application'
    }

    dependencyManagement {
        imports {
            mavenBom(SpringBootPlugin.BOM_COORDINATES)
        }
        dependencies {
            dependencySet(group: 'org.apache.dubbo', version: '2.7.1') {
                entry 'dubbo'
                entry 'dubbo-spring-boot-starter'
            }
        }
        dependencies {
            dependencySet(group: 'org.apache.curator', version: '4.0.0') {
                entry 'curator-framework'
                entry 'curator-recipes'
            }
        }
        dependencies {
            dependency 'org.apache.zookeeper:zookeeper:3.5.5'
        }
    }

    if (!project.name.contains("api")) {

        println "Project ${name}  activeProfile ${System.properties['activeProfile']}"

        jar {
            enabled true
        }

        applicationDefaultJvmArgs = ['-Xms256m', '-Xmx256m']

        /**
         * 生成启动脚本
         */
        startScripts() {
            doFirst {
                unixStartScriptGenerator.template = resources.text.fromFile(
                        project.getRootDir().getAbsolutePath() + "/script/unixStartScript.txt"
                )
                windowsStartScriptGenerator.template = resources.text.fromFile(
                        project.getRootDir().getAbsolutePath() + "/script/windowsStartScript.txt"

                )
            }
        }

        /**
         * 开发环境下不能排除配置文件，不然没法通过IDE运行项目
         */
        if (!"dev".equals(System.properties['activeProfile'])) {
            proce***esources {
                exclude 'application*.properties'
            }
        }

        /**
         * application*.properties统一复制到config目录
         * SpringBoot程序启动时从config目录读取配置文件
         */
        applicationDistribution.from("src/main/resources") {
            include 'application*.properties'
            into 'config'
        }

    }
}
```

项目采用了[Spring Boot Gradle Plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-gradle-plugin.html)和[dependency-management](https://docs.spring.io/dependency-management-plugin/docs/1.0.8.RELEASE/reference/html/)结合起来进行SpringBoot版本管理以及子工程中依赖的统一管理。

### 5.2 打包命令

```
gradle   -DactiveProfile=prod  clean  distZip  
```

基于SpringBoot的`dubbo-server`和`dubbo-client`工程会输出发布包到各自工程目录的`build/distributions`目录。

### 5.3 发布包格式

![](https://s1.51cto.com/images/blog/201908/04/8a044c378df93a694cde74bffee186d9.png?x-oss-process=image/watermark,size_16,text_QDUxQ1RP5Y2a5a6i,color_FFFFFF,t_100,g_se,x_10,y_10,shadow_90,type_ZmFuZ3poZW5naGVpdGk=)
