/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import com.ngocanh.pojo.Invoice;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ngoc Anh
 */
public interface InvoiceService {
    public List<Object[]> getAllInvoice(Map<String, String> params);
     public void updateOrCreateInvoice(Invoice invoice);
}
