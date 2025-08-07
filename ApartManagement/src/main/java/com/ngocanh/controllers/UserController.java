/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.User;
import com.ngocanh.services.UserService;
import jakarta.validation.Valid;
import java.util.Map;
import org.hibernate.annotations.CreationTimestamp;
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
 * @author Ngoc Anh
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/users")
//    public String createUser(@ModelAttribute(value="user") User u){
//        this.userService.updateOrCreateuser(u);
//        return "redirect:/users";
//    }
    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") @Valid User u,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            System.out.println("Loi");
            result.getAllErrors().forEach(err -> System.out.println(err.toString()));
            return "userCreateForm";
        }
        System.out.println(">> Raw password từ form: [" + u.getPassword() + "]");
        this.userService.updateOrCreateuser(u);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUser(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("users", this.userService.getUsers(params));

        return "user";
    }

    @GetMapping("/user/{userId}")
    public String updateUser(Model model, @PathVariable(value = "userId") int id) {
        model.addAttribute("user", this.userService.getUserById(id));

        return "userCreateForm";
    }

    @GetMapping("/userCreateForm")
    public String createuser(Model model) {
        model.addAttribute("user", new User());

        return "userCreateForm";
    }

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @GetMapping("/user/block/{userId}")
    public String blockUser(Model model, @PathVariable(value = "userId") int id) {
        User u = this.userService.getUserById(id);
        this.userService.blockUser(u);
        return "redirect:/users";
    }
    
    @GetMapping("/user/delete/{userId}")
    public String deleteUser(Model model, @PathVariable(value = "userId") int id) {
       
        this.userService.deleteUser(id);
        return "redirect:/users";
    }

}
