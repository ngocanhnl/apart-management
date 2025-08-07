/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.repositories.impl;

import com.ngocanh.pojo.Answers;
import com.ngocanh.pojo.Questions;
import com.ngocanh.pojo.Survey;
import com.ngocanh.pojo.User;
import com.ngocanh.pojo.Vehiclecardregistration;
import com.ngocanh.repositories.QuestionRepository;
import com.ngocanh.repositories.SurveyRepository;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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
public class SurveyRepositoryImpl implements SurveyRepository {

    private int PAGE_SIZE = 10;
    @Autowired
    private LocalSessionFactoryBean factory;

    @Autowired
    private QuestionRepository questionRepo;

    @Override
    public List<Survey> getAllSurvey(Map<String, String> params) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Survey> q = b.createQuery(Survey.class);
        Root root = q.from(Survey.class);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("title"), String.format("%%%s%%", kw)));
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
    public void updateOrCreateSurvey(Survey suvey) {
        Session s = this.factory.getObject().getCurrentSession();
        if (suvey.getId() == null) {

            s.persist(suvey);
            if (suvey.getQuestions() != null) {

                for (String content : suvey.getQuestions()) {
                    Questions q = new Questions();
                    q.setContent(content);
                    q.setSurveyId(suvey);
                    s.persist(q);
                }
            }

        } else {
            s.merge(suvey);
            if (suvey.getQuestions() == null || suvey.getQuestions().isEmpty()) {
                List<Questions> res = this.questionRepo.getQuestionBySurveyId(suvey.getId());
                for (Questions qu : res) {
                    this.questionRepo.deleteQuestion(qu.getId());
                }
            } else {
                List<Questions> oldQuestions = this.questionRepo.getQuestionBySurveyId(suvey.getId());

                // Xoá các câu hỏi không còn tồn tại
                for (Questions oldQ : oldQuestions) {
                    if (!suvey.getQuestions().contains(oldQ.getContent())) {
                        this.questionRepo.deleteQuestion(oldQ.getId());
                    }
                }
                for (String content : suvey.getQuestions()) {

                    if (this.questionRepo.getQuestionByContent(suvey.getId(), content) == null) {
                        Questions q = new Questions();
                        q.setContent(content);
                        q.setSurveyId(suvey);
                        s.persist(q);
                    }

                }
            }

        }
    }

    @Override
    public Survey getSurveyById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Survey.class, id);
    }

    @Override
    public List<Survey> getAllSurveyForUser(Map<String, String> params, User u) {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        
        CriteriaQuery<Survey> q = b.createQuery(Survey.class);
        Root root = q.from(Survey.class); // Survey


        Subquery<Integer> sub = q.subquery(Integer.class);
        Root aRoot = sub.from(Answers.class);
        Root qRoot = sub.from(Questions.class); // Questions

        sub.select(qRoot.get("surveyId").get("id"))
                .where(
                        b.equal(aRoot.get("questionId").get("id"), qRoot.get("id")),
                        b.equal(aRoot.get("userId").get("userId"), u.getUserId())
                )
                .distinct(true);


        q.select(root)
                .where(b.not(root.get("id").in(sub)))
                .distinct(true);


        return s.createQuery(q).getResultList();
    }

}
