package com.baldrichcorp.ticketeer.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baldrichcorp.ticketeer.model.Event;
import com.baldrichcorp.ticketeer.model.Order;
import com.baldrichcorp.ticketeer.service.EventService;
import com.baldrichcorp.ticketeer.service.OrderService;

@Controller
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
    return new ModelAndView("purchase").addObject("form", new PurchaseForm(event.getId())).addObject(event);
  }
  
  @RequestMapping(value = "/purchase", method = RequestMethod.POST)
  public ModelAndView confirmOrder(PurchaseForm form){
    Order order = new Order(eventService.getEvent(form.getEventId()), null, form.getSeats(), null);
    log.info("placing order with code {}" , order.hashCode());
    orderService.placeOrder(order);
    return new ModelAndView("confirmation").addObject("order", order);
  }
  
  public static class PurchaseForm{
    private long eventId;
    private short seats;
    private Link confirmation;
    
    public PurchaseForm(){}
    
    public PurchaseForm(long eventId){
      this.eventId = eventId;
      this.confirmation = linkTo(methodOn(EventController.class).confirmOrder(this)).withRel("confirm:" + eventId);
    }

    public long getEventId(){
      return eventId;
    }
    
    public void setEventId(long eventId){
      this.eventId = eventId;
    }
    
    public short getSeats() {
      return seats;
    }

    public void setSeats(short seats) {
      this.seats = seats;
    }

    public Link getConfirmation() {
      return confirmation;
    }
    
  }
  
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
