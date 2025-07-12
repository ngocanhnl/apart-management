/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ngoc Anh
 */
@Entity
@Table(name = "complaint")
@NamedQueries({
    @NamedQuery(name = "Complaint.findAll", query = "SELECT c FROM Complaint c"),
    @NamedQuery(name = "Complaint.findByComplaintId", query = "SELECT c FROM Complaint c WHERE c.complaintId = :complaintId"),
    @NamedQuery(name = "Complaint.findByTitle", query = "SELECT c FROM Complaint c WHERE c.title = :title"),
    @NamedQuery(name = "Complaint.findByStatus", query = "SELECT c FROM Complaint c WHERE c.status = :status"),
    @NamedQuery(name = "Complaint.findBySubmittedAt", query = "SELECT c FROM Complaint c WHERE c.submittedAt = :submittedAt"),
    @NamedQuery(name = "Complaint.findByResolvedAt", query = "SELECT c FROM Complaint c WHERE c.resolvedAt = :resolvedAt")})
public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "complaint_id")
    private Integer complaintId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private String status;
    @Column(name = "submitted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedAt;
    @Column(name = "resolved_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resolvedAt;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;

    public Complaint() {
    }

    public Complaint(Integer complaintId) {
        this.complaintId = complaintId;
    }

    public Complaint(Integer complaintId, String title, String description) {
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
    }

    public Integer getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Date submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Date getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(Date resolvedAt) {
        this.resolvedAt = resolvedAt;
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
        hash += (complaintId != null ? complaintId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complaint)) {
            return false;
        }
        Complaint other = (Complaint) object;
        if ((this.complaintId == null && other.complaintId != null) || (this.complaintId != null && !this.complaintId.equals(other.complaintId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ngocanh.pojo.Complaint[ complaintId=" + complaintId + " ]";
    }
    
}
