/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.User;
import com.ngocanh.repositories.UserRepositoriy;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.TypedQuery;

/**
 *
 * @author Ngoc Anh
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepositoriy {

    private int PAGE_SIZE = 10;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public User createUser(String username, String password, String role, String fullName) {
//        Session s = this.factory.getObject().getCurrentSession();
//
//        User a = new User(username, password, role, fullName);
//        s.persist(a);
        return null;
    }

    @Override
    public List<User> getUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM User where role='resident' ", User.class);
        return q.getResultList();
    }

    @Override
    public List<User> getUsersByName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();

        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);

        if (name != null && !name.isEmpty()) {
            Predicate namePredicate = cb.like(root.get("username"), "%" + name + "%");
            cq.where(namePredicate);
        }

      
        Query query = s.createQuery(cq); 

       
        List<User> users = new ArrayList<>();
        List<Object> resultList = query.getResultList(); 
        for (Object o : resultList) {
            if (o instanceof User) { 
                users.add((User) o); 
            }
        }
        return users;

    }

}
