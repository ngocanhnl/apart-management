/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Complaint;
import com.ngocanh.pojo.Invoice;
import com.ngocanh.pojo.Locker;
import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.pojo.User;
import com.ngocanh.repositories.ComplainRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ADMIN
 */
@Repository
@Transactional
public class ComplaintRepoImpl implements ComplainRepository {

    private int PAGE_SIZE = 10;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public void addOrUpdateItem(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
         Complaint c = new Complaint();

        if (params.containsKey("title")) {
            c.setTitle(params.get("title"));
        }
        if (params.containsKey("description")) {
            c.setDescription(params.get("description"));
        }
        if (params.containsKey("status")) {
            c.setStatus(params.get("status"));
        }
        if (params.containsKey("status") && !params.get("status").isBlank()) {
            c.setStatus(params.get("status"));
        } else {
            c.setStatus("pending"); // giá trị mặc định phù hợp ENUM
        }
        if (params.containsKey("userId")) {
            try {
                int userId = Integer.parseInt(params.get("userId"));
                User u = new User();
                u.setUserId(userId);
                c.setUserId(u);
            } catch (NumberFormatException ex) {
               
            }
        }

        c.setSubmittedAt(new Date());
        if (c.getComplaintId() == null) {
            s.persist(c);
        } else {
            s.merge(c);
        }
    }

    @Override
    public List<Object[]> getAllCamplaints(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);

        Root<Complaint> cRoot = q.from(Complaint.class);
        Join<Complaint, User> uJoin = cRoot.join("userId"); 
        List<Predicate> predicates = new ArrayList<>();

        // Lọc theo username nếu có
        if (params != null) {
            String username = params.get("username");
            if (username != null && !username.isEmpty()) {
                predicates.add(b.like(uJoin.get("username"), "%" + username + "%"));
            }

            String title = params.get("title");
            if (title != null && !title.isEmpty()) {
                predicates.add(b.like(cRoot.get("title"), "%" + title + "%"));
            }
        }

        q.where(predicates.toArray(new Predicate[0]));

        q.multiselect(
                cRoot.get("complaintId"),
                cRoot.get("title"),
                cRoot.get("status"),
                cRoot.get("submittedAt"),
                cRoot.get("resolvedAt"),
                uJoin.get("fullName"),
                uJoin.get("username")
        );

        Query query = s.createQuery(q);

        // Phân trang
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
    public Complaint findById(int id) {
         Session s = this.factory.getObject().getCurrentSession();

       return s.get(Complaint.class, id);
    }

    @Override
    public void updateStatus(int complaintId, String status) {
         Session s = this.factory.getObject().getCurrentSession();
        Complaint c = findById(complaintId);
          
        c.setStatus(status);
        c.setResolvedAt(new Date());
        s.merge(c);
    }

    @Override
    public void deleteComplaint(int complaintId) {
         Session s = this.factory.getObject().getCurrentSession();
        Complaint p = this.findById(complaintId);
        s.remove(p);
    }

}
