/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ngoc Anh
 */
@Entity
@Table(name = "vehiclecardregistration")
@NamedQueries({
    @NamedQuery(name = "Vehiclecardregistration.findAll", query = "SELECT v FROM Vehiclecardregistration v"),
    @NamedQuery(name = "Vehiclecardregistration.findByRegistrationId", query = "SELECT v FROM Vehiclecardregistration v WHERE v.registrationId = :registrationId"),
    @NamedQuery(name = "Vehiclecardregistration.findByRelativeName", query = "SELECT v FROM Vehiclecardregistration v WHERE v.relativeName = :relativeName"),
    @NamedQuery(name = "Vehiclecardregistration.findByRelativePhone", query = "SELECT v FROM Vehiclecardregistration v WHERE v.relativePhone = :relativePhone"),
    @NamedQuery(name = "Vehiclecardregistration.findByRegisteredAt", query = "SELECT v FROM Vehiclecardregistration v WHERE v.registeredAt = :registeredAt"),
    @NamedQuery(name = "Vehiclecardregistration.findByStatus", query = "SELECT v FROM Vehiclecardregistration v WHERE v.status = :status")})
public class Vehiclecardregistration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "registration_id")
    private Integer registrationId;
    @Basic(optional = false)
    @Column(name = "relative_name")
    private String relativeName;
    @Column(name = "relative_phone")
    private String relativePhone;
    @Column(name = "registered_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt;
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private User userId;

    public Vehiclecardregistration() {
    }

    public Vehiclecardregistration(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public Vehiclecardregistration(Integer registrationId, String relativeName) {
        this.registrationId = registrationId;
        this.relativeName = relativeName;
    }

    public Integer getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    public String getRelativeName() {
        return relativeName;
    }

    public void setRelativeName(String relativeName) {
        this.relativeName = relativeName;
    }

    public String getRelativePhone() {
        return relativePhone;
    }

    public void setRelativePhone(String relativePhone) {
        this.relativePhone = relativePhone;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        hash += (registrationId != null ? registrationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiclecardregistration)) {
            return false;
        }
        Vehiclecardregistration other = (Vehiclecardregistration) object;
        if ((this.registrationId == null && other.registrationId != null) || (this.registrationId != null && !this.registrationId.equals(other.registrationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ngocanh.pojo.Vehiclecardregistration[ registrationId=" + registrationId + " ]";
    }
    
}
