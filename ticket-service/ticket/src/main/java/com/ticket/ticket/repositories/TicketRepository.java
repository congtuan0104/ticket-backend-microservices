package com.ticket.ticket.repositories;

import com.ticket.ticket.entities.Ticket;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends MongoRepository<Ticket, ObjectId> {
}