## dc-mq-parent


**分布式事务解决方案之-柔性事务-最终一致性实现, 这里采用独立消息服务**


### 原理

+ 服务上游开启本地事务创建order订单，然后，同时创建transaction_message
+ 独立消息服务读取transaction_message,然后，通过生产者发送消息给rabbitmq服务节点
+ 服务下游接收消息，扣减库存，然后ack消息，流程结束；如果扣减库存失败，则进行Nack,然后重新入队，直到库存扣减成功


### 工程说明

+ dc-mq-parent
    + dc-mq: 独立消息服务节点
        + dc-mq-core: 对transaction_message的操作服务，如增删改查
            + dc-mq-core-persist: 持久层
            + dc-mq-core-provider: transaction_message微服务节点
            + dc-mq-core-public: feign服务，对dc-mq-core-provider的包装,提供jar引用
            + dc-mq-core-service: transaction_message服务
        + dc-mq-service-provider: 定时任务，扫描transaction_message
    + dc-mq-example: 最终一致性实例
        + dc-cloud-eureka: 注册中心
        + dc-cloud-order
            + dc-cloud-order-persist: 持久层
            + dc-cloud-order-provider: 订单微服务节点
            + dc-cloud-order-service: 订单service
        + dc-cloud-stock
            + dc-cloud-stock-persist: 持久层
            + dc-cloud-stock-provider: 库存微服务节点
            + dc-cloud-stock-service: 库存service





