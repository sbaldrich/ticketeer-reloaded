package com.baldrichcorp.ticketeer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.baldrichcorp.ticketeer.model.Event;
import com.baldrichcorp.ticketeer.model.Order;
import com.baldrichcorp.ticketeer.repository.EventRepository;
import com.baldrichcorp.ticketeer.repository.OrderRepository;
import com.baldrichcorp.ticketeer.worker.OrderConsumer;

@SpringBootApplication
public class WorkerApplication{
  
  private static final Logger logger = LoggerFactory.getLogger(WorkerApplication.class);
  
  private static final String MAILBOX = "mailbox";

  public static void main(String[] args){
    SpringApplication.run(WorkerApplication.class, args);
  }
  
  @Bean
  public ConnectionFactory connectionFactory() {
    final CachingConnectionFactory factory = new CachingConnectionFactory();
    logger.info("Instantiating connection factory");
    factory.setHost("localhost");
    factory.setUsername("guest");
    factory.setPassword("guest");
    factory.setPort(5672);
    return factory;
  }

  @Bean
  MessageListenerAdapter adapter(OrderConsumer consumer) {
    MessageListenerAdapter adapter = new MessageListenerAdapter(consumer);
    adapter.setDefaultListenerMethod("process");
    return adapter;
  }

  @Bean
  Queue queue(){
    return new Queue(MAILBOX, false);
  }
  
  @Bean
  SimpleMessageListenerContainer container(MessageListener adapter, ConnectionFactory factory) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setMessageListener(adapter);
    container.setConnectionFactory(factory);
    container.setQueueNames(MAILBOX);
    return container;
  }
  
}
