/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Answers;
import com.ngocanh.pojo.Questions;
import com.ngocanh.pojo.User;
import com.ngocanh.repositories.AnswerRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ngoc Anh
 */
@Repository
@Transactional
public class AnswerRepositoryImpl implements AnswerRepository{

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public void addAnswer(String text, User userId, Questions questionId) {
        Session s = this.factory.getObject().getCurrentSession();
        Answers a = new Answers();
        a.setAnswerNumber(Integer.valueOf(text));
        a.setQuestionId(questionId);
        a.setUserId(userId);
        System.out.println("DEBUG answer: " + a);

        s.persist(a);
    }
    
}
