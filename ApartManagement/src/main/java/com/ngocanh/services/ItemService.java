/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import com.ngocanh.pojo.Lockeritem;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ADMIN
 */

public interface ItemService {
    void addOrUpdateItem(Lockeritem p);
    Lockeritem getItemById(int id);
    void updateStatus(int itemId, String status);
    void deleteItem(int id);
     public List<Lockeritem> getLockeritem(Map<String, String> params);
}
