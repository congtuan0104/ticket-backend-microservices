package com.ticket.event.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ticket.event.entities.EventEvaluate;


@Repository
public interface EventEvaluateRepository extends MongoRepository<EventEvaluate, String>{
    List<EventEvaluate> findByUserIdOrEventIdOrRatingOrTimeBetween(String userId, String eventId, Float rating, LocalDateTime startTime, LocalDateTime endTime);
}
