package com.baldrichcorp.ticketeer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baldrichcorp.ticketeer.model.Event;
import com.baldrichcorp.ticketeer.repository.EventRepository;

@Service
public class EventService {
  
  @Autowired
  private EventRepository ticketRepository;
  
  public Iterable<Event> getAll(){
    return ticketRepository.findAll();
  }
  
  public Event getEvent(Long id){
    return ticketRepository.findOne(id);
  }
}
