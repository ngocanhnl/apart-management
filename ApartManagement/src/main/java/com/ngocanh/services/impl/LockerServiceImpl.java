package com.ngocanh.services.impl;



import com.ngocanh.pojo.Locker;
import com.ngocanh.repositories.LockerRepository;
import com.ngocanh.services.LockerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ngoc Anh
 */
@Service
public class LockerServiceImpl implements LockerService{
    @Autowired
    private LockerRepository lockerRepo;
    @Override
    public List<Locker> getLockers() {
        return this.lockerRepo.getLockers();
    }
    
}
