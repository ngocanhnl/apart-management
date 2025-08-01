/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.pojo.Invoice;
import com.ngocanh.repositories.Invoicerepository;
import com.ngocanh.services.InvoiceService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

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

    @Override
    public List<Map<String, Object>> getInvoicesByUsername(String username) {
        List<Object[]> obj = this.invoiceRepo.getInvoicesByUsername(username);
        List<Map<String, Object>> result = obj.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id",row[0]);
            map.put("name", row[1]);
            map.put("total", row[2]);
            map.put("status", row[3]);
            return map;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public void isPaidInvoice(int id) {
        this.invoiceRepo.isPaidInvoice(id);
    }

}
