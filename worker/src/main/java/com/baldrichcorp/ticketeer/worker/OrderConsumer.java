package com.baldrichcorp.ticketeer.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baldrichcorp.ticketeer.model.Event;
import com.baldrichcorp.ticketeer.model.Order;
import com.baldrichcorp.ticketeer.repository.EventRepository;
import com.baldrichcorp.ticketeer.repository.OrderRepository;

@Component
public class OrderConsumer {
  
  private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);
  
  private OrderRepository orderRepository;
  
  @Autowired
  public OrderConsumer(OrderRepository orderRepository){
    this.orderRepository = orderRepository;
  }
  
  public void process(Order order) {
    orderRepository.save(order);
  }
}