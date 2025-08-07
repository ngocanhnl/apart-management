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
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    @Autowired
    private LockerService lockerService;

    @GetMapping("/Item")
    public String getUser(@RequestParam("lockerId") int lockerId, Model model) {
        Lockeritem item = new Lockeritem();
        
        item.setLockerId(lockerService.getLocker(lockerId));
        System.out.println("lockerIdsssssssss" + lockerService.getLocker(lockerId));
        System.out.println("itemsssssssss" + item);
        model.addAttribute("Item", item);
 

        return "ItemLocker";
    }
//   @PostMapping("/Item")
//    public String addItem(@ModelAttribute(value="Item") Lockeritem item ){
//        this.itemService.addOrUpdateItem(item);
//        return  "redirect:/locker/" + item.getLockerId().getLockerId();
//    }

    @PostMapping("/Item")
    public String addItem(@ModelAttribute("Item") @Valid Lockeritem item,
            BindingResult result,
            Model model) {
        System.out.println("Itesssssssssssssssssm"+ item);
        if (result.hasErrors()) {
             System.out.println("Itesssssssssssssssssm"+ result.hasErrors());
            return "ItemLocker";  // quay lại form nếu lỗi
        }

        this.itemService.addOrUpdateItem(item);
        return "redirect:/locker/" + item.getLockerId().getLockerId();
    }

    @GetMapping("/Item/delete")
    public String deleteItem(@RequestParam("itemId") Integer itemId,
            @RequestParam("lockerId") Integer lockerId) {
        this.itemService.deleteItem(itemId);
        return "redirect:/locker/" + lockerId;
    }

    @GetMapping("/item/search")
    public String getLockerByName(Model model, @RequestParam Map<String, String> params) {
         String lockerIdRaw = params.get("lockerId");
    if (lockerIdRaw == null || lockerIdRaw.isEmpty()) {
        return "redirect:/locker/"; // fallback nếu thiếu lockerId
    }

    int lockerId = Integer.parseInt(lockerIdRaw);

    // 2. Lấy thông tin tủ & danh sách item theo từ khóa
    Locker locker = this.lockerService.getLocker(lockerId);
    List<Lockeritem> lockerItems = this.itemService.getLockeritem(params); // lọc theo kw + lockerId

    // 3. Gửi dữ liệu về view
    model.addAttribute("locker", locker);
    model.addAttribute("lockerItems", lockerItems);

    return "locker"; // view chi tiết tủ
    }
}
