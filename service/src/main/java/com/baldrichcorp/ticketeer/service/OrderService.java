package com.baldrichcorp.ticketeer.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baldrichcorp.ticketeer.model.Event;
import com.baldrichcorp.ticketeer.model.Order;
import com.baldrichcorp.ticketeer.model.PreOrder;
import com.baldrichcorp.ticketeer.repository.EventRepository;
import com.baldrichcorp.ticketeer.repository.OrderRepository;

@Service
public class OrderService {
  
  private AmqpTemplate template;
  private OrderRepository orderRepository;
  private EventRepository eventRepository;
  
  @Autowired
  public OrderService(AmqpTemplate jmsTemplate, OrderRepository orderRepository, EventRepository eventRepository){
    this.template = jmsTemplate;
    this.orderRepository = orderRepository;
    this.eventRepository = eventRepository;
  }
  
  public void placeOrder(Order order){
    template.convertAndSend("mailbox", order);
  }
  
  public Iterable<Order> getOrdersByUser(){
    return orderRepository.findAll();
  }
  
  public Order generateOrder(PreOrder to){
    Event event = eventRepository.findOne(to.getEventId());
    Order order = new Order(event, null, to.getSeats(), event.getPrice(to.getSeats()));
    return order;
  }
}
