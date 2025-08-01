/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.repositories.ItemRepo;
import jakarta.transaction.Transactional;
import java.util.Date;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
public class ItemRepoImpl implements ItemRepo{
    @Autowired
    private LocalSessionFactoryBean factory;
    @Override
    public void addOrUpdateItem(Lockeritem item) {
        Session s = this.factory.getObject().getCurrentSession();
        if(item.getItemId() == null){
            s.persist(item);
        }
        else{
            s.merge(item);
        }

    }

    @Override
    public Lockeritem findItemById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Lockeritem.class, id);
    }

    @Override
    public void updateStatus(Lockeritem item, String status) {
        Session s = this.factory.getObject().getCurrentSession();
        item.setStatus(status);
        item.setReceivedAt(new Date());
        s.merge(item);
    }

    @Override
    public void deleteItem(int id) {
          Session s = this.factory.getObject().getCurrentSession();
        Lockeritem p = this.findItemById(id);
        s.remove(p);
    }
    
}
