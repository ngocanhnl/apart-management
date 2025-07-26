/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.pojo.Invoice;
import com.ngocanh.repositories.Invoicerepository;
import com.ngocanh.services.InvoiceService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{
@Autowired
private Invoicerepository invoiceRepo;
    @Override
    public List<Object[]> getAllInvoice(Map<String, String> params) {
        return this.invoiceRepo.getAllInvoice(params);
    }

    @Override
    public void updateOrCreateInvoice(Invoice invoice) {
        
        this.invoiceRepo.updateOrCreateInvoice(invoice);
    }
    
}
