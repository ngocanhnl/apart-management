/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.formatter;

import com.ngocanh.pojo.Locker;
import com.ngocanh.pojo.User;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ADMIN
 */
public class UserFormatter implements Formatter<User>{

    @Override
    public String print(User x, Locale locale) {
        return String.valueOf(x.getUsername());
    }

    @Override
    public User parse(String x, Locale locale) throws ParseException {
         User c =new User(Integer.valueOf(x));
        return c;
    }
    
}
