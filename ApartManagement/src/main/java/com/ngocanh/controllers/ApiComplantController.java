/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.Complaint;
import com.ngocanh.pojo.User;
import com.ngocanh.services.ComplaintService;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiComplantController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/secure/complaints")
    @ResponseBody
    public ResponseEntity<?> createComplaint(@RequestParam Map<String, String> params) {
       
        complaintService.addOrUpdateItem(params);

        return ResponseEntity.ok().build();
    }

}
