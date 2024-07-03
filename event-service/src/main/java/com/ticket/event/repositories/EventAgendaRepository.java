package com.ticket.event.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ticket.event.entities.EventAgenda;

@Repository
public interface EventAgendaRepository extends MongoRepository<EventAgenda, String> {

}
