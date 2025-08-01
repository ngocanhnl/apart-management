/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.services.AnswerService;
import com.ngocanh.services.QuestionService;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ngoc Anh
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiAnswerController {
    @Autowired
    private AnswerService answerService;
    
    @PostMapping("/secure/answer")
    public ResponseEntity<?> answer(@RequestBody Map<String,String> params, Principal p) {
        
        System.out.println("DEBUG answer: q" );
        this.answerService.addAnswer(params, p.getName());
        return new ResponseEntity<>(HttpStatus.OK);
        
    }
}
