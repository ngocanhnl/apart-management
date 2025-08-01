/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import static com.ngocanh.controllers.VNPayConfig.hmacSHA512;
import com.ngocanh.services.InvoiceService;
import com.ngocanh.services.PaymentService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ngoc Anh
 */
@CrossOrigin()
@RequestMapping("/api")
@RestController
public class ApiVNpayController {
    
//    @Value("${vnpay.tmnCode}")
//    private String vnp_TmnCode;
//
//    @Value("${vnpay.hashSecret}")
//    private String vnp_HashSecret;
//
//    @Value("${vnpay.payUrl}")
//    private String vnp_PayUrl;
//
//    @Value("${vnpay.returnUrl}")
//    private String vnp_Returnurl;
//   
   
    
    @Autowired
    private VNPayConfig vnPayConfig;
    
    @Autowired
    private InvoiceService invoiceSer;
    
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestParam("amount") int amount,  @RequestParam("invoiceId") int invoiceId) throws Exception {
  
        String vnp_TmnCode = "VH92V83I";
        String vnp_HashSecret = "FI8DNHRRIWNQ3WB4RVMJ4ZTYKQGTLMJG";
        String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
//        String vnp_Returnurl = "https://a7ffcc9087b0.ngrok-free.app/ApartManagement/api/secure/invoices";
       String vnp_Returnurl = "http://localhost:3000/payment-result";
        // ... giữ nguyên phần xử lý tiếp theo

        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        String vnp_OrderInfo = "Thanh toan don hang: " + vnp_TxnRef;
        String orderType = "other";
        String vnp_Amount = String.valueOf(amount * 100);
        String vnp_Locale = "vn";
        String vnp_IpAddr = "127.0.0.1";
        String vnp_CreateDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", vnp_Amount);
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", vnp_Locale);
        vnp_Params.put("vnp_ReturnUrl", vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        // Hash + URL như cũ
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = vnp_Params.get(key);
            if (value != null && !value.isEmpty()) {
                hashData.append(key).append('=').append(URLEncoder.encode(value, "UTF-8"));
                query.append(key).append('=').append(URLEncoder.encode(value, "UTF-8"));
                if (i < fieldNames.size() - 1) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String vnp_SecureHash = com.ngocanh.utils.VnpayUtils.hmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = vnp_PayUrl + "?" + query.toString();

        System.out.println("Payment URL: " + paymentUrl);
        System.out.println("Payment invoiceId: " + invoiceId);
        this.invoiceSer.isPaidInvoice(invoiceId);
        this.paymentService.updateOnlinePayment(invoiceId, vnp_TxnRef);
        return ResponseEntity.ok(Collections.singletonMap("paymentUrl", paymentUrl));
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<?> handleVNPayReturn(@RequestParam Map<String, String> allParams) throws UnsupportedEncodingException, Exception {
        String vnp_SecureHash = allParams.remove("vnp_SecureHash");
        allParams.remove("vnp_SecureHashType");
        String vnp_HashSecret = "FI8DNHRRIWNQ3WB4RVMJ4ZTYKQGTLMJG";

        // Sắp xếp tham số để tạo chuỗi dữ liệu cần kiểm tra hash
        List<String> fieldNames = new ArrayList<>(allParams.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            String key = fieldNames.get(i);
            String value = allParams.get(key);
            hashData.append(key).append("=").append(URLEncoder.encode(value, "UTF-8"));
            if (i < fieldNames.size() - 1) {
                hashData.append("&");
            }
        }

        // Tạo mã hash để so sánh
        String secureHashCheck = hmacSHA512(vnp_HashSecret, hashData.toString());

        // So sánh để xác thực
        if (secureHashCheck.equals(vnp_SecureHash)) {
            String responseCode = allParams.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                // Thanh toán thành công
                return ResponseEntity.ok(Map.of(
                        "status", "success",
                        "message", "Thanh toán thành công!",
                        "data", allParams
                ));
            } else {
                // Thanh toán thất bại
                return ResponseEntity.ok(Map.of(
                        "status", "failed",
                        "message", "Thanh toán thất bại với mã: " + responseCode,
                        "data", allParams
                ));
            }
        } else {
            // Sai checksum
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", "Sai chữ ký xác thực (checksum)"
            ));
        }
    }

}
