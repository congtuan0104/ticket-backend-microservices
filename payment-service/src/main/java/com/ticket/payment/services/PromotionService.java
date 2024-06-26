package com.ticket.payment.services;

import com.ticket.payment.dto.CreatePromotionRequest;
import com.ticket.payment.entitties.Promotion;
import com.ticket.payment.repositories.PromotionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PromotionService {
    private final PromotionRepository promotionRepository;

    @Autowired
    public PromotionService(final PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<Promotion> generatePromotion(CreatePromotionRequest request) {
        int count = request.getCodeCount();
        List<Promotion> promotionList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Promotion promotion
                    = Promotion.builder()
                    .code(UUID.randomUUID().toString())
                    .eventId(request.getEventId())
                    .discount(request.getDiscount())
                    .discountType(request.getDiscountType())
                    .expiredDate(request.getExpiredDate())
                    .status("active")
                    .build();
            promotionList.add(promotion);
        }
        return this.promotionRepository.saveAll(promotionList);
    }

    public Optional<Promotion> findById(String id) {
        return this.promotionRepository.findById(id);
    }

    public Promotion save(Promotion promotion) {
        return this.promotionRepository.save(promotion);
    }

    public void deleteById(String id) {
        this.promotionRepository.deleteById(id);
    }

    public List<Promotion> getAllPromotion() {
        return this.promotionRepository.findAll();
    }

    public List<Promotion> getAllPromotionByStatus(String status) {
        return this.promotionRepository.findAllByStatus(status);
    }
}

