/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ngocanh.pojo.Lockeritem;
import com.ngocanh.pojo.User;
import com.ngocanh.repositories.ItemRepo;
import com.ngocanh.repositories.UserRepositoriy;
import com.ngocanh.services.ItemService;
import com.ngocanh.utils.EmailService;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private UserRepositoriy userRepo;
@Autowired
private Cloudinary cloudinary;

    @Override
    public void addOrUpdateItem(Lockeritem p) {

        //upload
       if (!p.getFile().isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(p.getFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto"));
                p.setImage(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(ItemServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.itemRepo.addOrUpdateItem(p);
         if (p.getLockerId()!= null && p.getLockerId().getLockerId()  != null) {
        Integer lockerId = p.getLockerId().getLockerId() ;

        // Tìm user theo lockerId
            User user = userRepo.findByLockerId(lockerId); // Viết hàm này trong repository

        if (user != null && user.getEmail() != null) {
            String to = user.getEmail();
            String subject = "Có món đồ mới trong tủ của bạn";
            String content = String.format("Xin chào %s,\n\nMón đồ '%s' đã được thêm vào tủ của bạn.",
                    user.getFullName(), p.getItemName());

            EmailService.sendEmail(to, subject, content);
        }
    }
    }

    @Override
    public Lockeritem getItemById(int id) {
        return this.itemRepo.findItemById(id);
    }

    @Override
    public void updateStatus(int itemId, String status) {
        Lockeritem item = this.itemRepo.findItemById(itemId);
        this.itemRepo.updateStatus(item, status);
    }

    @Override
    public void deleteItem(int id) {
        this.itemRepo.deleteItem(id);
      }

}
