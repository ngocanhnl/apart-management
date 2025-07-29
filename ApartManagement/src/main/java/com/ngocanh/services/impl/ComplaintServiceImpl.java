/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.pojo.Complaint;
import com.ngocanh.repositories.ComplainRepository;
import com.ngocanh.services.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ComplaintServiceImpl  implements ComplaintService{
        @Autowired
       private ComplainRepository complaintRepo; 
    @Override
    public void addOrUpdateItem(Complaint p) {
        this.complaintRepo.addOrUpdateItem(p);
    }
    
}
