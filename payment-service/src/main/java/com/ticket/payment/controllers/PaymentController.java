package com.ticket.payment.controllers;


import com.ticket.payment.dto.PaymentRequestDTO;
import com.ticket.payment.dto.PaymentResponse;
import com.ticket.payment.entitties.PaymentInfo;
import com.ticket.payment.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

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
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
            PaymentResponse response = new PaymentResponse(savedPaymentInfo, paymentUrl);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
