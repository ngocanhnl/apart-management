/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.repositories;

import com.ngocanh.pojo.Questions;
import com.ngocanh.pojo.Survey;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ngoc Anh
 */
public interface QuestionRepository {

    public List<Questions> getAllQuestion(Map<String, String> params);

    public void updateOrCreateQuestion(Questions question);

    public List<Questions> getQuestionBySurveyId(int id);

    public Questions getQuestionByContent(int id, String content);

    public void deleteQuestion(int id);
    
    public Questions getQuestionById(int id);
    
    
}
