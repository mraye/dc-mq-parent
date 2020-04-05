package com.github.vspro.stock.svr.service.impl;

import com.github.vspro.dmcpublic.api.MqCoreProviderClient;
import com.github.vspro.stock.persist.ext.manager.StockManager;
import com.github.vspro.stock.svr.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockServiceImpl implements StockService {


    @Autowired
    private StockManager stockManager;

    @Autowired
    private MqCoreProviderClient mqCoreProviderClient;

    @Override
    @Transactional
    public boolean deductStock(Long messageId, Long productId) {

        boolean result = stockManager.deductStock(productId);

        if (!result) {
            throw new RuntimeException("扣减库存失败");
        }

        boolean success = mqCoreProviderClient.confirmMessage(messageId);
        if (!success) {
            throw new RuntimeException("扣减库存失败");
        }

        return Boolean.TRUE;
    }
}
