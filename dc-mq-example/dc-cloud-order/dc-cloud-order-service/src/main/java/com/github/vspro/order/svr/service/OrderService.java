package com.github.vspro.order.svr.service;


public interface OrderService {


    boolean placeOrder(Long userId, Long productId);

}
