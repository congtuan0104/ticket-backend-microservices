package com.ticket.event.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.event.entities.EventEvaluate;
import com.ticket.event.entities.EventEvaluate;
import com.ticket.event.repositories.EventEvaluateRepository;

@RestController
@RequestMapping("/api")
public class EventEvaluateController {
    @Autowired
    private EventEvaluateRepository eventEvaluateRepository;

    @GetMapping(value = "eventEvaluate")
    public ResponseEntity<Map<String, Object>> getAllEventEvaluate(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "12") int size
    )
    {
        try {
            List<EventEvaluate> eventEvaluates = new ArrayList<EventEvaluate>();
            Pageable paging = PageRequest.of(page, size);
            Page<EventEvaluate> pageEventEvaluates = eventEvaluateRepository.findAll(paging);
            eventEvaluates = pageEventEvaluates.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("eventEvaluates", eventEvaluates);
            response.put("currentPage", pageEventEvaluates.getNumber());
            response.put("totalItems", pageEventEvaluates.getTotalElements());
            response.put("totalPages", pageEventEvaluates.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/eventEvaluate/{id}")
    public ResponseEntity<EventEvaluate> getEventEvaluateById(
        @PathVariable("id") String evaluateId
    )
    {
        Optional<EventEvaluate> eventEvaluateData = eventEvaluateRepository.findById(evaluateId);
        if (eventEvaluateData.isPresent())
        {
            return new ResponseEntity<>(eventEvaluateData.get(), HttpStatus.OK);
            
        }
        else 
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "eventEvaluate/search")
    public ResponseEntity<List<EventEvaluate>> getAllEventEvaluateByUserId(
        @RequestParam(required = false, name = "userId") String inputUserId,
        @RequestParam(required = false, name = "eventId") String inputEventId,
        @RequestParam(required = false, name = "rating") Float inputRating,
        @RequestParam(required = false, name = "startTime") @DateTimeFormat LocalDateTime inputStartTime,
        @RequestParam(required = false, name = "endTime") @DateTimeFormat LocalDateTime inputEndTime
    )
    {
        List<EventEvaluate> eventEvaluateDatas = new ArrayList<EventEvaluate>() ;
        try {
            eventEvaluateDatas = eventEvaluateRepository.findByUserIdOrEventIdOrRatingOrTimeBetween(inputUserId, inputEventId, inputRating, inputStartTime, inputEndTime);
                return new ResponseEntity<>(eventEvaluateDatas, HttpStatus.OK);
 
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PostMapping(value = "/eventEvaluate")
    public ResponseEntity<EventEvaluate> createEventEvaluate(
        @RequestBody EventEvaluate eventEvaluate
    )
    {
        try {
            EventEvaluate _eventEvaluate = eventEvaluateRepository.save(eventEvaluate);
            return new ResponseEntity<>(_eventEvaluate, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/eventEvaluate")
    public ResponseEntity<EventEvaluate> updateEventEvaluateById(
        @RequestBody EventEvaluate inputEventEvaluate
    )
    {
        Optional<EventEvaluate> eventEvaluateData = eventEvaluateRepository.findById(inputEventEvaluate.getEvaluateId().toString());

        if (eventEvaluateData.isPresent())
        {
            EventEvaluate _eventEvaluate = eventEvaluateData.get();
            _eventEvaluate.setEvaluateContent(inputEventEvaluate.getEvaluateContent());
            _eventEvaluate.setEventId(inputEventEvaluate.getEventId());
            _eventEvaluate.setRating(inputEventEvaluate.getRating());
            _eventEvaluate.setTime(inputEventEvaluate.getTime());
            _eventEvaluate.setUserId(inputEventEvaluate.getUserId());

            return new ResponseEntity<>(eventEvaluateRepository.save(_eventEvaluate), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/eventEvaluate")
    public ResponseEntity<HttpStatus> deleteByEvaluateId(
        @PathVariable("id") String evaluateId
    )
    {
        try {
            eventEvaluateRepository.deleteById(evaluateId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
