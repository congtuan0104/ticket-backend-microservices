package com.ticket.payment.entitties;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Document(collection = "paymentInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentInfo {


    @MongoId
    private String paymentId;

    private String paymentMethod;
    private String content;
    private Number paymentAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime transactionTime = LocalDateTime.now();

    private String paymentAccount;
    private String status;
    private String orderId;
}
