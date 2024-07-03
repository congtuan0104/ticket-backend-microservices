package com.ticket.event.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.ticket.event.entities.EventCategory;
import com.ticket.event.repositories.EventCategoryRepository;

@RestController
@RequestMapping("/api")
public class EventCategoryController {
    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping(value = "/eventCategory")
    public ResponseEntity<List<EventCategory>> getAllEventCategory()
    {
        List<EventCategory> eventCategories = new ArrayList<EventCategory>();
        try {
            eventCategories =eventCategoryRepository.findAll();
            return new ResponseEntity<>(eventCategories, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value ="/eventCategory/{id}")
    public ResponseEntity<EventCategory> getEventCategoryById(
        @PathVariable("id") String eventCategoryId
    )
    {
        Optional<EventCategory> eventCategoryData = eventCategoryRepository.findById(eventCategoryId);
        if (eventCategoryData.isPresent())
        {
            return new ResponseEntity<>(eventCategoryData.get(), HttpStatus.OK);
        }
        else 
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/eventCategory")
    public ResponseEntity<EventCategory> createEventCategory(
        @RequestBody EventCategory eventCategory
    )
    {
        try {
            EventCategory _eventCategory = eventCategoryRepository.save(eventCategory);
            return new ResponseEntity<>(_eventCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/eventCategory")
    public ResponseEntity<EventCategory> updateEventCategoryByEventCategoryId(
        @RequestBody EventCategory inputEventCategory
    )
    {
        Optional<EventCategory> eventCategoryData = eventCategoryRepository.findById(inputEventCategory.getECategoryId().toString());
        if (eventCategoryData.isPresent())
        {
            EventCategory _eventCategory = eventCategoryData.get();
            _eventCategory.setECategoryName(inputEventCategory.getECategoryName());
            _eventCategory.setECategoryDescription(inputEventCategory.getECategoryDescription());

            return new ResponseEntity<>(eventCategoryRepository.save(_eventCategory), HttpStatus.OK);
        }
        else 
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/eventCategory")
    public ResponseEntity<HttpStatus> deleteByEventCategoryId(
        @RequestParam(name = "eCategoryId") String eCategoryId
    )
    {
        try {
            eventCategoryRepository.deleteById(eCategoryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
