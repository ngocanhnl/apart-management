/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.repositories.AnswerRepository;
import com.ngocanh.repositories.QuestionRepository;
import com.ngocanh.repositories.UserRepositoriy;
import com.ngocanh.services.AnswerService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepo;
    @Autowired
    private UserRepositoriy userRepo;
    
    @Autowired
    private QuestionRepository questRepo;
    

    @Override
    public void addAnswer(Map<String, String> params, String username) {
        System.out.println("DEBUG answer: 2");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println("DEBUG answer: 3" );
            String questionId = entry.getKey();
            String point =  entry.getValue();
            
            int id = Integer.parseInt(questionId); 
            

            this.answerRepo.addAnswer(point,this.userRepo.getUserByUsername(username), questRepo.getQuestionById(id));
        }
    }

}
