package com.baldrichcorp.ticketeer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.baldrichcorp.ticketeer.model.Order;

@Service
public class OrderService {
  
  private JmsTemplate jmsTemplate;
  
  @Autowired
  public OrderService(JmsTemplate jmsTemplate){
    this.jmsTemplate = jmsTemplate;
  }
  
  public void placeOrder(Order order){
    jmsTemplate.convertAndSend("mailbox", order);
  }
}
