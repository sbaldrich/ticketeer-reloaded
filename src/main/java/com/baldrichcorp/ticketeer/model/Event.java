package com.baldrichcorp.ticketeer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Event implements Serializable{
 
  private static final long serialVersionUID = 1L;
  
  @Id @GeneratedValue
  private Long id;
  private String name;
  private String description;
  
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
  
  public Long getId() {
    return id;
  }
  
}
