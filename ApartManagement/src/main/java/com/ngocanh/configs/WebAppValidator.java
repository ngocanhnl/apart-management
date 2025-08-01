/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ngocanh.configs;

import com.ngocanh.pojo.Lockeritem;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;

import java.util.HashSet;
import java.util.Set;

@Component
public class WebAppValidator implements SmartValidator {

    @Autowired
    private Validator beanValidator;

    private Set<org.springframework.validation.Validator> springValidators = new HashSet<>();

    public void setSpringValidators(Set<org.springframework.validation.Validator> springValidators) {
        this.springValidators = springValidators;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Lockeritem.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Set<ConstraintViolation<Object>> violations = beanValidator.validate(target);
        for (ConstraintViolation<Object> v : violations) {
            errors.rejectValue(v.getPropertyPath().toString(), v.getMessageTemplate(), v.getMessage());
        }

        for (org.springframework.validation.Validator v : springValidators) {
            v.validate(target, errors);
        }
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        validate(target, errors);
    }
}

