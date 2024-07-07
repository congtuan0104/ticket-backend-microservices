package com.ticket.event.repositories;


import com.ticket.event.entities.Event;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends MongoRepository<Event, String> {
    // Page<Event> findByCateIdOrEventNameLikeOrLocationNameLikeOrEventAddressLikeOrCityIdOrOrganizationIdOrStartTimeBetweenOrBasePriceBetween(
    //     String cateId, 
    //     String eventName, 
    //     String locationName, 
    //     String eventAddress, 
    //     String cityId, 
    //     String organizationId, 
    //     LocalDateTime  startDateTime, 
    //     LocalDateTime endDateTime, 
    //     Float startPrice, 
    //     Float endPrice,
    //     Pageable pageable
    // );
   
    // Page<Event> findByCateIdOrEventNameLikeOrLocationNameLikeOrEventAddressLikeOrCityIdOrOrganizationIdOrEndTimeBetweenOrBasePriceBetween(
    //     String cateId, 
    //     String eventName, 
    //     String locationName, 
    //     String eventAddress, 
    //     String cityId, 
    //     String organizationId, 
    //     LocalDateTime  startDateTime, 
    //     LocalDateTime endDateTime, 
    //     Float startPrice, 
    //     Float endPrice,
    //     Pageable pageable

    // );

    // Page<Event> findByCateIdOrEventNameLikeOrLocationNameLikeOrEventAddressLikeOrCityIdOrOrganizationIdOrStartTimeGreaterThanOrEndTimeLessThanOrBasePriceBetween(
    //     String cateId, 
    //     String eventName, 
    //     String locationName, 
    //     String eventAddress, 
    //     String cityId, 
    //     String organizationId, 
    //     LocalDateTime  startDateTime, 
    //     LocalDateTime endDateTime, 
    //     Float startPrice, 
    //     Float endPrice,
    //     Pageable pageable

    // );
}
