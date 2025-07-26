/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.repositories;


import com.ngocanh.pojo.User;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Ngoc Anh
 */
public interface UserRepositoriy {
    public User createUser(String username, String password, String role, String fullName);
    public List<User> getUsers(Map<String, String> params);
    public List<User> getUsers();
    public void updateOrCreateUser(User user);
    public User getUserById(int id);
    public User getUserByUsername(String username);
    boolean authenticate(String username, String password);
    public User addUser(User u);
}
