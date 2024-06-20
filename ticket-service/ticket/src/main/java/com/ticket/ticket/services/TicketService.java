package com.ticket.ticket.services;

import com.ticket.ticket.entities.Ticket;
import com.ticket.ticket.repositories.TicketRepository;
import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class TicketService {
    private final TicketRepository repository;
    private final RestTemplate restTemplate;

    @Autowired
    public TicketService(TicketRepository repository, RestTemplate restTemplate)
    {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Ticket save(Ticket ticket)
    {
        return this.repository.save(ticket);
    }
}
