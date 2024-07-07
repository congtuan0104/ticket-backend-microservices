package com.ticket.event.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClients;
import com.ticket.event.entities.City;
import com.ticket.event.entities.Event;
import com.ticket.event.entities.EventCategory;
import com.ticket.event.entities.EventDTO;
import com.ticket.event.entities.Organization;
import com.ticket.event.entities.PagedResponse;
import com.ticket.event.repositories.EventRepository;

@Service
public class EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    
    public PagedResponse<EventDTO> eventFilterHandler(
        String eventCategoryId,
        String eventName,
        String locationName,
        String eventAddress, 
        String cityId,
        String organizationId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Float basePrice,
        int page,
        int size
        )
    {
        List<Event> events = new ArrayList<Event>();
        MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create("mongodb+srv://huynhtrongnghia10012000:18120478@ryucluster.hkwulwu.mongodb.net/?retryWrites=true&w=majority&appName=RyuCluster"), "event-service");
        Query query = new Query();
        if (!eventCategoryId.isBlank() && eventCategoryId!= null)
        {
        query.addCriteria(Criteria.where("eventCategoryId").is(eventCategoryId));
        }

        if (!eventName.isBlank() && eventName!= null)
        {
        query.addCriteria(Criteria.where("eventName").regex(".*"+eventName+".*"));
        }
        
        if (!cityId.isBlank() && cityId!=null)
        {
        query.addCriteria(Criteria.where("cityId").is(cityId));
        }
        if (locationName.isBlank()==false)
        {
        query.addCriteria(Criteria.where("locationName").regex(".*"+locationName+".*"));
        }
        if (eventAddress.isBlank()==false)
        {
        query.addCriteria(Criteria.where("eventAddress").regex(".*"+eventAddress+".*"));
        }
        if (organizationId.isBlank()==false)
        {
        query.addCriteria(Criteria.where("organizationId").is(organizationId));
        };
        if (startTime != null && endTime != null)
        {
        

            Criteria timeFilter = new Criteria().orOperator(
            Criteria.where("startTime").gte(startTime).lte(endTime),
            Criteria.where("endTime").gte(startTime).lte(endTime),
            new Criteria().andOperator(
                Criteria.where("startTime").lte(startTime),
                Criteria.where("endTime").gte(endTime)
            )
            );
            // Criteria.where("startTime").lte(startTime).andOperator(Criteria.where("endTime").gte(endTime
            // )
            //)
            query.addCriteria(timeFilter);
        }
        if (basePrice != null)
        {
        query.addCriteria(Criteria.where("basePrice").lte(basePrice));
        }
        
        long totalItems = mongoTemplate.count(query, Event.class);

        Pageable pageable = PageRequest.of(page, size);
        query.with(pageable);
        mongoTemplate.find(query, Event.class);

        logger.info("Mongo Query: {}", query);
        events = mongoTemplate.find(query, Event.class);
        logger.info("Events found: {}", events.size());

        
        List<EventDTO> eventDTOs = events.stream().map(event -> {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setEventName(event.getEventName());
            // Lấy eventCategoryName từ eventCategory collection và set vào eventDTO
            EventCategory eventCategory = mongoTemplate.findById(event.getEventCategoryId(), EventCategory.class);
            if (eventCategory != null) {
                eventDTO.setEventCategoryName(eventCategory.getEventCategoryName());
            }
            // Lấy cityName từ city collection và set vào eventDTO
            City city = mongoTemplate.findById(event.getCityId(), City.class);
            if (city != null) {
                eventDTO.setCityName(city.getCityName());
            }
            // Lấy organizationName từ city organization và set vào eventDTO
            Organization organization = mongoTemplate.findById(event.getOrganizationId(), Organization.class);
            if (city != null) {
                eventDTO.setOrganizationName(organization.getOrganizationName());
            }
            // Thêm các field khác nếu cần thiết
            eventDTO.setEventId(event.getEventId().toString());
            eventDTO.setLocationName(event.getLocationName());
            eventDTO.setEventAddress(event.getEventAddress());
            eventDTO.setSrc_eventLogo(event.getSrc_eventLogo());
            eventDTO.setSrc_eventThumbnail(event.getSrc_eventThumbnail());
            eventDTO.setEventDescription(event.getEventDescription());
            eventDTO.setStartTime(event.getStartTime());
            eventDTO.setEndTime(event.getEndTime());
            eventDTO.setBasePrice(event.getBasePrice());

            return eventDTO;
        }).collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new PagedResponse<>(totalItems, totalPages, page, eventDTOs);
    }
    
}
