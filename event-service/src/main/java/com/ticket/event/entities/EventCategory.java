package com.ticket.event.entities;

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
@Document(collection = "eventCategory")

public class EventCategory {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId eCategoryId;
    private String eCategoryName;
    private String eCategoryDescription;
    
}
