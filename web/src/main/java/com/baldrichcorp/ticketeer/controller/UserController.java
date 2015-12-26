package com.baldrichcorp.ticketeer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baldrichcorp.ticketeer.service.OrderService;

@Controller
public class UserController {
  
  private OrderService orderService;
  
  @Autowired
  public UserController(OrderService orderService){
    this.orderService = orderService;
  }
  
  @RequestMapping("/my-orders")
  public String orders(Model model){
    model.addAttribute("orders", orderService.getOrdersByUser());
    return "orders";
  }
}
