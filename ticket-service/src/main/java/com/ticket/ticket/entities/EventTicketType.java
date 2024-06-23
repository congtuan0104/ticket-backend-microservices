package com.ticket.ticket.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "EventTicketType")

public class EventTicketType {

    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId eventTicketId;
    private String ticketName;
    private String description;
    private float price;
    private String eventId;
    private int inStock;
    private Date startDate;
    private Date endDate;
}
