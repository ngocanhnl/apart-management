/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.repositories;

import com.ngocanh.pojo.Invoice;
import com.ngocanh.pojo.Payment;
import com.ngocanh.pojo.User;

/**
 *
 * @author Ngoc Anh
 */
public interface PaymentRepository {
    public void updateOrCreatePayMent(Payment p);
    public Payment findPaymentByInvoiceId(int id);
    public void updateOnlinePayment(int id, String code);
    public void deletePayment(int u);
}
