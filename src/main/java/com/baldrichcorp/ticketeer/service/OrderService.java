package com.baldrichcorp.ticketeer.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baldrichcorp.ticketeer.model.Order;

@Service
public class OrderService {
  
  private AmqpTemplate template;
  
  @Autowired
  public OrderService(AmqpTemplate jmsTemplate){
    this.template = jmsTemplate;
  }
  
  public void placeOrder(Order order){
    template.convertAndSend("mailbox", order);
  }
}
