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

- 监控服务资源使用情况API

  http://127.0.0.1:15672/api/index.html

- Connection

  - 自动恢复

    示例类：com.ifreegroup.reliability.connection.AutoRecoverConnection

     触发条件(**初始化连接失败将不会触发恢复连接机制**)：

    - 连接过程中的IO异常
    - socket读操作超时
    - 没有收到broker端发的心跳
  
    关键配置：
  
    ```Java
    ConnectionFactory factory = new ConnectionFactory();
    //启用自动恢复，4.0.0版本之后默认启用
    factory.setAutomaticRecoveryEnabled(true);
    //设置尝试自动恢复间隔
    factory.setNetworkRecoveryInterval(10000);
    factory.newConnection();
    ```

  ------

  - 多服务节点切换

    示例类：com.ifreegroup.reliability.connection.AutoSwitchAddressConnection
    ```java
    ConnectionFactory factory = new ConnectionFactory();
    //启用自动恢复，4.0.0版本之后默认启用
    factory.setAutomaticRecoveryEnabled(false);
    //设置尝试自动恢复间隔
    factory.setNetworkRecoveryInterval(10000);
    //指定服务列表
    Address[] addresses = {
        new Address("8.210.252.134"),
        new Address("47.242.63.83"), 
        new Address("47.242.168.10")
    };
    factory.newConnection(addresses);
    ```

    ```java
    <!--初始化连接134-->
    Attempting to connect to: [8.210.252.134:5672, 47.242.168.10:5672, 47.242.63.83:5672]
    Created new connection: rabbitConnectionFactory#6e041285:0/SimpleConnection@2da99821 [delegate=amqp://guest@8.210.252.134:5672/, localPort= 62234]
    
                                                                                      <!--134节点停掉，服务抛出连接异常，关闭所有channel-->                                                 
    An unexpected connection driver error occured (Exception message: Connection reset)
    Channel shutdown: connection error                                                                      
                                                                                      
    <!--重新连接10服务-->                                                                                      
    Restarting Consumer@2c829dbc: tags=[[amq.ctag-ha5YrxgRIIC2NgH371Pnhg]], channel=Cached Rabbit Channel: PublisherCallbackChannelImpl: AMQChannel(amqp://guest@8.210.252.134:5672/,5), conn: Proxy@7b1e5e55 Shared Rabbit Connection: SimpleConnection@2da99821 [delegate=amqp://guest@8.210.252.134:5672/, localPort= 62234], acknowledgeMode=AUTO local queue size=0
    Attempting to connect to: [8.210.252.134:5672, 47.242.168.10:5672, 47.242.63.83:5672]
    
    Created new connection: rabbitConnectionFactory#6e041285:1/SimpleConnection@1c2afbd1 [delegate=amqp://guest@47.242.168.10:5672/, localPort= 62420]
    ```
    
  
- Channel避免多线程共享

- Subscriber与Broker可靠性

  示例类：com.ifreegroup.reliability.confirm.Subscriber

- Publisher 与Broker可靠性

  - Ack

    交互过程：

    ```
    Publisher             Broker
    Connection.Start<---> Connection.Start-Ok
    Connection.Tune <---> Connection.Tune-Ok
    Connection.Open <---> Connection.Open-Ok
    Channel.Open    <---> Channel.Open-Ok
    Confirm.Select  <---> Confirm.Select-Ok
    Exchange.Declare<---> Exchange.Declare-Ok
    Basic.Publish    --->
    				<---  Basic.ACK
    ```

    示例类：com.ifreegroup.reliability.confirm.Publisher

  - 事务消息

    ```
    Publisher             Broker
    Connection.Start<---> Connection.Start-Ok
    Connection.Tune <---> Connection.Tune-Ok
    Connection.Open <---> Connection.Open-Ok
    Channel.Open    <---> Channel.Open-Ok
    Tx.Select       <---> Tx.Select-Ok
    Exchange.Declare<---> Exchange.Declare-Ok
    Basic.Publish    --->
    Tx.Commit       <---> Tx.Commit-Ok
    ```

  ​       示例目录：com.ifreegroup.reliability.confirm.publisher.transactional

- 追踪消息轨迹

  通过配置text类型（JSON格式更适合程序读取，内容会进行Base64编码）的消息跟踪日志，发布消息时手动增加自定义属性

  ```
  2020-12-08 3:18:24:446: Message published
  
  Node:         rabbit@CY-20171221PLMF
  Connection:   127.0.0.1:55811 -> 127.0.0.1:5672
  Virtual host: /
  User:         guest
  Channel:      1
  Exchange:     txMsgExchange
  Routing keys: [<<"tx">>]
  Routed queues: [<<"txMsgQueue">>]
  Properties:   [{<<"cluster_id">>,longstr,<<"clusterId">>},
                 {<<"app_id">>,longstr,<<"appId">>},
                 {<<"user_id">>,longstr,<<"guest">>},
                 {<<"type">>,longstr,<<"type">>},
                 {<<"timestamp">>,signedint,1607397504},
                 {<<"message_id">>,longstr,
                  <<"4d53940a-09e0-4492-acef-e8d846c1a92e">>},
                 {<<"expiration">>,longstr,<<"100">>},
                 {<<"reply_to">>,longstr,<<"replyTo">>},
                 {<<"correlation_id">>,longstr,<<"correlationId">>},
                 {<<"priority">>,signedint,1},
                 {<<"delivery_mode">>,signedint,1},
                 {<<"headers">>,table,[]},
                 {<<"content_encoding">>,longstr,<<"utf-8">>},
                 {<<"content_type">>,longstr,<<"text">>}]
  Payload: 
  hello txMessage 0
  ```

- mq节点集群

  - 普通集群
- 节点之间只同步元数据，消息数据仍保存在发布目标节点，如果目标节点宕机，会导致队列的不可用
  - 镜像集群

    - 节点类型必须为disc，否则无法启用

- 异常情况的监控
  - 对于publish消息指定不存在的exchange导致channel关闭情况的监控
  - 对于publish消息指定的exchange没有绑定queue情况的监控

## 消息及时性

push模式 Vs  pull模式