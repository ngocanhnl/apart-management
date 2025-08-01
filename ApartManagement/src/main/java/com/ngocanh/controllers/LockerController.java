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
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
                           @RequestParam("lockerId") Integer lockerId){
            
            
             this.itemService.updateStatus(itemId,status );
            
            return "redirect:/locker/" + lockerId;
        }
            @GetMapping("/locker/")
    public String getLockers(Model model) {
        List<User> users = this.userService.getUsers();

        model.addAttribute("users", users);

       
        
      
        return "listLocker";
        
    }
        @GetMapping("/locker/search")
    public String getLockerByName(Model model, @RequestParam Map<String, String> params) {
        List<User> users = this.userService.getUsers(params);
        model.addAttribute("users", users);
        return "listLocker";
    }
    
}
