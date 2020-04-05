package com.github.vspro.order.svr.service.impl;

import com.github.vspro.dmcpublic.api.MqCoreProviderClient;
import com.github.vspro.dmcpublic.vo.TransactionMessageVO;
import com.github.vspro.order.persist.ext.manager.OrderManager;
import com.github.vspro.order.persist.orm.domain.OrderDo;
import com.github.vspro.order.svr.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component("orderService")
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderManager orderManager;

    @Autowired
    private MqCoreProviderClient mqCoreProviderClient;


    @Override
    @Transactional
    public boolean placeOrder(Long userId, Long productId) {


        OrderDo orderDo = new OrderDo();
        orderDo.setOrderNo(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now()));
        orderDo.setOrderName("小米10订单");
        orderDo.setUserId(userId);
        orderDo.setAmount(new Random().nextInt(100));
        orderDo.setProductId(productId);
        orderDo.setDeleted("2");
        orderDo.setGmtCreated(LocalDateTime.now());
        orderDo.setGmtModified(LocalDateTime.now());
        orderManager.insert(orderDo);


//        orderManager.insert(orderDo);

        TransactionMessageVO messageVO = new TransactionMessageVO();
        messageVO.setProducerServiceId("order-service");
        messageVO.setConsumerServiceId("stock-service");
        messageVO.setMessage(productId+"");
        messageVO.setSendCount(0);
        messageVO.setStatus(1);
        messageVO.setLastSendDate(LocalDateTime.now());
        messageVO.setDeleted("2");
        messageVO.setGmtCreated(LocalDateTime.now());
        messageVO.setGmtModified(LocalDateTime.now());


        boolean success = mqCoreProviderClient.sendMessage(messageVO);
        if (!success){
            throw new RuntimeException("下单异常!!");
        }

        return Boolean.TRUE;
    }
}
