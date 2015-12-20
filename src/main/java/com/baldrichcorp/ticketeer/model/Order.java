package com.baldrichcorp.ticketeer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Order implements Serializable{

  private static final long serialVersionUID = 1L;
 
  @Id @GeneratedValue
  private Long id;
  
  @ManyToOne
  private Event event;
  @ManyToOne
  private User user;
  private short numberOfTickets;
  private BigDecimal price;
  
  private Order(){}
  
  public Order(Event event, User user, short numberOfTickets, BigDecimal price) {
    this.event = event;
    this.user = user;
    this.numberOfTickets = numberOfTickets;
    this.price = price;
  }

  public Event getEvent() {
    return event;
  }
  
  public User getUser() {
    return user;
  }

  public short getNumberOfTickets() {
    return numberOfTickets;
  }
  
  public BigDecimal getPrice() {
    return price;
  }
  
}
