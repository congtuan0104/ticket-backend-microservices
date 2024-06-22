package com.ticket.paymentservice.services;

import com.ticket.paymentservice.dto.PaymentRequestDTO;
import com.ticket.paymentservice.dto.VnpayDTO;
import com.ticket.paymentservice.entitties.PaymentInfo;
import com.ticket.paymentservice.repositories.PaymentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.InetAddress;
import java.util.Optional;

@Service
public class PaymentService {
    private final RestTemplate restTemplate;
    private final QueryStringService queryStringService;

    @Autowired
    public PaymentService(RestTemplate restTemplate, final QueryStringService queryStringService) {
        this.restTemplate = restTemplate;
        this.queryStringService = queryStringService;
    }

    @Autowired
    private Environment env;

    @Autowired
    private PaymentInfoRepository paymentRepository;

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
        dto.setVnp_TxnRef(requestDTO.getPaymentId().toString());
        dto.setVnp_TmnCode(env.getProperty("vnpay.terminalId"));
        dto.setVnp_ReturnUrl(env.getProperty("vnpay.returnUrl"));
        dto.setVnpUrl(env.getProperty("vnpay.vnpayUrl"));
        dto.setVnp_Version("2.1.0");
        dto.setVnp_HashSecret(env.getProperty("vnpay.secret"));
        return queryStringService.vnpayQueryString(dto);
    }

    public PaymentInfo save(PaymentInfo paymentInfo) {
        return paymentRepository.save(paymentInfo);
    }

    public Optional<PaymentInfo> findById(Number id) {
        return paymentRepository.findById(id);
    }
}