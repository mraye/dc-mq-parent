package com.github.vspro.stock.pd.dccloudstockprovider.consumer;

import com.github.vspro.dmcpublic.api.MqCoreProviderClient;
import com.github.vspro.dmcpublic.vo.TransactionMessageVO;
import com.github.vspro.stock.pd.dccloudstockprovider.dto.MqMessage;
import com.github.vspro.stock.pd.dccloudstockprovider.util.JacksonUtil;
import com.github.vspro.stock.svr.service.StockService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockConsumerListener {


    @Autowired
    private MqCoreProviderClient mqCoreProviderClient;

    @Autowired
    private StockService stockService;

    @RabbitListener(queues = "tx-mq-queue", ackMode = "MANUAL")
    public void onMessage(Message message, Channel channel) throws Exception {

        try {
            System.out.println(">>>>开始扣减库存...");
            String json = new String(message.getBody(), "UTF-8");
            MqMessage mqMessage = JacksonUtil.toBean(MqMessage.class, json);
            if (mqMessage == null) {
                System.out.println(">>>>消息不存在...");
                return;
            }
            Long messageId = mqMessage.getMessageId();
            TransactionMessageVO messageVO = mqCoreProviderClient.retrieveMessage(messageId);
            if (messageVO == null || messageVO.getStatus() == 2 || messageVO.getStatus() == 3) {
                System.out.println(">>>>消息已经消费,或者已经关闭，不再重复消费... messageId: "+ messageId);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }

            if (messageVO.getStatus() !=4){
                System.out.println(">>>>消息状态异常，不再重复消费... status: "+ messageVO.getStatus());
                //设置重回队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                return;
            }

            Long productId = Long.parseLong(mqMessage.getBody());
            boolean success = stockService.deductStock(messageId, productId);
            if (success) {
                System.out.println(">>>>扣减库存成功...");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(">>>>扣减库存失败，消息重新入队...");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }
}
