/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;
import com.ngocanh.pojo.Complaint;
import com.ngocanh.pojo.Lockeritem;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author ADMIN
 */
public interface ComplaintService {
    void addOrUpdateItem(Map<String, String> params);
     public List<Object[]> getAllComplaints(Map<String, String> params);
     public Complaint findById(int id);
     void updateStatus(int complaintId, String status);
     void deleteComplaint(int id);
}
