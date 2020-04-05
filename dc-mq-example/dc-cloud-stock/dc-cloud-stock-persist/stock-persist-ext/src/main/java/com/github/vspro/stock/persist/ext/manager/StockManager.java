package com.github.vspro.stock.persist.ext.manager;

import com.github.vspro.persist.base.dao.BaseDao;
import com.github.vspro.persist.base.manager.BaseManager;
import com.github.vspro.stock.persist.ext.mapper.StockMapper;
import com.github.vspro.stock.persist.orm.domain.StockDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockManager extends BaseManager<StockDo> {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public BaseDao<StockDo> getDao() {
        return stockMapper;
    }

    public boolean deductStock(Long productId) {
        return stockMapper.deductStock(productId) > 0;
    }
}
