package com.github.vspro.order.pd.dcoprovider.controller;

import com.github.vspro.order.svr.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public boolean placeOrder(@RequestParam("userId") Long userId, @RequestParam("productId")Long productId){
        return orderService.placeOrder(userId, productId);
    }
}
