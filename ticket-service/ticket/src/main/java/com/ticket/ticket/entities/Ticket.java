package com.ticket.ticket.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCrypt;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "tickets")

public class Ticket {

    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    private ObjectId ticketId;
    private ObjectId orderId;
    private ObjectId eventTicketId;
    private ObjectId promotionCode;
    private int quantity;
    private float totalMoney;
    private String status;
   
}
