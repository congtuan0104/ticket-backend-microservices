package com.ticket.paymentservice.dto;

import com.ticket.paymentservice.entitties.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private PaymentInfo info;
    private String paymentUrl;
}
