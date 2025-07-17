/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author Ngoc Anh
 */
@Entity
@Table(name = "locker")
@NamedQueries({
    @NamedQuery(name = "Locker.findAll", query = "SELECT l FROM Locker l"),
    @NamedQuery(name = "Locker.findByLockerId", query = "SELECT l FROM Locker l WHERE l.lockerId = :lockerId"),
    @NamedQuery(name = "Locker.findByLocation", query = "SELECT l FROM Locker l WHERE l.location = :location")})
public class Locker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "locker_id")
    private Integer lockerId;
    @Column(name = "location")
    private String location;
    @Lob
    @Column(name = "description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lockerId")
    private Set<Lockeritem> lockeritemSet;
    @OneToMany(mappedBy = "lockerId")
    private Set<User> userSet;

    public Locker() {
    }

    public Locker(Integer lockerId) {
        this.lockerId = lockerId;
    }

    public Integer getLockerId() {
        return lockerId;
    }

    public void setLockerId(Integer lockerId) {
        this.lockerId = lockerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Lockeritem> getLockeritemSet() {
        return lockeritemSet;
    }

    public void setLockeritemSet(Set<Lockeritem> lockeritemSet) {
        this.lockeritemSet = lockeritemSet;
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lockerId != null ? lockerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locker)) {
            return false;
        }
        Locker other = (Locker) object;
        if ((this.lockerId == null && other.lockerId != null) || (this.lockerId != null && !this.lockerId.equals(other.lockerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ngocanh.pojo.Locker[ lockerId=" + lockerId + " ]";
    }
    
}
