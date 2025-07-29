/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import com.ngocanh.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ngoc Anh
 */
public interface UserService extends UserDetailsService {

    public User createUser(String username, String password, String role, String fullName);

    public List<User> getUsers(Map<String, String> params);

    public List<User> getUsers();

    public void updateOrCreateuser(User user);

    public User getUserById(int id);

    public User getUserByUsername(String username);

    boolean authenticate(String username, String password);
    
    public User addUser(Map<String, String> params, MultipartFile avatar);
    

    public void changePassword(String username, String password);
    
    public User updateUser(Map<String, String> params, MultipartFile avatar);

}
