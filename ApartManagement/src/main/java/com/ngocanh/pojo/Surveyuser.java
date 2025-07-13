/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.pojo;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author ADMIN
 */
@Entity
@Table(name = "surveyuser")
@NamedQueries({
    @NamedQuery(name = "Surveyuser.findAll", query = "SELECT s FROM Surveyuser s"),
    @NamedQuery(name = "Surveyuser.findBySurveyUserid", query = "SELECT s FROM Surveyuser s WHERE s.surveyUserid = :surveyUserid"),
    @NamedQuery(name = "Surveyuser.findByCreatedAt", query = "SELECT s FROM Surveyuser s WHERE s.createdAt = :createdAt")})
public class Surveyuser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "surveyUser_id")
    private Integer surveyUserid;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "survey_id", referencedColumnName = "survey_id")
    @ManyToOne
    private Survey surveyId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public Surveyuser() {
    }

    public Surveyuser(Integer surveyUserid) {
        this.surveyUserid = surveyUserid;
    }

    public Integer getSurveyUserid() {
        return surveyUserid;
    }

    public void setSurveyUserid(Integer surveyUserid) {
        this.surveyUserid = surveyUserid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Survey getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Survey surveyId) {
        this.surveyId = surveyId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyUserid != null ? surveyUserid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Surveyuser)) {
            return false;
        }
        Surveyuser other = (Surveyuser) object;
        if ((this.surveyUserid == null && other.surveyUserid != null) || (this.surveyUserid != null && !this.surveyUserid.equals(other.surveyUserid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ngocanh.pojo.Surveyuser[ surveyUserid=" + surveyUserid + " ]";
    }
    
}
