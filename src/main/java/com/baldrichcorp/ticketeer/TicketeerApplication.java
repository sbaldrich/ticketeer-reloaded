package com.baldrichcorp.ticketeer;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import com.baldrichcorp.ticketeer.model.Order;

@SpringBootApplication
public class TicketeerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketeerApplication.class, args);
    }
    
    private static final String MAILBOX = "mailbox";
    
    @Bean
    MessageListenerAdapter adapter(OrderConsumer consumer){
      MessageListenerAdapter adapter = new MessageListenerAdapter(consumer);
      adapter.setDefaultListenerMethod("process");
      return adapter;
    }
    
    @Bean
    SimpleMessageListenerContainer container(MessageListener adapter, ConnectionFactory factory){
      SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
      container.setMessageListener(adapter);
      container.setConnectionFactory(factory);
      container.setDestinationName(MAILBOX);
      return container;
    }
    
    @Component
    public static class OrderConsumer{
      private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);
      
      public void process(Order order){
        log.info("processing order for {} seats for {}", order.getNumberOfTickets(), order.getEvent().getName());
      }
    }
}
