/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.formatters;

import com.ngocanh.pojo.User;
import com.ngocanh.services.UserService;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ngoc Anh
 */
//public class UserFormatter implements Formatter<User> {
//
//
//    @Override
//    public User parse(String text, Locale locale) throws ParseException {
//        User u = new User(Integer.valueOf(text));
//        return u;
//    }
//
//    @Override
//    public String print(User object, Locale locale) {
//        return String.valueOf(object.getUserId());
//    }
//
//}

public class UserFormatter implements Formatter<User> {



    @Override
    public User parse(String text, Locale locale) throws ParseException {
        User u =  new User(Integer.valueOf(text));
        return u;
    }

    @Override
    public String print(User object, Locale locale) {
        return String.valueOf(object.getUserId());
    }
}
