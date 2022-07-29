package com.sg.superherosighting.controller;

import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.SuperPower;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Jul. 9, 2022
 * purpose: 
 */
@Controller
public class SuperPowerController {

    @Autowired
    SuperHeroSightingServiceLayer service;
    
    Set<ConstraintViolation<SuperPower>> violations = new HashSet<>();
    
    @GetMapping("superpowers")
    public String displaySuperPowers(Model model) {
        
        List<SuperPower> superPowers = service.getAllSuperPowers();
        model.addAttribute("superPowers", superPowers);
        model.addAttribute("errors", violations);
        return "superpowers";
        
    }
    
    @PostMapping("addSuperPower")
    public String addSuperPower(SuperPower superPower, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superPower);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("superPower", superPower);
            return "superpowers";
        }
        
        service.addSuperPower(superPower);
        return "redirect:/superpowers";
    }
    
    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(SuperPower superPower){
        service.deleteSuperPower(superPower.getSuperPowerId());
        return "redirect:/superpowers";
    }
    
    @GetMapping("editSuperPower")
    public String editSuperPower(Integer superPowerId, Model model){
        SuperPower superPower = service.getSuperPowerById(superPowerId);
        model.addAttribute("superPower", superPower);
        return "editSuperPower";
    }
    
    @PostMapping("editSuperPower")
    public String updateSuperPower(@Valid SuperPower superPower, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("superPower", superPower);
            return "editSuperPower";
        }
        service.updateSuperPower(superPower);
        return "redirect:/superpowers";
    }
}
