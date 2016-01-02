package com.baldrichcorp.ticketeer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baldrichcorp.ticketeer.model.Event;
import com.baldrichcorp.ticketeer.repository.EventRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TicketeerWebApplication.class)
@WebAppConfiguration
public class TicketeerWebApplicationTests {

  @Autowired
  private WebApplicationContext context;
  
  @Autowired
  private EventRepository eventRepository;
  
  MockMvc mvc;
  
  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }
  
  @Test
  public void listAllEvents() throws Exception{
    mvc.perform(get("/")).andExpect(view().name("index")).
    andExpect(model().attributeExists("events"));
  }
  
  @Test
  public void listUserOrders() throws Exception {
    mvc.perform(get("/my-orders")).andExpect(view().name("orders")).
    andExpect(model().attributeExists("orders"));
  }
  
  @Test
  public void preOrder() throws Exception{
    Event event = new Event();
    event.setName("Test event");
    event.setDescription("Description");
    event.setPrice(new BigDecimal("250000"));
    event = eventRepository.save(event);
    
    mvc.perform(post("/purchase").
        contentType(MediaType.APPLICATION_FORM_URLENCODED).
        param("seats", "3")
        .param("eventId", String.valueOf(event.getId())))
        .andExpect(view().name("summary"))
        .andExpect(model().attributeExists("order"));
  }
  
}
