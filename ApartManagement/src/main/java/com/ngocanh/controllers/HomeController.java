/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.services.LockerService;
import com.ngocanh.services.StatService;
import com.ngocanh.services.SurveyService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author admin
 */
@Controller
@ControllerAdvice
public class HomeController {

    @Autowired
    private LockerService lockerService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private StatService statService;

    @RequestMapping("/")
    @Transactional
    public String index(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("lockers", this.lockerService.getLockers());
        model.addAttribute("surveys", this.surveyService.getAllSurvey(params));
     

        int selectedId = params.get("surveyId") == null ? 1 : Integer.parseInt(params.get("surveyId"));
        model.addAttribute("selectedSurveyId", selectedId);


        model.addAttribute("stats", this.statService.statSurvey(selectedId));

        return "index";
    }
}
