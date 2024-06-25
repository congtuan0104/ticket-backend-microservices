package com.ticket.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequestDTO {
    private String paymentId;
    private String paymentMethod;
    private Integer paymentAmount;
    private String content;
    private Number orderId;
}
