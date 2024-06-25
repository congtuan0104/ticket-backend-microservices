package com.ticket.payment.dto;

import com.ticket.payment.entitties.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private PaymentInfo paymentInfo;
    private String paymentUrl;
}
