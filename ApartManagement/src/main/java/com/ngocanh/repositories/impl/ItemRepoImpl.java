/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.repositories.ItemRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
public class ItemRepoImpl implements ItemRepo {

    private int PAGE_SIZE = 10;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdateItem(Lockeritem item) {
        Session s = this.factory.getObject().getCurrentSession();
        if (item.getItemId() == null) {
            s.persist(item);
        } else {
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

    @Override
    public List<Lockeritem> getLockeritem(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Lockeritem> q = b.createQuery(Lockeritem.class);
        Root<Lockeritem> root = q.from(Lockeritem.class);

        List<Predicate> predicates = new ArrayList<>();
        String lockerId = params.get("lockerId");
        if (lockerId != null && !lockerId.isEmpty()) {
            predicates.add(b.equal(root.get("lockerId").get("lockerId"), Integer.parseInt(lockerId)));
        }
        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(b.lower(root.get("itemName")), "%" + kw.toLowerCase() + "%"));
            }
        }

        q.where(predicates.toArray(new Predicate[0]));
        q.orderBy(b.desc(root.get("createdAt"))); // Sắp xếp mới nhất trước (nếu muốn)

        Query<Lockeritem> query = s.createQuery(q);

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

}
