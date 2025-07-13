/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.services.UserService;
import java.util.Map;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ngoc Anh
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/users")
    public String createUser(Model model){
        return "user";
    }
    
    @GetMapping("/users")
    public String getUser(Model model, @RequestParam Map<String, String> params){
        model.addAttribute("users", this.userService.getUsers(params));
        
        return "user";
    }
    
    
}
