package com.ticket.payment.dto;

import com.ticket.payment.entitties.PaymentInfo;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String paymentId;
    private String paymentMethod;
    private String content;
    private Number paymentAmount;
    private LocalDateTime transactionTime;
    private String paymentAccount;
    private String status;
    private String orderId;
    private String paymentUrl;
}
