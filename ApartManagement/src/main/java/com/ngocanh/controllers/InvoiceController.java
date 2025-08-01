/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.Invoice;
import com.ngocanh.pojo.User;
import com.ngocanh.services.InvoiceService;
import com.ngocanh.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import com.google.gson.JsonObject;

/**
 *
 * @author Ngoc Anh
 */
@Controller
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private UserService userService;

    @GetMapping("/invoices")
    public String invoiceList(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("invoices", this.invoiceService.getAllInvoice(params));
        model.addAttribute("currentPage", Integer.valueOf(params.getOrDefault("page","1")));
        System.out.println(params.getOrDefault("page","1"));
        int total = 0;
        if(this.invoiceService.getAllInvoice(params) != null){
            total = this.invoiceService.getAllInvoice(params).size();
            System.out.println(total);
        }
        model.addAttribute("totalPages", (int) Math.ceil((double) total / 10));
        System.out.println((int) Math.ceil((double) total / 10));
        return "invoiceList";
    }

    @GetMapping("/invoices/add")
    public String addInvoice(Model model, @RequestParam Map<String, String> params) {
//        model.addAttribute("invoice", new Invoice());
        model.addAttribute("users", this.userService.getUsers(params));
        return "InvoiceListUser";
    }

    @GetMapping("/invoice/add/{userId}")
    public String addInvoiceUser(Model model, @RequestParam Map<String, String> params, @PathVariable(value = "userId") int userId) {
        model.addAttribute("invoice", new Invoice());
        model.addAttribute("userId", userId);
        return "invoiceForm";
    }

    @PostMapping("/invoice/add/{userId}")
    public String addInvoiceSubmit(@PathVariable("userId") Integer userId,
            @ModelAttribute("invoice") Invoice invoice) {
      
        User user = userService.getUserById(userId);
        invoice.setUserId(user);
        invoice.setCreatedAt(new Date());
        this.invoiceService.updateOrCreateInvoice(invoice);
//        invoiceRepository.save(invoice);
        return "redirect:/invoices";
    }

}
