package com.ticket.event.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

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
public class EventDTO {
    
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private String eventId;
    private String eventCategoryName;
    private String eventName;
    private String locationName;
    private String eventAddress;
    private String src_eventThumbnail; //? URL
    private String src_eventLogo; //? URL
    private String cityName;
    private String eventDescription;
    private String organizationName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private Float basePrice;
}
