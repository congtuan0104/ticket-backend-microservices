package com.ticket.payment.repositories;

import com.ticket.payment.entitties.PaymentInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentCustomRepository {
    List<PaymentInfo> findPayment(
            String status,
            String paymentMethod,
            String paymentAccount,
            String orderId,
            LocalDateTime transactionTimeFrom,
            LocalDateTime transactionTimeTo);
}
