/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Questions;
import com.ngocanh.pojo.Vehiclecardregistration;
import com.ngocanh.repositories.QuestionRepository;
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
public class QuestionRepositoryImpl implements QuestionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    private int PAGE_SIZE = 10;

    @Override
    public List<Questions> getAllQuestion(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Questions> q = b.createQuery(Questions.class);
        Root root = q.from(Questions.class);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("content"), String.format("%%%s%%", kw)));
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
    public void updateOrCreateQuestion(Questions question) {
        Session s = this.factory.getObject().getCurrentSession();
        if (question.getId() == null) {
            s.persist(question);
        } else {
            s.merge(question);
        }

    }

    @Override
    public List<Questions> getQuestionBySurveyId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Questions> q = b.createQuery(Questions.class);
        Root root = q.from(Questions.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(root.get("surveyId").get("id"), id));

        q.where(predicates.toArray(Predicate[]::new));

        Query query = s.createQuery(q);

        return query.getResultList();
    }

    @Override
    public Questions getQuestionByContent(int id, String content) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Questions> q = b.createQuery(Questions.class);
        Root root = q.from(Questions.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(b.equal(root.get("surveyId").get("id"), id));
        predicates.add(b.equal(root.get("content"), content));
        q.where(predicates.toArray(Predicate[]::new));

        Query query = s.createQuery(q);

        List<Questions> results = query.getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public void deleteQuestion(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Questions question = s.get(Questions.class, id);
        if (question != null) {
            s.remove(question);
        }
    }

}
