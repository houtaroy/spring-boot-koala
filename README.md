# spring-boot-koala 2021.1.0-SNAPSHOT

*本项目已重构升级并迁移至[Koala](https://github.com/koala-projects/koala), 后续不会进行更新维护*

## 介绍

集成Spring Boot, 实现部分方便快捷的starter

- spring-boot-koala-starter-mqtt-auto

## 使用说明

### spring-boot-koala-starter-mqtt-auto

#### maven引入

```xml
<repository>
    <id>Spring Boot Koala</id>
    <name>Spring Boot Koala</name>
    <url>https://raw.github.com/Houtaroy/maven-repo/main</url>
    <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
    </snapshots>
    <releases>
        <enabled>true</enabled>
    </releases>
</repository>
```

在pom.xml中引入

```xml
<dependency>
  <groupId>cn.houtaroy</groupId>
  <artifactId>spring-boot-koala-starter-mqtt-auto</artifactId>
  <version>2021.1.0-SNAPSHOT</version>
</dependency>
```

如果github的源存在问题, 可以改用gitee:

```xml
<repository>
    <id>Spring Boot Koala</id>
    <name>Spring Boot Koala</name>
    <url>https://gitee.com/houtaroy/maven-repo/raw/main/</url>
    <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
    </snapshots>
    <releases>
        <enabled>true</enabled>
    </releases>
</repository>
```

#### 生产者配置示例

在配置文件中写入对应配置:

```yaml
mqtt:
  producers:
    producer-test-01:
      url: [ tcp://127.0.0.1:1883 ]
      topics: [ producer/+/test ]
      qos: [ 0 ]
      async: true
      client-id: producer-test-01
```

使用默认生产者向指定主题发送消息(保证至少有一个生产者):

```java
MqttUtil.sendMessage("topic01", "测试消息");
```

使用指定生产者向指定主题发送消息:

```java
MqttUtil.sendMessage("producer-test-01", "topic01", "测试消息");
```

#### 消费者配置示例

在配置文件中写入对应配置:

```yaml
mqtt:
  consumers:
    consumer-test-01:
      url: [ tcp://127.0.0.1:1883 ]
      topics: [ consumer/+/test ]
      qos: [ 0 ]
      async: true
      client-id: consumer-test-01
```

创建一个Handler用于接收消息的业务逻辑处理:

```java
@Slf4j
@Component
public class ConsumerHandler implements MessageHandler {

  @Override
  @ServiceActivator(inputChannel = "consumer-test-01")
  public void handleMessage(Message<?> message) throws MessagingException {
    Message<String> result = (Message<String>) message;
    LOGGER.info(String.format("MQTT: 主题[%s]接收到消息[%s]", result.getHeaders().get(MqttHeaders.RECEIVED_TOPIC), result.getPayload()));
  }

}
```


## 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
