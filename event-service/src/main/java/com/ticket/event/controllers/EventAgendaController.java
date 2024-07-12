package com.ticket.event.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.ticket.event.entities.EventAgenda;
import com.ticket.event.repositories.EventAgendaRepository;

@RestController
@RequestMapping("/api/event")
public class EventAgendaController {


    @Autowired
    private EventAgendaRepository eventAgendaRepository;


    @GetMapping(value= "/eventAgenda")
    public ResponseEntity<List<EventAgenda>> getAllEventAgenda()
    {
        List<EventAgenda> eventAgendas = new ArrayList<EventAgenda>();
        try {
            
        eventAgendas = eventAgendaRepository.findAll();
        return new ResponseEntity<>(eventAgendas, HttpStatus.OK);
        
        } catch (Exception e) {
        // TODO: handle exception
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/eventAgenda/{id}")
    public ResponseEntity<EventAgenda> getEventAgendaByAgendaId(@PathVariable("id") String agendaId)
    {
        Optional<EventAgenda> eventAgendaData = eventAgendaRepository.findById(agendaId);
        if (eventAgendaData.isPresent())
        {
            return new ResponseEntity<>(eventAgendaData.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "eventAgenda/eventId")
    public ResponseEntity<Map<String, Object>> searchEventAgendaByEventId(
        @RequestParam(defaultValue = "0", name = "page") int page,
        @RequestParam(defaultValue = "12", name ="size") int size,
        @RequestParam(name = "eventId") String eventId
    )
    {
        List<EventAgenda> eventAgendas= new ArrayList<EventAgenda>();
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<EventAgenda> pageEventAgendas;
            pageEventAgendas = eventAgendaRepository.findAll(paging);
            if (pageEventAgendas.isEmpty())
            {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            eventAgendas = pageEventAgendas.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("eventAgendas", eventAgendas);
            response.put("currentPage", pageEventAgendas.getNumber());
            response.put("totalItems", pageEventAgendas.getTotalElements());
            response.put("totalPages", pageEventAgendas.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }        
    }

    @PostMapping(value = "/eventAgenda")
    public ResponseEntity<EventAgenda> createEventAgenda(
        @RequestBody EventAgenda eventAgenda
    )
    {
        try {
            EventAgenda _eventAgenda = eventAgendaRepository.save(eventAgenda);
            return new ResponseEntity<>(_eventAgenda, HttpStatus.CREATED);
        } catch (Exception e) {
            // TODO: handle exception
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(value = "/eventAgenda")
    public ResponseEntity<EventAgenda> updateAgendaByAgendaId(
        @RequestBody EventAgenda inputEventAgenda
    )
    {
        Optional<EventAgenda> eventAgendaData = eventAgendaRepository.findById(inputEventAgenda.getAgendaId().toString());
        if (eventAgendaData.isPresent())
        {
            EventAgenda _eventAgenda = eventAgendaData.get();
            _eventAgenda.setEvaluateContent(inputEventAgenda.getEvaluateContent());
            _eventAgenda.setEventId(inputEventAgenda.getEventId());
            _eventAgenda.setRating(inputEventAgenda.getRating());
            _eventAgenda.setTime(inputEventAgenda.getTime());

            return new ResponseEntity<>(eventAgendaRepository.save(_eventAgenda), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping(value = "/eventAgenda")
    public ResponseEntity<HttpStatus> deleteByAgendaId(
        @PathVariable("id") String agendaId
    )
    {
        try {
            eventAgendaRepository.deleteById(agendaId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // TODO: handle exception

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
