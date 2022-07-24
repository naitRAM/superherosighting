package com.sg.superherosighting.controller;

import com.sg.superherosighting.entity.SuperPower;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    
    @GetMapping("superpowers")
    public String displaySuperPowers(Model model) {
        List<SuperPower> superPowers = service.getAllSuperPowers();
        model.addAttribute("superPowers", superPowers);
        return "superpowers";
        
    }
    
    @PostMapping("addSuperPower")
    public String addSuperPower(SuperPower superPower) {
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
    public String updateSuperPower(SuperPower superPower){
        service.updateSuperPower(superPower);
        return "redirect:/superpowers";
    }
}
