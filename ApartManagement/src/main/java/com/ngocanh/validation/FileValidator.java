/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.ngocanh.pojo.Lockeritem;

@Component
public class FileValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Lockeritem.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Lockeritem item = (Lockeritem) target;

        if (item.getFile() == null || item.getFile().isEmpty()) {
            errors.rejectValue("file", "lockeritem.file.required", "Vui lòng chọn ảnh để upload");
        }
    }
}
