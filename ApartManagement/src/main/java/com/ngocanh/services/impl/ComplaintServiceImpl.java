/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.pojo.Complaint;
import com.ngocanh.repositories.ComplainRepository;
import com.ngocanh.services.ComplaintService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Service
public class ComplaintServiceImpl  implements ComplaintService{
        @Autowired
       private ComplainRepository complaintRepo; 
    @Override
    public void addOrUpdateItem( Map<String, String> params) {
        this.complaintRepo.addOrUpdateItem( params);
    }

    @Override
    public List<Object[]> getAllComplaints(Map<String, String> params) {
          return this.complaintRepo.getAllCamplaints(params);
    }

    @Override
    public Complaint findById(int id) {
        return this.complaintRepo.findById(id);
    }
    
    
}
