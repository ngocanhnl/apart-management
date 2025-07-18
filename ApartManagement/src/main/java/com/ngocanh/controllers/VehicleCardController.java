/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.controllers;

import com.ngocanh.pojo.User;
import com.ngocanh.pojo.Vehiclecardregistration;
import com.ngocanh.services.UserService;
import com.ngocanh.services.VehicleCardService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Ngoc Anh
 */
@Controller
public class VehicleCardController {

    
    @Autowired
    private VehicleCardService vehicleCardService;

    @Autowired
    private UserService userService;

    @GetMapping("/vehicle-cards")
    public String listVehicleCards(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("vehicleCards", this.vehicleCardService.getAllVehiclecardregistration(params));

        return "vehicleCard";
    }

    @GetMapping("/vehicle-cards/new")
    public String create(Model model) {

        model.addAttribute("vehicleCard", new Vehiclecardregistration());
        model.addAttribute("users", this.userService.getUsers());
        return "vehicleCardForm";
    }

    @PostMapping("/vehicle-cards")
    public String createCard(@ModelAttribute(value = "vehicleCard") Vehiclecardregistration vehicleCard) {
        
        this.vehicleCardService.updateOrCreateCard(vehicleCard);
        return "redirect:/vehicle-cards";
    }
    @GetMapping("/vehicle-cards/{cardId}")
    public String updateProduct(Model model,@PathVariable(value = "cardId") int id){
        model.addAttribute("vehicleCard", this.vehicleCardService.getCardById(id));
        model.addAttribute("users", this.userService.getUsers());
        return "vehicleCardForm";
    }
    

}
