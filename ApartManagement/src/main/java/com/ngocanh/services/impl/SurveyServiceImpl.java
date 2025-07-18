/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.pojo.Survey;
import com.ngocanh.repositories.SurveyRepository;
import com.ngocanh.services.SurveyService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class SurveyServiceImpl implements SurveyService{
    @Autowired
    private SurveyRepository surveyRepo;
    
    @Override
    public List<Survey> getAllSurvey(Map<String, String> params) {
        return this.surveyRepo.getAllSurvey(params);
    }

    @Override
    public void updateOrCreateSurvey(Survey suvey) {
         this.surveyRepo.updateOrCreateSurvey(suvey);
    }

    @Override
    public Survey getSurveyById(int id) {
        return this.surveyRepo.getSurveyById(id);
    }
    
}
