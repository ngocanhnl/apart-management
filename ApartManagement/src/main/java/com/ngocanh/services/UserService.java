/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import com.ngocanh.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ngoc Anh
 */
public interface UserService {
    public User createUser(String username, String password, String role, String fullName);
    public List<User> getUsers();
    public List<User> getUsersByName(String name);
}
