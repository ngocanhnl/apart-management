/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.services.ComplaintService;
import com.ngocanh.services.ItemService;
import com.ngocanh.services.UserService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
public class complaintController {

    @Autowired
    private ComplaintService complainService;

    @GetMapping("/complaints")
    public String getUser(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("complaints", this.complainService.getAllComplaints(params));

        return "complaints";
    }

    @GetMapping("/complaints/{id}")
    public String complaintDetail(Model model, @PathVariable("id") Integer id) {

        model.addAttribute("complaint", this.complainService.findById(id));
        return "complaint-detail";
    }

    @PostMapping("/complaints/updateStatus")
    public String updateStatus(@RequestParam("complaintId") int complaintId,
            @RequestParam("status") String status) {
        this.complainService.updateStatus(complaintId, status);
        return "redirect:/complaints";
    }

    @PostMapping("/complaints/delete")
    public String deleteComplaint(@RequestParam("complaintId") int complaintId) {
        this.complainService.deleteComplaint(complaintId);
        return "redirect:/complaints";
    }
}
