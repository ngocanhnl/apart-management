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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User createUser(String username, String password, String role, String fullName) {
        Session s = this.factory.getObject().getCurrentSession();

        User a = new User(username, password, role, fullName);
        s.persist(a);
        return a;
    }

    @Override
    public List<User> getUsers(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root root = q.from(User.class);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("fullName"), String.format("%%%s%%", kw)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = s.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null) {
                int p = Integer.parseInt(page);
                int start = (p - 1) * PAGE_SIZE;
                query.setFirstResult(start);
                query.setMaxResults(PAGE_SIZE);
            }
        }
        return query.getResultList();
    }

    @Override
    public void updateOrCreateUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        if (user.getUserId() == null) {
            s.persist(user);
        } else {
            s.merge(user);
        }
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public List<User> getUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<User> q = b.createQuery(User.class);
        Root root = q.from(User.class);
        Query query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findByUsername", User.class);
        q.setParameter("username", username);

        return (User) q.getSingleResult();
    }

    @Override
    public boolean authenticate(String username, String password) {
        User u = this.getUserByUsername(username);

        return this.passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    public User addUser(User u) {
         Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);
        
        return u;
    }
}
