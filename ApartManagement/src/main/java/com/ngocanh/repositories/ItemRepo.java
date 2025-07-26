/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.repositories;

import com.ngocanh.pojo.Lockeritem;

/**
 *
 * @author ADMIN
 */
public interface ItemRepo {
    void addOrUpdateItem(Lockeritem item);
    Lockeritem findItemById(int id);
    public void updateStatus(Lockeritem item, String status) ;
    void deleteItem(int id);
}
