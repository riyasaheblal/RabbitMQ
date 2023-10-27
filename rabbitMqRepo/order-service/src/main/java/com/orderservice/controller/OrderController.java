package com.orderservice.controller;

import com.orderservice.dto.Order;
import com.orderservice.dto.OrderEvent;
import com.orderservice.publisher.OrderProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody Order order){
      //  order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("Pending");
        orderEvent.setMessage("Order is in pending status");
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);
        return ResponseEntity.ok("Order placed successfully");
    }
}
