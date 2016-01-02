package com.baldrichcorp.ticketeer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Event implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String description;
  private BigDecimal price;
  private int capacity;
  private int allocation;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getPrice(short tickets){
    return this.price.multiply(BigDecimal.valueOf(tickets));
  }
  
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  
  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int getAllocation() {
    return allocation;
  }

  public void setAllocation(int allocation) {
    this.allocation = allocation;
  }

  public Long getId() {
    return id;
  }

}
