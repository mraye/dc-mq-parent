package com.github.vspro.order.persist.ext.manager;

import com.github.vspro.order.persist.ext.mapper.OrderMapper;
import com.github.vspro.order.persist.orm.domain.OrderDo;
import com.github.vspro.persist.base.dao.BaseDao;
import com.github.vspro.persist.base.manager.BaseManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("orderManager")
public class OrderManager extends BaseManager<OrderDo> {


    @Autowired
    private OrderMapper orderMapper;

    @Override
    public BaseDao<OrderDo> getDao() {
        return this.orderMapper;
    }
}
