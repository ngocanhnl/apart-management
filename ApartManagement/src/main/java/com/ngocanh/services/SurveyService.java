/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import com.ngocanh.pojo.Survey;
import com.ngocanh.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ngoc Anh
 */
public interface SurveyService {
     public List<Survey> getAllSurvey(Map<String,String> params);
    public void updateOrCreateSurvey(Survey suvey);
    public Survey getSurveyById(int id);
    public List<Survey> getAllSurveyForUser(Map<String,String> params, User u);
}
