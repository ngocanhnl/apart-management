/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Complaint;
import com.ngocanh.repositories.ComplainRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
public class ComplaintRepoImpl implements ComplainRepository{
    @Autowired
private LocalSessionFactoryBean factory;
    @Override
    public void addOrUpdateItem(Complaint p) {
           Session s = this.factory.getObject().getCurrentSession();
        if(p.getComplaintId()== null){
            s.persist(p);
        }
        else{
            s.merge(p);
        }
    }
    
}
