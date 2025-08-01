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
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiLockerController {
@Autowired
    private LockerService lockerService; 
@Autowired
    private UserService userService;
@Autowired
private ItemService itemService;

    @GetMapping("/secure/MyLocker")
    public ResponseEntity<?> getLockerItems(Principal principal) {
       String username = principal.getName(); // Lấy username từ token

    User user = this.userService.getUserByUsername(username);
    if (user == null)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

    Locker locker = lockerService.getLocker(Integer.parseInt(user.getLockerId().toString()));
   
    if (locker == null)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Locker not found");
    List<Lockeritem> items = lockerService.getLockeritem(locker.getLockerId());
    return ResponseEntity.ok( items);
    }

   

}
