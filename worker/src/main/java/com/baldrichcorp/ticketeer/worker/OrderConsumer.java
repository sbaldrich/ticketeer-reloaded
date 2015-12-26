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
  private EventRepository eventRepository;
  
  @Autowired
  public OrderConsumer(OrderRepository orderRepository, EventRepository eventRepository){
    this.orderRepository = orderRepository;
    this.eventRepository = eventRepository;
  }
  
  public void process(Order order) {
    log.info(" === processing order for {} seats for {} [ {} ]", order.getNumberOfTickets(), order.getEvent().getName(), order.hashCode());
    log.info("== querying existing events ==");
    Iterable<Event> events = eventRepository.findAll();
    for(Event ev : events){
      log.info(ev.getId() + " - " + ev.getName());
    }
    log.info("== done ==");
    orderRepository.save(order);
  }
}