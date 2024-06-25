package com.ticket.payment.services;

import com.ticket.payment.dto.VnpayDTO;
import org.bouncycastle.crypto.macs.HMac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


//https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?vnp_Amount=1806000&vnp_Command=pay&vnp_CreateDate=20210801153333&vnp_CurrCode=VND&vnp_IpAddr=127.0.0.1&vnp_Locale=vn&vnp_OrderInfo=Thanh+toan+don+hang+%3A5&vnp_OrderType=other&vnp_ReturnUrl=https%3A%2F%2Fdomainmerchant.vn%2FReturnUrl&vnp_TmnCode=DEMOV210&vnp_TxnRef=5&vnp_Version=2.1.0&vnp_SecureHash=3e0d61a0c0534b2e36680b3f7277743e8784cc4e1d68fa7d276e79c23be7d6318d338b477910a27992f5057bb1582bd44bd82ae8009ffaf6d141219218625c42

@Component
public class QueryStringService {

    private final HashingService hashingService;

    @Autowired
    public QueryStringService(final HashingService hashingService) {
        this.hashingService = hashingService;
    }


    public String vnpayQueryString(VnpayDTO dto)
    throws UnsupportedEncodingException {
        Map params = new HashMap<>();

        //Add params
        params.put("vnp_Amount", dto.getVnp_Amount());
        params.put("vnp_Command", dto.getVnp_Command());
        params.put("vnp_CreateDate", dto.getVnp_CreateDate());
        params.put("vnp_CurrCode", dto.getVnp_CurrCode());
        params.put("vnp_IpAddr", dto.getVnp_IpAddr());
        params.put("vnp_Locale", dto.getVnp_Locale());
        params.put("vnp_OrderInfo", dto.getVnp_OrderInfo());
        params.put("vnp_OrderType", dto.getVnp_OrderType());
        params.put("vnp_ReturnUrl", dto.getVnp_ReturnUrl());
        params.put("vnp_TmnCode", dto.getVnp_TmnCode());
        params.put("vnp_TxnRef", dto.getVnp_TxnRef());
        params.put("vnp_Version", dto.getVnp_Version());

        //Build query string
        List fieldNames = new ArrayList(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = hashingService.hashing(queryUrl, dto.getVnp_HashSecret());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return dto.getVnpUrl() + "?" + queryUrl;
    }
}
