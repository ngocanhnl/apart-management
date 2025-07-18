/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.Survey;
import com.ngocanh.pojo.Vehiclecardregistration;
import com.ngocanh.services.QuestionService;
import com.ngocanh.services.SurveyService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ngoc Anh
 */
@Controller
public class SurveyController {
    @Autowired
    private SurveyService surveyService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("/surveys")
    public String create(Model model, @ModelAttribute(value = "survey") Survey survey) {
        this.surveyService.updateOrCreateSurvey(survey);
        return "redirect:/surveys";
    }

    @GetMapping("/surveys/create")
    public String createSurveyForm(Model model) {
        model.addAttribute("survey", new Survey());
        return "survey";
    }

    @GetMapping("/surveys")
    public String listSurveys(Model model, @RequestParam Map<String, String> params) {
        
        model.addAttribute("surveys", this.surveyService.getAllSurvey(params));
        return "surveyList";
    }
    @GetMapping("/surveys/{surveyId}")
    public String SurveyDetail(Model model, @PathVariable(value = "surveyId") int id) {
        
        model.addAttribute("survey", this.surveyService.getSurveyById(id));
        model.addAttribute("questions", this.questionService.getQuestionBySurveyId(id));
        return "survey";
    }
}
