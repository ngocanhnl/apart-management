/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.services.InvoiceService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ngoc Anh
 */
@Controller
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    
    @GetMapping("/invoices")
    public String invoiceList(Model model, @RequestParam Map<String,String> params){
        model.addAttribute("invoices", this.invoiceService.getAllInvoice(params));
        return "invoiceList";
    }
    
}
