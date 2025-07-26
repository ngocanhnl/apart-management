/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.formatter;

import com.ngocanh.pojo.Locker;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author ADMIN
 */
public class LockerFormatter implements Formatter<Locker>{

    @Override
    public String print(Locker x, Locale locale) {
        return String.valueOf(x.getLockerId());
    }

    @Override
    public Locker parse(String x, Locale locale) throws ParseException {
        Locker c =new Locker(Integer.valueOf(x));
        return c;
    }
    
    
}
