/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.User;
import com.ngocanh.pojo.Vehiclecardregistration;
import com.ngocanh.repositories.VehicleCardRepository;
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

/**
 *
 * @author Ngoc Anh
 */
@Repository
@Transactional
public class VehicleCardRepositoryImpl implements VehicleCardRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private int PAGE_SIZE = 10;

    @Override
    public List<Vehiclecardregistration> getAllVehiclecardregistration(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Vehiclecardregistration> q = b.createQuery(Vehiclecardregistration.class);
        Root root = q.from(Vehiclecardregistration.class);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("relativeName"), String.format("%%%s%%", kw)));
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
    public void updateOrCreateVehicleCard(Vehiclecardregistration card) {
        Session s = this.factory.getObject().getCurrentSession();
        if (card.getRegistrationId()== null) {
            s.persist(card);
        } else {
            s.merge(card);
        }
    }

    @Override
    public Vehiclecardregistration getCardById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Vehiclecardregistration.class, id);
    }

    @Override
    public List<Vehiclecardregistration> getVehiclecardByUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
         CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Vehiclecardregistration> q = b.createQuery(Vehiclecardregistration.class);
        Root root = q.from(Vehiclecardregistration.class);
        
        q.where(b.equal(root.get("userId").get("userId"), u.getUserId()));
        Query query = s.createQuery(q);  
        return query.getResultList();
    }

}
