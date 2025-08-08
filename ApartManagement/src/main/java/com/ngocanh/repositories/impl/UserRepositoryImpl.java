/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Locker;
import com.ngocanh.pojo.User;
import com.ngocanh.repositories.UserRepositoriy;
import jakarta.persistence.NoResultException;
import org.hibernate.query.Query;
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

        User a = new User(username, password, passwordEncoder.encode(password), fullName);
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
            if (kw != null && !kw.isEmpty() && kw.equals("")==Boolean.FALSE) {
                predicates.add(b.like(root.get("fullName"), String.format("%%%s%%", kw)));
            }
            String status = params.get("status");
            Boolean tmp = "active".equals(status) ? Boolean.TRUE : Boolean.FALSE;
            if (status != null && !status.isEmpty()) {
                predicates.add(b.equal(root.get("isActive"), tmp));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
        Query query = s.createQuery(q);

//        if (params != null) {
//            String page = params.get("page");
//            if (page != null) {
//                int p = Integer.parseInt(page);
//                int start = (p - 1) * PAGE_SIZE;
//                query.setFirstResult(start);
//                query.setMaxResults(PAGE_SIZE);
//            }
//        }
        return query.getResultList();
    }

    @Override
    public void updateOrCreateUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        System.out.println("UserId: " + user.getUserId());

        if (user.getUserId() == null) {

            Locker locker = new Locker();
            s.persist(locker);

            user.setLockerId(locker);
            System.out.println("pw: " + user.getPassword());

            String rawPassword = user.getPassword();
            if (rawPassword != null) {
                rawPassword = rawPassword.replace(",", "").trim();
                user.setPassword(passwordEncoder.encode(rawPassword));
            }
            System.out.println(passwordEncoder.encode("123456"));

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
        Root<User> root = q.from(User.class);

        Predicate rolePredicate = b.equal(root.get("role"), "resident");
        q.where(rolePredicate);

        Query<User> query = s.createQuery(q);
        return query.getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            Query q = s.createNamedQuery("User.findByUsername", User.class);
            q.setParameter("username", username);
            return (User) q.getSingleResult();
        } catch (NoResultException ex) {
            return null; // hoặc throw UsernameNotFoundException
        }
    }

    @Override
    public boolean authenticate(String username, String password) {
        User u = this.getUserByUsername(username);
        System.out.println(this.passwordEncoder.matches("$2a$10$TFCHUZWzKcNfVnXMAeAOi.jX1CJxBHDpziCqI44j1/Yi0ezpZoVVC", "123456"));
        System.out.println("Plain password: " + password);
        System.out.println("Encoded from DB: " + u.getPassword());
        System.out.println("Match? " + passwordEncoder.matches(password, u.getPassword()));
        if (u.getIsActive() == Boolean.FALSE) {
            return Boolean.FALSE;
        }
        return this.passwordEncoder.matches(password, u.getPassword());
    }

    @Override
    public User addUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.persist(u);

        return u;
    }

    @Override
    public User findByLockerId(Integer lockerId) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        cq.select(root);
        cq.where(cb.equal(root.get("lockerId").get("lockerId"), lockerId));

        Query query = s.createQuery(cq);

        List<User> users = query.getResultList();
        if (!users.isEmpty()) {
            return users.get(0);
        }

        return null;
    }

    @Override
    public void changePassword(String username, String password, String avatar) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.getUserByUsername(username);
        u.setPassword(this.passwordEncoder.encode(password));
        u.setFirstLogin(Boolean.FALSE);

        if (avatar.equals("") == Boolean.FALSE) {
            u.setAvatarUrl(avatar);
        }
        if (u != null) {
            s.merge(u);
        }

    }

    @Override
    public void deleteUser(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.getUserById(id);
        if (u != null) {
            s.remove(u);
        }
    }

}
