/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.User;
import com.ngocanh.services.QuestionService;
import com.ngocanh.services.SurveyService;
import com.ngocanh.services.UserService;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class ApiSurveyController {
    @Autowired
    private SurveyService surveySer;
    
    @Autowired
    private UserService userDetailService;
    
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/secure/survey")
    public ResponseEntity<?> surveyList(@RequestParam Map<String,String> params, Principal p) {
        
        User u = this.userDetailService.getUserByUsername(p.getName());
        
        
        return new ResponseEntity<>(this.surveySer.getAllSurveyForUser(params,u), HttpStatus.OK);
        
    }
    
    @GetMapping("/secure/survey/{id}")
    public ResponseEntity<?> questionList(@RequestParam Map<String,String> params, @PathVariable("id") int id) {
        
        return new ResponseEntity<>(this.questionService.getQuestionBySurveyId(id), HttpStatus.OK);
        
    }
}
