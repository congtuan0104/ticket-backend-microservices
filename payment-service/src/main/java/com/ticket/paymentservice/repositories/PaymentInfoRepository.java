package com.ticket.paymentservice.repositories;

import com.ticket.paymentservice.entitties.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Number> {
}
