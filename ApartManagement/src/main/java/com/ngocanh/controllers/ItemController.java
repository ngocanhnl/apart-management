/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.Locker;
import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.services.ItemService;
import com.ngocanh.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ItemController {
    @Autowired
    private ItemService itemService;
    
    @Autowired
     private UserService userService;
    @GetMapping("/Item")
    public String getUser(Model model) {
       model.addAttribute("Item", new Lockeritem());
       model.addAttribute("Users",this.userService.getUsers() );
        return "ItemLocker";
    }
   @PostMapping("/Item")
    public String addItem(@ModelAttribute(value="Item") Lockeritem item ){
        this.itemService.addOrUpdateItem(item);
        return "redirect:/Item";
    }
    @GetMapping("/Item/delete")
    public String deleteItem(@RequestParam("itemId") Integer itemId,
                           @RequestParam("lockerId") Integer lockerId){
       this.itemService.deleteItem(itemId);
        return "redirect:/locker/" + lockerId;
    }
}
