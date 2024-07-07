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
@Document(collection = "organization")

public class Organization {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId organizationId;
    private String userId;
    private String OrganizationName;
    private String description;
    private String imgLogo_src;
}
