package com.baldrichcorp.ticketeer.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "customer", uniqueConstraints = {
    @UniqueConstraint(name = "unique_handle", columnNames= "handle") 
    },indexes = {
        @Index(name = "handle_index", columnList = "handle")
    })
public class User implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id @GeneratedValue
  private Long id;
  private String handle;
  private String email;
  @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
  private List<Order> orders;
  
  public Long getId() {
    return id;
  }

  public String getHandle() {
    return handle;
  }
  
  public void setHandle(String handle) {
    this.handle = handle;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public List<Order> getOrders() {
    return orders;
  }
  
  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }
  
  
  
}
