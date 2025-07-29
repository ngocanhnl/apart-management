/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.cloudinary.utils.ObjectUtils;
import com.ngocanh.pojo.Payment;
import com.ngocanh.pojo.User;
import com.ngocanh.repositories.PaymentRepository;
import com.ngocanh.services.PaymentService;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.Cloudinary;
import com.ngocanh.repositories.Invoicerepository;
import java.util.Date;
/**
 *
 * @author Ngoc Anh
 */
@Service
public class PaymentServiceImpl implements PaymentService{
    
    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private Cloudinary cloudinary;
    
    @Autowired
    private Invoicerepository invoiceRepo;

    @Override
    public void updateOrCreatePayment(Map<String, String> params, MultipartFile image) {
        
        Payment p = this.paymentRepo.findPaymentByInvoiceId(Integer.parseInt(params.get("invoiceId")));
      
        if(p == null){
            p = new Payment();
            p.setInvoiceId(this.invoiceRepo.getInvoiceById(Integer.parseInt(params.get("invoiceId"))));
            p.setMethod(params.get("method"));
            p.setCreatedAt(new Date());
        }
   
        if (image != null && !image.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                p.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                
            }
        }
        
        this.paymentRepo.updateOrCreatePayMent(p);
        
    }
    
}
