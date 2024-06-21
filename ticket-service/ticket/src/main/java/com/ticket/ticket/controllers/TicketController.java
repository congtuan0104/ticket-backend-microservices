package com.ticket.ticket.controllers;

import com.ticket.ticket.entities.Ticket;
import com.ticket.ticket.services.TicketService;

import jakarta.ws.rs.GET;

import com.ticket.ticket.entities.TicketOrder;
import com.ticket.ticket.repositories.TicketRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tickets")

public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private Environment env;

    @GetMapping
    public String home()
    {
        return "Ticket Service is running at port " + env.getProperty("local.server.port");
    }


    @GetMapping(value = "/ticketOrder")
    public String getTicketOrder()
    {
        return "Ticket Order path is here";
    }

    @PostMapping
    public Ticket save(@RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }
}


