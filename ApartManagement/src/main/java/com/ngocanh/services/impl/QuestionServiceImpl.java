/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.pojo.Questions;
import com.ngocanh.repositories.QuestionRepository;
import com.ngocanh.services.QuestionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class QuestionServiceImpl implements QuestionService{
@Autowired
private QuestionRepository questionRepo;
    @Override
    public List<Questions> getQuestionBySurveyId(int id) {
        return this.questionRepo.getQuestionBySurveyId(id);
    }

    @Override
    public void deleteQuestion(int id) {
        this.questionRepo.deleteQuestion(id);
    }
    
}
