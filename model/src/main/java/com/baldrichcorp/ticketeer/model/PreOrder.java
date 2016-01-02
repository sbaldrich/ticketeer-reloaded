package com.baldrichcorp.ticketeer.model;

public class PreOrder {
  public short seats;
  public long eventId;

  public PreOrder(){}
  
  public PreOrder(long eventId){
    this.eventId = eventId;
    this.seats = (short)1;
  }
  
  public short getSeats() {
    return seats;
  }

  public void setSeats(short seats) {
    this.seats = seats;
  }

  public long getEventId() {
    return eventId;
  }

  public void setEventId(long eventId) {
    this.eventId = eventId;
  }

}
