package com.baldrichcorp.ticketeer.worker;

import org.junit.Test;

import com.baldrichcorp.ticketeer.model.Order;
import com.baldrichcorp.ticketeer.repository.OrderRepository;

import static org.mockito.Mockito.*;

public class OrderConsumerTest {
  
  @Test
  public void processedOrdersArePersisted(){
    OrderRepository orderRepository = mock(OrderRepository.class);
    OrderConsumer orderConsumer = new OrderConsumer(orderRepository);
    Order order = mock(Order.class);
    orderConsumer.process(order);
    verify(orderRepository).save(order);
  }
  
}
