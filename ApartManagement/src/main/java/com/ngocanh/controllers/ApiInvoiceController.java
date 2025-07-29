/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.User;
import com.ngocanh.services.InvoiceService;
import com.ngocanh.services.PaymentService;
import com.ngocanh.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ngoc Anh
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiInvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private PaymentService paymentService;
    
    
    @GetMapping("/secure/invoices")
    public ResponseEntity<?> invoiceList(Principal principal) {
        
        return new ResponseEntity<>(this.invoiceService.getInvoicesByUsername(principal.getName()), HttpStatus.OK);
        
    }
    
    @PostMapping(path="/secure/updateBill", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateBill(@RequestParam Map<String, String> params, @RequestParam(value = "image") MultipartFile image) {
        
        this.paymentService.updateOrCreatePayment(params, image);
        return new ResponseEntity<>(HttpStatus.OK);
        
    }
}
