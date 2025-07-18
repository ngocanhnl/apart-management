/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import com.ngocanh.pojo.Questions;
import java.util.List;

/**
 *
 * @author Ngoc Anh
 */
public interface QuestionService {
    public List<Questions> getQuestionBySurveyId(int id);
    public void deleteQuestion(int id);
}
