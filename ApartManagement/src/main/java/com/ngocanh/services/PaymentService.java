/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ngoc Anh
 */
public interface PaymentService {
    public void updateOrCreatePayment(Map<String, String> params,MultipartFile image);
}
