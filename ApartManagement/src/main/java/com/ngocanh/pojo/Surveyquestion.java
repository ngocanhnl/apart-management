/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.pojo;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "surveyquestion")
@NamedQueries({
    @NamedQuery(name = "Surveyquestion.findAll", query = "SELECT s FROM Surveyquestion s"),
    @NamedQuery(name = "Surveyquestion.findBySurveyquestionId", query = "SELECT s FROM Surveyquestion s WHERE s.surveyquestionId = :surveyquestionId")})
public class Surveyquestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "surveyquestion_id")
    private Integer surveyquestionId;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne(optional = false)
    private Question questionId;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne(optional = false)
    private Survey surveyId;

    public Surveyquestion() {
    }

    public Surveyquestion(Integer surveyquestionId) {
        this.surveyquestionId = surveyquestionId;
    }

    public Integer getSurveyquestionId() {
        return surveyquestionId;
    }

    public void setSurveyquestionId(Integer surveyquestionId) {
        this.surveyquestionId = surveyquestionId;
    }

    public Question getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Question questionId) {
        this.questionId = questionId;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyquestionId != null ? surveyquestionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Surveyquestion)) {
            return false;
        }
        Surveyquestion other = (Surveyquestion) object;
        if ((this.surveyquestionId == null && other.surveyquestionId != null) || (this.surveyquestionId != null && !this.surveyquestionId.equals(other.surveyquestionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ngocanh.pojo.Surveyquestion[ surveyquestionId=" + surveyquestionId + " ]";
    }
    
}
