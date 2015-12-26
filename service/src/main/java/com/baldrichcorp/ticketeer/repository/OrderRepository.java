package com.baldrichcorp.ticketeer.repository;

import org.springframework.data.repository.CrudRepository;

import com.baldrichcorp.ticketeer.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{}
