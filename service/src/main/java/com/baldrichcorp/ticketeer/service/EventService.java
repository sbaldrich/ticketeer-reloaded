package com.baldrichcorp.ticketeer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baldrichcorp.ticketeer.model.Event;
import com.baldrichcorp.ticketeer.repository.EventRepository;

@Service
public class EventService {
  
  private EventRepository eventRepository;
  
  @Autowired
  public EventService(EventRepository eventRepository){
    this.eventRepository = eventRepository;
  }
  
  public Iterable<Event> getAll(){
    return eventRepository.findAll();
  }
  
  public Event getEvent(Long id){
    return eventRepository.findOne(id);
  }
}
