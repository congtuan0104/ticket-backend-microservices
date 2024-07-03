package com.ticket.payment.repositories.implement;

import com.ticket.payment.entitties.PaymentInfo;
import com.ticket.payment.repositories.PaymentCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PaymentCustomRepositoryImpl implements PaymentCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<PaymentInfo> findPayment(
            String status,
            String paymentMethod,
            String paymentAccount,
            String orderId,
            LocalDateTime transactionTimeFrom,
            LocalDateTime transactionTimeTo) {
        Query query = new Query();

        if (status != null && !status.isEmpty()) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        if (orderId != null && !orderId.isEmpty()) {
            query.addCriteria(Criteria.where("orderId").is(orderId));
        }
        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            query.addCriteria(Criteria.where("paymentMethod").is(paymentMethod));
        }
        if (paymentAccount != null && !paymentAccount.isEmpty()) {
            query.addCriteria(Criteria.where("paymentAccount").is(paymentAccount));
        }

        if (transactionTimeFrom != null && transactionTimeTo != null) {
            query.addCriteria(Criteria.where("transactionTime").gte(transactionTimeFrom));
        } else if (transactionTimeFrom != null) {
            query.addCriteria(Criteria.where("transactionTime").gte(transactionTimeFrom));
        } else if (transactionTimeTo != null) {
            query.addCriteria(Criteria.where("transactionTime").lte(transactionTimeTo));
        }

        return mongoTemplate.find(query, PaymentInfo.class);
    }
}
