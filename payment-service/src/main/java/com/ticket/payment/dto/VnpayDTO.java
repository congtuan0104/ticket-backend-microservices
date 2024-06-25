package com.ticket.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//vnp_Amount=1806000&vnp_Command=pay&vnp_CreateDate=20210801153333&vnp_CurrCode=VND&vnp_IpAddr=127.0.0.1&vnp_Locale=vn&vnp_OrderInfo=Thanh+toan+don+hang+%3A5&vnp_OrderType=other&vnp_ReturnUrl=https%3A%2F%2Fdomainmerchant.vn%2FReturnUrl&vnp_TmnCode=DEMOV210&vnp_TxnRef=5&vnp_Version=2.1.0&vnp_SecureHash=3e0d61a0c0534b2e36680b3f7277743e8784cc4e1d68fa7d276e79c23be7d6318d338b477910a27992f5057bb1582bd44bd82ae8009ffaf6d141219218625c42
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VnpayDTO {
    private String  vnp_Amount;
    private String vnp_Command;
    private String vnp_CreateDate;
    private String vnp_CurrCode;
    private String vnp_IpAddr;
    private String vnp_Locale;
    private String vnp_OrderInfo;
    private String vnp_OrderType;
    private String vnp_ReturnUrl;
    private String vnp_TmnCode;
    private String vnp_TxnRef;
    private String vnp_Version;
    private String vnp_HashSecret;
    private String vnpUrl;
}
