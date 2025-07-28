/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Payment;
import com.ngocanh.pojo.User;
import com.ngocanh.repositories.PaymentRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
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
public class PaymentRepositoryImpl implements PaymentRepository{

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void updateOrCreatePayMent(Payment p) {
        Session s = this.factory.getObject().getCurrentSession();
        if (p.getId()== null) {
            s.persist(p);
        } else {
            s.merge(p);
        }
    }

    @Override
    public Payment findPaymentByInvoiceId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
         
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Payment> q = b.createQuery(Payment.class);
        Root root = q.from(Payment.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(root.get("invoiceId").get("id"), id));
        q.where(predicates.toArray(Predicate[]::new));
        Query query = s.createQuery(q);

        
       List<Payment> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
}
