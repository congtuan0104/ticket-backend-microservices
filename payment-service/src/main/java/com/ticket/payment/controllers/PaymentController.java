package com.ticket.payment.controllers;


import com.ticket.payment.dto.CreatePromotionRequest;
import com.ticket.payment.dto.PaymentRequestDTO;
import com.ticket.payment.dto.PaymentResponse;
import com.ticket.payment.entitties.PaymentInfo;
import com.ticket.payment.entitties.Promotion;
import com.ticket.payment.repositories.PaymentRepository;
import com.ticket.payment.services.PaymentService;
import com.ticket.payment.services.PromotionService;
import jakarta.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private Environment env;

    @RequestMapping(value = "/payment/test", method = RequestMethod.GET)
    public String test() {
        return "this is payment Service";
    }

    @RequestMapping(value = "/payment/test", method = RequestMethod.POST)
    public ResponseEntity<String> testPayment(@RequestBody PaymentRequestDTO dto) {
        try {
            String paymentUrl = paymentService.vnpayUrl(dto);
            return new ResponseEntity<>(paymentUrl, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentInfo paymentInfo) {
        try {
            PaymentInfo createdData = paymentInfo;
            createdData.setPaymentId(UUID.randomUUID().toString().split("-")[0]);
            PaymentInfo savedPaymentInfo = paymentService.save(createdData);
            final PaymentRequestDTO dto
                    = PaymentRequestDTO.builder()
                    .paymentId(savedPaymentInfo.getPaymentId())
                    .paymentMethod(savedPaymentInfo.getPaymentMethod())
                    .content(savedPaymentInfo.getContent())
                    .paymentAmount(savedPaymentInfo.getPaymentAmount())
                    .build();
            String paymentUrl = paymentService.vnpayUrl(dto);
            PaymentResponse response
                    = PaymentResponse.builder()
                    .paymentUrl(paymentUrl)
                    .orderId(savedPaymentInfo.getPaymentId())
                    .paymentId(savedPaymentInfo.getPaymentId())
                    .paymentMethod(savedPaymentInfo.getPaymentMethod())
                    .content(savedPaymentInfo.getContent())
                    .paymentAmount(savedPaymentInfo.getPaymentAmount())
                    .transactionTime(savedPaymentInfo.getTransactionTime())
                    .status(savedPaymentInfo.getStatus())
                    .paymentAccount(savedPaymentInfo.getPaymentAccount())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/payment/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PaymentInfo> updatePayment(
            @RequestBody PaymentInfo paymentInfo,
            @PathVariable("id") String id) {
        try {
            Optional<PaymentInfo> foundPaymentInfo = this.paymentService.findById(id);
            if (foundPaymentInfo.isPresent()) {
                PaymentInfo updatedData = foundPaymentInfo.get();
                updatedData.setContent(paymentInfo.getContent());
                updatedData.setPaymentMethod(paymentInfo.getPaymentMethod());
                updatedData.setPaymentAmount(paymentInfo.getPaymentAmount());
                updatedData.setPaymentAccount(paymentInfo.getPaymentAccount());
                updatedData.setStatus(paymentInfo.getStatus());
                updatedData.setOrderId(paymentInfo.getOrderId());
                updatedData.setTransactionTime(paymentInfo.getTransactionTime());
                return new ResponseEntity<>(this.paymentService.save(updatedData), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/payment/{id}", method = RequestMethod.GET)
    public ResponseEntity<PaymentInfo> findPaymentById(
            @PathVariable("id") String id
    ) {
        try {
            Optional<PaymentInfo> paymentInfo = this.paymentService.findById(id);
            if (paymentInfo.isPresent()) {
                return new ResponseEntity<>(paymentInfo.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/payment/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deletePaymentById(@PathVariable("id") String id) {
        try {
            Optional<PaymentInfo> paymentInfo = this.paymentService.findById(id);
            if (paymentInfo.isPresent()) {
                this.paymentService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/promotion/generate", method = RequestMethod.POST)
    public ResponseEntity<List<Promotion>> generatePromotion(
            @RequestBody CreatePromotionRequest request
            ) {
        try {
            return new ResponseEntity<>(this.promotionService.generatePromotion(request), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/promotion/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable("id") String id, @RequestBody Promotion promotion) {
        try {
            Optional<Promotion> foundPromotion = this.promotionService.findById(id);
            if(foundPromotion.isPresent()) {
                Promotion updatedData = foundPromotion.get();
                updatedData.setDiscount(promotion.getDiscount());
                updatedData.setDiscountType(promotion.getDiscountType());
                updatedData.setEventId(promotion.getEventId());
                updatedData.setExpiredDate(promotion.getExpiredDate());
                updatedData.setStatus(promotion.getStatus());
                return new ResponseEntity<>(this.promotionService.save(updatedData), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/promotion/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable("id") String id){
        try {
            Optional<Promotion> promotion = this.promotionService.findById(id);
            if(promotion.isPresent()) {
                return new ResponseEntity<>(promotion.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ResponseEntity<List<PaymentInfo>> findAllPayment(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String paymentMethod,
            @RequestParam(required = false) String paymentAccount,
            @RequestParam(required = false) String orderId,
            @RequestParam(required = false) LocalDateTime transactionTimeFrom,
            @RequestParam(required = false) LocalDateTime transactionTimeTo
    ) {
        try {
            List<PaymentInfo> payments =
                    this.paymentService.searchPaymentInfo(
                            status,
                            paymentMethod,
                            paymentAccount,
                            orderId,
                            transactionTimeFrom,
                            transactionTimeTo);
            if(payments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/promotion", method = RequestMethod.GET)
    public ResponseEntity<List<Promotion>> findAllPromotion(
            @RequestParam(required = false) String eventId,
            @RequestParam(required = false) String status
    ) {
        try {
            List<Promotion> promotions = this.promotionService.getAllPromotion();
            if (status != null && !status.isEmpty()) {
                promotions = this.promotionService.getPromotionByStatus(status);
            } else if(eventId != null && !eventId.isEmpty()) {
                promotions= this.promotionService.getPromotionByEventId(eventId);
            }
            if(promotions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(promotions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/promotion/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> deletePromotionById(@PathVariable("id") String id) {
        try {
            Optional<Promotion> promotion = this.promotionService.findById(id);
            if (promotion.isPresent()) {
                this.promotionService.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
