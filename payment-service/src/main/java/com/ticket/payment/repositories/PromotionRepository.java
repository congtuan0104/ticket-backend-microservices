package com.ticket.payment.repositories;

import com.ticket.payment.entitties.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PromotionRepository extends MongoRepository<Promotion, String> {
}
