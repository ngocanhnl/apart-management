/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.repositories;

import com.ngocanh.pojo.Complaint;
import java.util.List;
import java.util.Map;
/**
 *
 * @author ADMIN
 */
public interface ComplainRepository {
     void addOrUpdateItem(Map<String, String> params);
      public List<Object[]> getAllCamplaints(Map<String, String> params);
      public Complaint findById(int id);
      void updateStatus(int complaintId, String status);
       void deleteComplaint(int complaintId);
}
