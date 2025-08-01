/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Answers;
import com.ngocanh.pojo.Questions;
import com.ngocanh.repositories.StatRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
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
public class StatsRepositoryImpl implements StatRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    public List<Object[]> statsSurvey(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root qRoot = q.from(Questions.class);
        Root aRoot = q.from(Answers.class);
        
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(qRoot.get("id"), aRoot.get("questionId").get("id")));
        predicates.add(b.equal(qRoot.get("surveyId").get("id"), id));
        
        q.multiselect(
                qRoot.get("content"),
                b.avg(aRoot.get("answerNumber")).as(BigDecimal.class)
                
        );
        q.groupBy(qRoot.get("content"));
        q.where(predicates.toArray(Predicate[]::new));
        
        Query query = s.createQuery(q);
        return query.getResultList();
    }

}
