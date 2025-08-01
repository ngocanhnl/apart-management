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
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepositoriy userRepo;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User createUser(String username, String password, String role, String fullName) {
        return this.createUser(username, password, role, fullName);
    }

    @Override
    public List<User> getUsers(Map<String, String> params) {
        return this.userRepo.getUsers(params);
    }

    @Override
    public void updateOrCreateuser(User user) {

        if (user.getFile() != null && !user.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(user.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                user.setAvatarUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.userRepo.updateOrCreateUser(user);
    }

    @Override
    public User getUserById(int id) {
        return this.userRepo.getUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return this.userRepo.getUsers();
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userRepo.getUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = this.getUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(u.getRole()));

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), authorities);
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.userRepo.authenticate(username, password);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User u = new User();
        u.setFullName(params.get("fullName"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
        u.setUsername(params.get("username"));
        u.setPassword(this.passwordEncoder.encode(params.get("password")));
        u.setRole("ROLE_USER");
        
        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatarUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                
            }
        }
        
        return this.userRepo.addUser(u);
    }

    @Override
    public void changePassword(String username, String password, MultipartFile avatar) {
        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                
                this.userRepo.changePassword(username, password, res.get("secure_url").toString());
                return;
            } catch (IOException ex) {
                
            }
        }
         this.userRepo.changePassword(username, password, "");
    }

    @Override
    public User updateUser(Map<String, String> params, MultipartFile avatar) {
        
        
        User u = this.userRepo.getUserByUsername(params.get("username"));
        u.setFullName(params.get("fullName"));
        u.setEmail(params.get("email"));
        u.setPhone(params.get("phone"));
   
        
        if (!avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(avatar.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatarUrl(res.get("secure_url").toString());
            } catch (IOException ex) {
                
            }
        }
        
        this.userRepo.updateOrCreateUser(u);
        return u;
        
    }

    @Override
    public void blockUser(User u) {
        u.setIsActive(Boolean.FALSE);
        this.updateOrCreateuser(u);
    }

}
