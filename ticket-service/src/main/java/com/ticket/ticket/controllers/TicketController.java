package com.ticket.ticket.controllers;

import com.google.common.base.Optional;
import com.ticket.ticket.entities.EventTicketType;
import com.ticket.ticket.entities.Ticket;
import com.ticket.ticket.services.TicketService;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.GET;

import com.ticket.ticket.entities.TicketOrder;
import com.ticket.ticket.repositories.EventTicketTypeRepository;
import com.ticket.ticket.repositories.TicketOrderRepository;
import com.ticket.ticket.repositories.TicketRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private Environment env;


    @Autowired
    private TicketRepository ticketRepository;

  


    @GetMapping
    public String home()
    {
        return "Ticket Service is running at port " + env.getProperty("local.server.port");
    }

    @GetMapping(value = "/tickets/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") String id)
    {
        java.util.Optional<Ticket> ticketData = ticketRepository.findById(id);
        if (ticketData.isPresent())
        {
            return new ResponseEntity<>(ticketData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/tickets")
    public ResponseEntity<List<Ticket>> getAllTicket()
    {
        try{
            List<Ticket> tickets= new ArrayList<Ticket>();

            ticketRepository.findAll().forEach(tickets::add);
          
            if (tickets.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        }
        catch (Exception e){
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public Ticket save(@RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }

    @DeleteMapping("/tickets/{id}")
    public ResponseEntity<HttpStatus> deleteTicketById(@PathVariable("id") String id)
    {
        try {
            ticketRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/ticket/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable("id") String id, @RequestBody Ticket ticket)
    {
        java.util.Optional<Ticket> ticketData = ticketRepository.findById(id);
        if (ticketData.isPresent())
        {
            Ticket _ticket = ticketData.get();
            _ticket.setEventTicketId(ticket.getEventTicketId());
            _ticket.setOrderId(ticket.getOrderId());
            _ticket.setPromotionCode(ticket.getPromotionCode());
            _ticket.setQuantity(ticket.getQuantity());
            _ticket.setStatus(ticket.getStatus());
            _ticket.setTotalMoney(ticket.getTotalMoney());
            return new ResponseEntity<>(ticketRepository.save(_ticket), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //TicketOrder
    
    @Autowired
    private TicketOrderRepository ticketOrderRepository;

    @GetMapping(value = "/ticketOrder")
    public ResponseEntity<List<TicketOrder>> getAllTicketOrder()
    {
        try{
            List<TicketOrder> ticketOrders= new ArrayList<TicketOrder>();

            ticketOrderRepository.findAll().forEach(ticketOrders::add);
          
            if (ticketOrders.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ticketOrders, HttpStatus.OK);
        }
        catch (Exception e){
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/ticketOrder/{id}")
    public ResponseEntity<TicketOrder> getTicketOrderByid(@PathVariable("id") String orderId)
    {
        java.util.Optional<TicketOrder> ticketOrderData = ticketOrderRepository.findById(orderId);
        if (ticketOrderData.isPresent())
        {
            return new ResponseEntity<>(ticketOrderData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value= "/ticketOrder")
    public ResponseEntity<TicketOrder> setTicketOrder(@RequestBody TicketOrder ticketOrder)
    {
        try
        {
            TicketOrder _ticketOrder = ticketOrderRepository.save(ticketOrder);
            return new ResponseEntity<>(_ticketOrder, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/ticketOrder/{id}")
    public  ResponseEntity<TicketOrder> updateTicketOrder(@PathVariable("id") String orderId, @RequestBody TicketOrder ticketOrder)
    {
        java.util.Optional<TicketOrder> ticketOrderData = ticketOrderRepository.findById(orderId);
        if (ticketOrderData.isPresent())
        {
            TicketOrder _ticketOrder = ticketOrderData.get();
            _ticketOrder.setTimeOrder(ticketOrder.getTimeOrder());
            _ticketOrder.setTotalAmount(ticketOrder.getTotalAmount());
            _ticketOrder.setTotalDiscount(ticketOrder.getTotalDiscount());
            _ticketOrder.setStatus(ticketOrder.getStatus());
            return new ResponseEntity<>(ticketOrderRepository.save(_ticketOrder), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/eventTicketType/{id}")
    public ResponseEntity<HttpStatus> DeleteTicketOrderById(@PathVariable("id") String ticketOrderId)
    {
        try {
            ticketOrderRepository.deleteById(ticketOrderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Event Ticket Type

    @Autowired
    private EventTicketTypeRepository eventTicketTypeRepository;

    @GetMapping(value = "/eventTicketType")
    public ResponseEntity<List<EventTicketType>> getAllEventTicketType()
    {
        List<EventTicketType> eventTicketTypes = new ArrayList<EventTicketType>();
        try {
            eventTicketTypeRepository.findAll().forEach(eventTicketTypes::add);

            if (eventTicketTypes.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<>(eventTicketTypes, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/eventTicketType/{id}")
    public ResponseEntity<EventTicketType> getEventTicketTypeById(@PathVariable("id") String eventTicketId)
    {
        java.util.Optional<EventTicketType> eventTicketTypeData = eventTicketTypeRepository.findById(eventTicketId);
        if (eventTicketTypeData.isPresent())
        {
            return new ResponseEntity<>(eventTicketTypeData.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/eventTicketType")
    public ResponseEntity<List<EventTicketType>> getEventTicketTypeByEventId(String eventId)
    {
        List<EventTicketType> eventTicketTypes = new ArrayList<EventTicketType>();
        try {
            eventTicketTypeRepository.findByEventIdContaining(eventId).forEach(eventTicketTypes::add);

            if (eventTicketTypes.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else
            {
                return new ResponseEntity<>(eventTicketTypes, HttpStatus.OK);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/eventTicketType")
    public ResponseEntity<EventTicketType> createEventTicketType(@RequestBody EventTicketType eventTicketType)
    {
        try
        {
            EventTicketType _eventTicketType = eventTicketTypeRepository.save(eventTicketType);
            return new ResponseEntity<>(_eventTicketType, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/eventTicketType/{id}")
    public ResponseEntity<HttpStatus> deleteEventTicketTypeById(@PathVariable("id") String eventTicketId)
    {
        try {
            eventTicketTypeRepository.deleteById(eventTicketId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
