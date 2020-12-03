# *RabbitMQ示例说明*

## 交换机初步认识

**Exchange 参数介绍**

- Name：交换机名称
- Type：交换机类型 direct、topic、fanout、headers
- Durability：是否需要持久化
- Auto Delete：最后一个绑定到 Exchange 上的队列删除之后自动删除该 Exchange
- Internal：当前 Exchange 是否应用于 RabbitMQ 内部使用，默认false。
- Arguments：扩展参数

**Exchange 四种类型**

- direct：不需要 Exchange 进行绑定，根据 RoutingKey 匹配消息路由到指定的队列。
- topic：生产者指定 RoutingKey 消息根据消费端指定的队列通过模糊匹配的方式进行相应转发，两种通配符模式：
  - `#`：可匹配一个或多个关键字
  - `*`：只能匹配一个关键字
- fanout：这种模式只需要将队列绑定到交换机上即可，是不需要设置路由键的。
- headers：根据发送消息内容中的 headers 属性来匹配（极少使用）

## 添加maven依赖

```xml
<!--java模式--> 
<dependency>
     <groupId>com.rabbitmq</groupId>
     <artifactId>amqp-client</artifactId>
     <version>5.9.0</version>
</dependency>

<!--springboot集成-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
    <version>2.3.4.RELEASE</version>
</dependency>
```

## 测试说明

Java方式测试均采用主方法进行

springboot集成方式除延时队列采用对外服务方式，其他所有示例均采用单元测试进行

## 默认交换机

RabbitMQ中如果发布消息时没有指定交换机则使用默认交换机（格式为空字符串）

### 点对点模式

生产者-消费者模式，

java示例目录：java-demo模块com.ifreegroup.simple.peer2peer

springboot集成示例目录：springboot-demo模块com.ifreegroup.simple.peer2peer

### 工作队列模式

多个消费者共享消息模式，通过轮询的模式向每个消费者分发消息

java示例目录：Java-demo模块com.ifreegroup.simple.workqueue

springboot集成示例目录：springboot-demo模块com.ifreegroup.simple.workqueue

## 交换机类型之 direct

 direct 通过 RoutingKey 匹配消息路由到指定的队列，因此也可以无需指定交换机，在不指定交换机的情况下会使用 `AMQP default` 默认的交换机，另外在消息投递时要注意 RoutingKey 要完全匹配才能被队列所接收，否则消息会被丢弃的。 

java示例目录：java-demo模块com.ifreegroup.simple.broadcast.direct

springboot集成示例目录：springboot-demo模块com.ifreegroup.simple.broadcast.direct

### 延时队列的实现

- 通过rabbitmq特性ttl和死信队列组合实现，

  Java示例目录：java-demo模块com.ifreegroup.simple.delay.ttl

  springboot集成示例目录：springboot-demo模块com.ifreegroup.simple.delay.ttl

- 通过扩展plugin之rabbitmq_delayed_message_exchange实现，

  插件下载地址：https://github.com/rabbitmq/rabbitmq-delayed-message-exchange/releases

  插件安装目录：/rabbitmq_server-3.8.9/plugins

  启用插件命令：./rabbitmq-plugins  enable rabbitmq_delayed_message_exchange

  Java示例目录：java-demo模块com.ifreegroup.simple.delay.plugin
  
  springboot集成示例目录：springboot-demo模块com.ifreegroup.simple.delay.plugin

## 交换机类型之 topic

生产者指定 RoutingKey ，消费端根据指定的队列通过模糊匹配的方式进行相应转发，两种通配符模式如下：

- `#`：可匹配一个或多个关键字
- `*`：只能匹配一个关键字

Java示例目录：com.ifreegroup.simple.broadcast.topic

springboot集成示例目录：springboot-demo模块com.ifreegroup.simple.broadcast.topic

## 交换机类型之 fanout

与 direct 和 topic 两种类型不同的是这种模式只需要将队列绑定到交换机上即可，是不需要设置路由键的，便可将消息转发到绑定的队列上，正是由于不需要路由键，所以 fanout 也是四个交换机类型中最快的一个，如果是做广播模式的就很适合。

java示例目录：com.ifreegroup.simple.broadcast.fanout

springboot集成示例目录：springboot-demo模块com.ifreegroup.simple.broadcast.fanout

## 交换机类型之 headers

该类型的交换机是根据发送消息内容中的 headers 属性来匹配的，headers 类型的交换机基本上不会用到。



## 消息可靠性

- Connection自动恢复

  示例目录：com.ifreegroup.reliability.connection

  触发条件：

  - 连接过程中的IO异常

  - socket读操作超时

  - 没有收到broker端发的心跳

  **初始化连接失败将不会触发恢复连接机制**

  启用配置：

  ```java
  ConnectionFactory factory = new ConnectionFactory();
  //启用自动恢复，4.0.0版本之后默认启用
  factory.setAutomaticRecoveryEnabled(true);
  //设置尝试自动恢复间隔
  factory.setNetworkRecoveryInterval(10000);
  //指定服务列表
  Address[] addresses = {new Address("192.168.1.4"), new Address("192.168.1.5")};
  factory.newConnection(addresses);
  ```

- Channel避免多线程共享

- Subscriber确认
- Publisher 确认