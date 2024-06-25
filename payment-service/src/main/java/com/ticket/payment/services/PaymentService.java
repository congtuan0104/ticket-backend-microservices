package com.ticket.payment.services;

import com.ticket.payment.dto.CreatePromotionRequest;
import com.ticket.payment.dto.PaymentRequestDTO;
import com.ticket.payment.dto.VnpayDTO;
import com.ticket.payment.entitties.PaymentInfo;
import com.ticket.payment.entitties.Promotion;
import com.ticket.payment.repositories.PaymentRepository;
import com.ticket.payment.repositories.PromotionRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final QueryStringService queryStringService;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(
            final QueryStringService queryStringService,
            final PaymentRepository paymentRepository) {
        this.queryStringService = queryStringService;
        this.paymentRepository = paymentRepository;
    }

    @Autowired
    private Environment env;


    public String vnpayUrl(PaymentRequestDTO requestDTO)
            throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String currentDate = LocalDateTime.now().format(formatter);
        InetAddress localhost = InetAddress.getLocalHost();
        String ipAddr = localhost.getHostAddress().trim();

        VnpayDTO dto = new VnpayDTO();
        dto.setVnp_Amount("1000000");
        dto.setVnp_CreateDate(currentDate);
        dto.setVnp_Command("pay");
        dto.setVnp_CurrCode("VND");
        dto.setVnp_Locale("vn");
        dto.setVnp_IpAddr(InetAddress.getLocalHost().getHostAddress().trim());
        dto.setVnp_OrderInfo(requestDTO.getContent());
        dto.setVnp_OrderType("other");
        dto.setVnp_TxnRef(requestDTO.getPaymentId());
        dto.setVnp_TmnCode(env.getProperty("vnpay.terminalId"));
        dto.setVnp_ReturnUrl(env.getProperty("vnpay.returnUrl"));
        dto.setVnpUrl(env.getProperty("vnpay.vnpayUrl"));
        dto.setVnp_Version("2.1.0");
        dto.setVnp_HashSecret(env.getProperty("vnpay.secret"));
        return queryStringService.vnpayQueryString(dto);
    }

    public PaymentInfo save(PaymentInfo paymentInfo) {
        return this.paymentRepository.save(paymentInfo);
    }

    public Optional<PaymentInfo> findById(String id) {
        return this.paymentRepository.findById(id);
    }

    public void deleteById(String id) {
        this.paymentRepository.deleteById(id);
    }

}