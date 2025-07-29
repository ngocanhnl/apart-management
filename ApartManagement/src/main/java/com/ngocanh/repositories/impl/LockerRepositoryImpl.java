/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Locker;
import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.repositories.LockerRepository;
import jakarta.persistence.Query;
import java.util.List;
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
public class LockerRepositoryImpl implements LockerRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Locker> getLockers() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Locker", Locker.class);
        return q.getResultList();
    }

    @Override
    public List<Lockeritem> getLockeritem(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        Query q = s.createQuery("FROM Lockeritem WHERE lockerId.id = :lockerId",Lockeritem.class);
        q.setParameter("lockerId", id);

        return q.getResultList(); 
    }

    @Override
    public Locker getLockers(int id) {
         Session s = this.factory.getObject().getCurrentSession();
            return s.get(Locker.class, id);
    }

}
