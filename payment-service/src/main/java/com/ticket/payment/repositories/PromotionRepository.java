package com.ticket.payment.repositories;

import com.ticket.payment.entitties.PaymentInfo;
import com.ticket.payment.entitties.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PromotionRepository extends MongoRepository<Promotion, String> {
    List<Promotion> findByStatus(String status);
    List<Promotion> findByEventId(String eventId);
}
