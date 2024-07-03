package com.ticket.event.entities;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCrypt;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "event")

public class Event
{
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId eventId;
    private String cateId;
    private String eventName;
    private String locationName;
    private String eventAddress;
    private String src_eventThumbnail; //? URL
    private String src_eventLogo; //? URL
    private String cityId;
    private String eventDescription;
    private String organizationId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private float basePrice;
}