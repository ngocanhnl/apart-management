/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Invoice;
import com.ngocanh.pojo.Payment;

import com.ngocanh.pojo.User;

import com.ngocanh.repositories.Invoicerepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
public class InvoiceRepositoryImpl implements Invoicerepository {

    private int PAGE_SIZE = 10;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> getAllInvoice(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root<Invoice> iRoot = q.from(Invoice.class);
//        Root<Payment> pRoot = q.from(Payment.class);
        Root<User> uRoot = q.from(User.class);

//        q.where(b.and(b.equal(uRoot.get("userId"), iRoot.get("userId").get("userId")), b.equal(iRoot.get("id"), pRoot.get("invoiceId").get("id"))));
        q.where(b.equal(uRoot.get("userId"), iRoot.get("userId").get("userId")));
        q.multiselect(
                iRoot.get("id"),
                uRoot.get("fullName"),
                uRoot.get("username"),
                iRoot.get("name"),
                iRoot.get("total"),
                iRoot.get("isPaid")
        );
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(uRoot.get("userId"), iRoot.get("userId").get("userId")));

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(uRoot.get("fullName"), String.format("%%%s%%", kw)));
            }
            String status = params.get("status");
            if (status != null && !status.isEmpty()) {
                String tmp;
                if(status.equals("1")){
                    tmp = "Chưa thanh toán";
                }
                else if(status.equals("2")){
                    tmp = "Chờ xác minh";
                }
                else{
                    tmp = "Đã thanh toán";
                }
                predicates.add(b.equal(iRoot.get("isPaid"), tmp));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
//        Query query = s.createQuery(q);
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
    public void updateOrCreateInvoice(Invoice invoice) {
        Session s = this.factory.getObject().getCurrentSession();
        if (invoice.getId() == null) {
            s.persist(invoice);
        } else {
            s.merge(invoice);

        }
    }

    @Override
    public List<Object[]> getInvoicesByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root<Invoice> iRoot = q.from(Invoice.class);

        Root<User> uRoot = q.from(User.class);

        q.multiselect(
                iRoot.get("id"),
                iRoot.get("name"),
                iRoot.get("total"),
                iRoot.get("isPaid")
        );
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(uRoot.get("userId"), iRoot.get("userId").get("userId")));

        predicates.add(b.equal(uRoot.get("username"), username));

        q.where(predicates.toArray(Predicate[]::new));
        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public Invoice getInvoiceById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Invoice.class, id);
    }

    @Override
    public void isPaidInvoice(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Invoice i = getInvoiceById(id);
        if (i != null) {
            i.setIsPaid("Đã thanh toán");
                 
        }
       
    }

    @Override
    public void deleteInvocie(User u) {
        Session s = this.factory.getObject().getCurrentSession();
         
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaDelete<Invoice> q = b.createCriteriaDelete(Invoice.class);
        Root root = q.from(Invoice.class);
        q.where(b.equal(root.get("userId").get("userId"), u.getUserId()));
        Query query = s.createQuery(q);
        query.executeUpdate();
    }
}
