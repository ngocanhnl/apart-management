/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.pojo.User;
import com.ngocanh.repositories.UserRepositoriy;
import com.ngocanh.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepositoriy userRepo;
    @Override
    public User createUser(String username, String password, String role, String fullName) {
        return this.createUser(username, password, role, fullName);
    }

    @Override
    public List<User> getUsers() {
        return this.userRepo.getUsers();
    }

    @Override
    public List<User> getUsersByName(String name) {
            return this.userRepo.getUsersByName(name);
    }
    
}
