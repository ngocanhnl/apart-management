/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ngocanh.services;

import com.ngocanh.pojo.User;
import com.ngocanh.pojo.Vehiclecardregistration;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ngoc Anh
 */
public interface VehicleCardService {
    public List<Vehiclecardregistration> getAllVehiclecardregistration(Map<String,String> params);
        public void updateOrCreateCard(Vehiclecardregistration card);
        public Vehiclecardregistration getCardById(int id);
        public List<Vehiclecardregistration> getVehiclecardByUser(String username);
        public Vehiclecardregistration addcard(Map<String, String> body, String username);
}
