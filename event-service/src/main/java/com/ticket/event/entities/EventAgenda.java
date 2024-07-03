package com.ticket.event.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "eventAgenda")

public class EventAgenda {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId agendaId;
    private LocalDateTime time;
    private String eventId;
    private String evaluateContent;
    private Float rating;

}
