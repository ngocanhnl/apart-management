/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Invoices;
import com.ngocanh.pojo.User;
import com.ngocanh.pojo.UserInvoice;
import com.ngocanh.repositories.Invoicerepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
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

//    Root<UserInvoice> uiRoot = q.from(UserInvoice.class);
//        Join<UserInvoice, Invoices> invoiceJoin = uiRoot.join("invoiceId"); // JOIN UserInvoice
//        Join<UserInvoice, User> userJoin = uiRoot.join("userId");
        Root<Invoices> iRoot = q.from(Invoices.class);
        Root<UserInvoice> uiRoot = q.from(UserInvoice.class);
        Root<User> uRoot = q.from(User.class);

        q.where(b.and(b.equal(uRoot.get("userId"), uiRoot.get("userId").get("userId")), b.equal(iRoot.get("id"), uiRoot.get("invoiceId").get("id"))));

        q.multiselect(
                iRoot.get("id"),
                uRoot.get("fullName"),
                uRoot.get("username"),
                iRoot.get("type"),
                iRoot.get("amount"),
                iRoot.get("dueDate"),
                iRoot.get("paidDate"),
                iRoot.get("status"),
                iRoot.get("createdAt")
        );
        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(uRoot.get("userId"), uiRoot.get("userId").get("userId")));
            predicates.add(b.equal(iRoot.get("id"), uiRoot.get("invoiceId").get("id")));

            String kw = params.get("name");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(uRoot.get("fullName"), String.format("%%%s%%", kw)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
//        Query query = s.createQuery(q);
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
    public void updateOrCreateInvoice(Invoices invoice) {
        Session s = this.factory.getObject().getCurrentSession();
        if (invoice.getId() == null) {
            s.persist(invoice);
            UserInvoice ui = new UserInvoice();
            ui.setInvoiceId(invoice);
            ui.setUserId(new User(invoice.getUserId()));
            s.persist(ui);
        } else {
            s.merge(invoice);

        }
    }

}
