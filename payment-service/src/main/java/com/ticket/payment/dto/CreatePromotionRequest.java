package com.ticket.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePromotionRequest {
    private String eventId;
    private Double discount;
    private String discountType;
    private Integer codeCount;
    private Date expiredDate;
}
