package com.orderservice.publisher;

import com.orderservice.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);

    @Value("${rabbitmq.order.routing-key}")
    private String orderRoutingKey;

    @Value("${rabbitmq.email.routing-key}")
    private String emailRoutingKey;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    private RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderEvent orderEvent){
        LOGGER.info(String.format("Order event -> %s", orderEvent));
        //send event to order queue
        rabbitTemplate.convertAndSend(exchange,orderRoutingKey, orderEvent);
        // send event to email queue
        rabbitTemplate.convertAndSend(exchange,emailRoutingKey, orderEvent);
    }
}
