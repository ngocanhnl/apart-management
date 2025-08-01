/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import com.ngocanh.pojo.Locker;
import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ngoc Anh
 */
public interface LockerService {
  
     public List<Lockeritem> getLockeritem(int id);
     public Locker getLocker(int id);
}
