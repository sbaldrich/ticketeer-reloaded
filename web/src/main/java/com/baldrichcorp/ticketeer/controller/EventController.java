package com.baldrichcorp.ticketeer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.baldrichcorp.ticketeer.model.Event;
import com.baldrichcorp.ticketeer.model.Order;
import com.baldrichcorp.ticketeer.model.PreOrder;
import com.baldrichcorp.ticketeer.service.EventService;
import com.baldrichcorp.ticketeer.service.OrderService;

@Controller
@SessionAttributes("order")
public class EventController {
  
  private static Logger log = LoggerFactory.getLogger(EventController.class);
  
  private EventService eventService;
  private OrderService orderService;
  
  @Autowired
  public EventController(EventService eventService, OrderService orderService){
    this.eventService = eventService;
    this.orderService = orderService;
  }
  
  @RequestMapping("/")
  public ModelAndView eventListing(Model model){
    return new ModelAndView("index").addObject("events", 
        StreamSupport.stream(eventService.getAll().spliterator(), false)
        .map(EventAndLink :: new).toArray());
  }
  
  @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
  public ModelAndView getEvent(@PathVariable long id){
    Event event = eventService.getEvent(id);
    return new ModelAndView("event").addObject("event", new EventAndLink(event));
  }
  
  @RequestMapping(value = "/purchase/{id}", method = RequestMethod.GET)
  public ModelAndView purchase(@PathVariable long id){
    Event event = eventService.getEvent(id);
    PreOrder preOrder = new PreOrder(id);
    return new ModelAndView("purchase").addObject("confirmation", 
        linkTo(methodOn(EventController.class).confirmOrder(preOrder)).withRel("confirm:" + id))
        .addObject(event)
        .addObject(preOrder);
  }
  
  @RequestMapping(value = "/purchase", method = RequestMethod.POST)
  public ModelAndView confirmOrder(PreOrder preOrder){
    Order order = orderService.generateOrder(preOrder);
    return new ModelAndView("summary").addObject("order", order);
  }
  
  @RequestMapping(value = "/confirm", method = RequestMethod.POST)
  public ModelAndView placeOrder(@ModelAttribute Order order, SessionStatus sessionStatus){
    log.info("placing order with code {} for event {}" , order.hashCode(), order.getEvent().getName());
    orderService.placeOrder(order);
    sessionStatus.setComplete();
    return new ModelAndView("confirmation");
  }
  
/*  public static class PreOrderAndLink{
    private PreOrder preOrder;
    private Link confirmation;
    
    public PreOrderAndLink(){}
    
    public PreOrderAndLink(long eventId){
      this.preOrder = new PreOrder(eventId);
      this.confirmation = linkTo(methodOn(EventController.class).confirmOrder(preOrder)).withRel("confirm:" + eventId);
    }

    public PreOrder getPreOrder(){
      return preOrder;
    }
    
    public Link getConfirmation() {
      return confirmation;
    }
    
  }*/
  
  public static class EventAndLink{
    private Event event;
    private Link details;
    private Link purchase;
        
    public EventAndLink(Event event) {
      this.event = event;
      this.details = linkTo(methodOn(EventController.class).
          getEvent(event.getId())).withRel(event.getName());
      this.purchase = linkTo(methodOn(EventController.class).
          purchase(event.getId())).withRel("purchase:" + event.getName());
    }

    public Event getEvent() {
      return event;
    }

    public Link getDetails() {
      return details;
    }
    
    public Link getPurchase(){
      return purchase;
    }
  }
  
}
