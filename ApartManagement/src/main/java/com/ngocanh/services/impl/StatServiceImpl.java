/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.repositories.StatRepository;
import com.ngocanh.services.StatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class StatServiceImpl implements StatService{
    @Autowired
    private StatRepository statRepo;
    @Override
    public List<Object[]> statSurvey(int id) {
        return this.statRepo.statsSurvey(id);
    }
    
}
