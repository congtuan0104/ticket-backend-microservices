package com.ticket.paymentservice.controllers;


import com.ticket.paymentservice.dto.PaymentRequestDTO;
import com.ticket.paymentservice.dto.VnpayDTO;
import com.ticket.paymentservice.entitties.PaymentInfo;
import com.ticket.paymentservice.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Autowired
    private Environment env;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String test() {
        return "this is payment Service";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<String> createPayment(@RequestBody PaymentInfo paymentInfo) {
        try {
            final PaymentInfo savedPaymentInfo = paymentService.save(paymentInfo);
            final PaymentRequestDTO dto
                    = PaymentRequestDTO.builder()
                    .paymentId(savedPaymentInfo.getPaymentId())
                    .paymentMethod(savedPaymentInfo.getPaymentMethod())
                    .content(savedPaymentInfo.getContent())
                    .paymentAmount(savedPaymentInfo.getPaymentAmount())
                    .build();
            String paymentUrl = paymentService.vnpayUrl(dto);
            return new ResponseEntity<>(paymentUrl, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
