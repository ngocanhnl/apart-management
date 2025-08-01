/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.User;
import com.ngocanh.services.VehicleCardService;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ngoc Anh
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiVehicleCardController {
    @Autowired
    private VehicleCardService vehicleService;
    
    @GetMapping("/secure/cards")
    public ResponseEntity<?> getCard(Principal p) {
        return new ResponseEntity<>(this.vehicleService.getVehiclecardByUser(p.getName()),HttpStatus.OK); 
    }
    @PostMapping("/secure/cards")
    public ResponseEntity<?> addCard(@RequestBody Map<String, String> body, Principal p) {
        
        return new ResponseEntity<>(this.vehicleService.addcard(body,p.getName()),HttpStatus.OK); 
    }
}
