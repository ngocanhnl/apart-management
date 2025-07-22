/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Ngoc Anh
 */
public class VNPayConfig {
    public static String vnp_TmnCode = "ABC123"; // mã TMN
    public static String vnp_HashSecret = "HASH_SECRET_KEY";
    public static String vnp_Returnurl = "http://localhost:8080/return"; // URL sau khi thanh toán
    public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public static String getRandomNumber(int length) {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public static String hmacSHA512(String key, String data) throws Exception {
        Mac hmac512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmac512.init(secretKeySpec);
        byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return DatatypeConverter.printHexBinary(bytes).toLowerCase();
    }
}
