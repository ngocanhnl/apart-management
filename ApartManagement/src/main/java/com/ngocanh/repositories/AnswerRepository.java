/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.repositories;

import com.ngocanh.pojo.Questions;
import com.ngocanh.pojo.User;

/**
 *
 * @author Ngoc Anh
 */
public interface AnswerRepository {
    public void addAnswer(String text, User userId, Questions questionId);
}
