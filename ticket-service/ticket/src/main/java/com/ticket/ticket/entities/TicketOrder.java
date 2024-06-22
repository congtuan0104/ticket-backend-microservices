package com.ticket.ticket.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "ticketOrder")


public class TicketOrder {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId orderId;
    private Date timeOrder;
    private int totalAmount;
    private String status;
    private String totalDiscount;
}
