/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.services.impl;

import com.ngocanh.pojo.Vehiclecardregistration;
import com.ngocanh.repositories.VehicleCardRepository;
import com.ngocanh.services.VehicleCardService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ngoc Anh
 */
@Service
public class VehicleCardServiceImpl implements VehicleCardService{
    @Autowired
    private VehicleCardRepository vehicleRepo; 

    @Override
    public List<Vehiclecardregistration> getAllVehiclecardregistration(Map<String, String> params) {
       return this.vehicleRepo.getAllVehiclecardregistration(params);
    }

    @Override
    public void updateOrCreateCard(Vehiclecardregistration card) {
        this.vehicleRepo.updateOrCreateVehicleCard(card);
    }

    @Override
    public Vehiclecardregistration getCardById(int id) {
       return this.vehicleRepo.getCardById(id);
    }
    
}
