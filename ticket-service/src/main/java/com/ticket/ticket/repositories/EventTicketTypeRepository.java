package com.ticket.ticket.repositories;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ticket.ticket.entities.EventTicketType;

@Repository
public interface EventTicketTypeRepository extends MongoRepository<EventTicketType, String> {
    List<EventTicketType> findByTicketNameContaining(String ticketName);
}


