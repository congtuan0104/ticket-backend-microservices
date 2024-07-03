package com.ticket.ticket.repositories;

import com.ticket.ticket.entities.TicketOrder;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketOrderRepository extends MongoRepository<TicketOrder, String> {

}