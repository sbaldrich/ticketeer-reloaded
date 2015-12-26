package com.baldrichcorp.ticketeer.repository;

import org.springframework.data.repository.CrudRepository;

import com.baldrichcorp.ticketeer.model.Event;

public interface EventRepository extends CrudRepository<Event, Long>{}
