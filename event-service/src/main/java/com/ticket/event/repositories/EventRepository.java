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
public interface EventRepository extends MongoRepository<Event, String>, customEventRepository {
    Page<Event> findByCateIdAndEventNameLikeAndLocationNameLikeAndEventAddressLikeAndCityIdAndOrganizationIdAndStartTimeBetweenAndBasePriceBetween(
        String cateId, 
        String eventName, 
        String locationName, 
        String eventAddress, 
        String cityId, 
        String organizationId, 
        LocalDateTime  startDateTime, 
        LocalDateTime endDateTime, 
        float startPrice, 
        float endPrice,
        Pageable pageable
    );
   
    Page<Event> findByCateIdAndEventNameLikeAndLocationNameLikeAndEventAddressLikeAndCityIdAndOrganizationIdAndEndTimeBetweenAndBasePriceBetween(
        String cateId, 
        String eventName, 
        String locationName, 
        String eventAddress, 
        String cityId, 
        String organizationId, 
        LocalDateTime  startDateTime, 
        LocalDateTime endDateTime, 
        float startPrice, 
        float endPrice,
        Pageable pageable

    );

    Page<Event> findByCateIdAndEventNameLikeAndLocationNameLikeAndEventAddressLikeAndCityIdAndOrganizationIdAndStartTimeLessThanAndEndTimeGreaterThanAndBasePriceBetween(
        String cateId, 
        String eventName, 
        String locationName, 
        String eventAddress, 
        String cityId, 
        String organizationId, 
        LocalDateTime  startDateTime, 
        LocalDateTime endDateTime, 
        float startPrice, 
        float endPrice,
        Pageable pageable

    );
}
