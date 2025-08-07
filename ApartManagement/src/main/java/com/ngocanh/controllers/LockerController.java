/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.Locker;
import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.pojo.User;
import com.ngocanh.services.ItemService;
import com.ngocanh.services.LockerService;
import com.ngocanh.services.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author ADMIN
 */
@Controller
public class LockerController {

    @Autowired
    private LockerService lockerService;
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @GetMapping("/locker/{id}")
    public String getUser(Model model, @PathVariable("id") int id) {
        Locker locker = this.lockerService.getLocker(id);

        model.addAttribute("locker", locker);

        model.addAttribute("lockerItems", this.lockerService.getLockeritem(id));

        return "locker";
    }

    @PostMapping("/locker/updateStatus")
    public String updateStatus(@RequestParam("itemId") Integer itemId,
            @RequestParam("status") String status,
            @RequestParam("lockerId") Integer lockerId) {

        this.itemService.updateStatus(itemId, status);

        return "redirect:/locker/" + lockerId;
    }
   
    @GetMapping("/locker")
    public String getLockers(Model model, @RequestParam Map<String, String> params) {
        Map<String, String> updatedParams = new HashMap<>(params);
        int curentPage = Integer.valueOf(params.getOrDefault("page","1"));
        model.addAttribute("currentPage", curentPage);
        List<User> a = this.userService.getUsers(updatedParams);
        int start = (curentPage-1)*10;
        int end = Math.min(start+10, a.size());
        List<User> b = a.subList(start, end);
        
        model.addAttribute("invoices", b);
        
       
        model.addAttribute("totalPages", (int) Math.ceil((double) a.size() / 10));
        System.out.println("TotalPage "+(int) Math.ceil((double) a.size() / 10));


        model.addAttribute("users", b);

        return "listLocker";

    }

    @GetMapping("/locker/search")
    public String getLockerByName(Model model, @RequestParam Map<String, String> params) {
        List<User> users = this.userService.getUsers(params);
        model.addAttribute("users", users);
        return "listLocker";
    }

}
