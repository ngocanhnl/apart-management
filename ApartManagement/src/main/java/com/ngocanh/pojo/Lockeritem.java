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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ngoc Anh
 */
@Entity
@Table(name = "lockeritem")
@NamedQueries({
    @NamedQuery(name = "Lockeritem.findAll", query = "SELECT l FROM Lockeritem l"),
    @NamedQuery(name = "Lockeritem.findByItemId", query = "SELECT l FROM Lockeritem l WHERE l.itemId = :itemId"),
    @NamedQuery(name = "Lockeritem.findByItemName", query = "SELECT l FROM Lockeritem l WHERE l.itemName = :itemName"),
    @NamedQuery(name = "Lockeritem.findByStatus", query = "SELECT l FROM Lockeritem l WHERE l.status = :status"),
    @NamedQuery(name = "Lockeritem.findByImage", query = "SELECT l FROM Lockeritem l WHERE l.image = :image"),
    @NamedQuery(name = "Lockeritem.findByReceivedAt", query = "SELECT l FROM Lockeritem l WHERE l.receivedAt = :receivedAt"),
    @NamedQuery(name = "Lockeritem.findByCreatedAt", query = "SELECT l FROM Lockeritem l WHERE l.createdAt = :createdAt")})
public class Lockeritem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "item_id")
    private Integer itemId;
    @Basic(optional = false)
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "status")
    private String status;
    @Column(name = "image")
    private String image;
    @Column(name = "received_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receivedAt;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "locker_id", referencedColumnName = "locker_id")
    @ManyToOne(optional = false)
    private Locker lockerId;
 @Transient
    private MultipartFile file;
    public Lockeritem() {
    }

    public Lockeritem(Integer itemId) {
        this.itemId = itemId;
    }

    public Lockeritem(Integer itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Locker getLockerId() {
        return lockerId;
    }

    public void setLockerId(Locker lockerId) {
        this.lockerId = lockerId;
    }
    @PrePersist
    protected void onCreate() {
         this.createdAt = new Date();
        if (this.status == null) {
            this.status = "waiting";
        }
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lockeritem)) {
            return false;
        }
        Lockeritem other = (Lockeritem) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ngocanh.pojo.Lockeritem[ itemId=" + itemId + " ]";
    }

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
}
