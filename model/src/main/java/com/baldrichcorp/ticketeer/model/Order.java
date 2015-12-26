package com.baldrichcorp.ticketeer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_order")
public class Order implements Serializable{

  private static final long serialVersionUID = 1L;
 
  @Id @GeneratedValue
  private Long id;
  
  @ManyToOne
  private Event event;
  private short numberOfTickets;
  private BigDecimal price;
  
  private Order(){}
  
  public Order(Event event, User user, short numberOfTickets, BigDecimal price) {
    this.event = event;
    this.numberOfTickets = numberOfTickets;
    this.price = price;
  }

  public void setEvent(Event event){
    this.event = event;
  }
  
  public Event getEvent() {
    return event;
  }
  
  public short getNumberOfTickets() {
    return numberOfTickets;
  }
  
  public BigDecimal getPrice() {
    return price;
  }
  
}
