package com.ticket.ticket.controllers;

import com.ticket.ticket.entities.Ticket;
import com.ticket.ticket.services.TicketService;

import com.ticket.ticket.entities.Ticket;

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

    @PostMapping
    public Ticket save(@RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }
}
