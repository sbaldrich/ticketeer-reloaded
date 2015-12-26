package com.baldrichcorp.ticketeer.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baldrichcorp.ticketeer.model.Order;
import com.baldrichcorp.ticketeer.repository.OrderRepository;

@Service
public class OrderService {
  
  private AmqpTemplate template;
  private OrderRepository orderRepository;
  
  
  @Autowired
  public OrderService(AmqpTemplate jmsTemplate, OrderRepository orderRepository){
    this.template = jmsTemplate;
    this.orderRepository = orderRepository;
  }
  
  public void placeOrder(Order order){
    template.convertAndSend("mailbox", order);
  }
  
  public Iterable<Order> getOrdersByUser(){
    return orderRepository.findAll();
  }
}
