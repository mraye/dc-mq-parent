package com.github.vspro.stock.svr.service;

public interface StockService {

    boolean deductStock(Long messageId,Long productId);

}
