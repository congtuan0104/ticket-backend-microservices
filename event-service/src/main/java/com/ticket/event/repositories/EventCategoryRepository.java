package com.ticket.event.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ticket.event.entities.EventCategory;

@Repository
public interface EventCategoryRepository extends MongoRepository<EventCategory, String>{

}
