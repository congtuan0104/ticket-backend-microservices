package com.ticket.event.controllers;


import com.ticket.event.entities.Event;
import com.ticket.event.repositories.EventRepository;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@RestController
@RequestMapping("/api")

public class EventController {
    @Autowired
    private Environment env;

    @GetMapping
    public String home()
    {
        return "Event in Event Service is running at port " + env.getProperty("local.server.port");
    }

    @Autowired
    private EventRepository eventRepository;

    @GetMapping(value = "/event")
    public ResponseEntity<Map<String, Object>> getAllEvent
    (

        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size
    )
    {
        try {
            List<Event> events = new ArrayList<Event>();
            Pageable paging = PageRequest.of(page, size);
            Page<Event> pageEvents;
            pageEvents = eventRepository.findAll(paging);
            events = pageEvents.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("events", events);
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "event/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id")String eventId)
    {
        Optional<Event> eventData = eventRepository.findById(eventId);
        if (eventData.isPresent())
        {
            return new ResponseEntity<>(eventData.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/event/search")
    public ResponseEntity<Map<String,Object>> searchWithFilter(
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "12", name ="size") int size,
        @RequestParam(required = false, name = "cateId") String cateId,
        @RequestParam(required = false, name = "eventName") String eventName,
        @RequestParam(required = false, name = "locationName") String locationName,
        @RequestParam(required = false, name = "cityId") String cityId,
        @RequestParam(required = false, name = "organizationId") String organizationId,
        @RequestParam(required = false, name = "startTime") LocalDateTime startDateTime,
        @RequestParam(required = false, name ="endTime") LocalDateTime endDateTime,
        @RequestParam(required = false, name = "startPrice") float startPrice,
        @RequestParam(required = false, name = "endPrice") float endPrice
    )
    {
        try {
        Pageable paging = PageRequest.of(page, size);
        

        Page<Event> events1 = eventRepository.findByCateIdAndEventNameLikeAndLocationNameLikeAndEventAddressLikeAndCityIdAndOrganizationIdAndStartTimeBetweenAndBasePriceBetween(cateId, eventName, locationName, locationName, cityId, organizationId, startDateTime, endDateTime, startPrice, endPrice, paging);
        Page<Event> events2 = eventRepository.findByCateIdAndEventNameLikeAndLocationNameLikeAndEventAddressLikeAndCityIdAndOrganizationIdAndEndTimeBetweenAndBasePriceBetween(cateId, eventName, locationName, locationName, cityId, organizationId, startDateTime, endDateTime, startPrice, endPrice, paging);
        Page<Event> events3 = eventRepository.findByCateIdAndEventNameLikeAndLocationNameLikeAndEventAddressLikeAndCityIdAndOrganizationIdAndStartTimeLessThanAndEndTimeGreaterThanAndBasePriceBetween(cateId, eventName, locationName, locationName, cityId, organizationId, startDateTime, endDateTime, startPrice, endPrice, paging);
        
        List<Event> mergedList = Stream.concat(events1.stream(), events2.stream())
                                        .distinct()
                                        .collect(Collectors.toList());

        mergedList = Stream.concat(mergedList.stream(), events3.stream())
                                        .distinct()
                                        .collect(Collectors.toList());

        // Calculate start and end index
        int start = (page - 1) * size;
        int end = Math.min(start + page, mergedList.size());

        // Create a sublist of events for the requested page
        List<Event> sublist = mergedList.subList(start, end);

        // Create Page object
        Page<Event> pagedList = new PageImpl<>(sublist, PageRequest.of(page - 1, size), mergedList.size());

        List<Event> events = new ArrayList<Event>();
        events = pagedList.getContent();

        Map<String, Object> response = new HashMap<>();
            response.put("events", events);
            response.put("currentPage", pagedList.getNumber());
            response.put("totalItems", pagedList.getTotalElements());
            response.put("totalPages", pagedList.getTotalPages());
            
        return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/event")
    public ResponseEntity<Event> createEvent(@RequestBody Event inputEvent)
    {
        try {
            Event _event = eventRepository.save(inputEvent);
            return new ResponseEntity<>(_event, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PutMapping(value = "/event/")
    public ResponseEntity<Event> updateEventById(@RequestBody Event inputEvent)
    {
        Optional<Event> eventData = eventRepository.findById(inputEvent.getEventId().toString());
        if(eventData.isPresent())
        {
        Event _event = eventData.get();
        _event.setCateId(inputEvent.getCateId());
        _event.setCityId(inputEvent.getCityId());
        _event.setEndTime(inputEvent.getEndTime());
        _event.setEventAddress(inputEvent.getEventAddress());
        _event.setBasePrice(inputEvent.getBasePrice());
        _event.setEventDescription(inputEvent.getEventDescription());
        _event.setEventName(inputEvent.getEventName());
        _event.setLocationName(inputEvent.getLocationName());
        _event.setOrganizationId(inputEvent.getOrganizationId());
        _event.setSrc_eventLogo(inputEvent.getSrc_eventLogo());
        _event.setSrc_eventThumbnail(inputEvent.getSrc_eventThumbnail());

        return new ResponseEntity<>(eventRepository.save(_event), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value="/event/{id}")
    public ResponseEntity<HttpStatus> deleteEventById(@PathVariable("id") String eventId)
    {
        try {
            eventRepository.deleteById(eventId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
