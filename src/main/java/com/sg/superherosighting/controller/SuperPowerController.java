package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entity.SuperPower;
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
    SuperPowerDao superPowerDao;
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("superpowers")
    public String displaySuperPowers(Model model) {
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        model.addAttribute("superPowers", superPowers);
        return "superpowers";
        
    }
    
    @PostMapping("addSuperPower")
    public String addSuperPower(SuperPower superPower) {
        superPowerDao.addSuperPower(superPower);
        return "redirect:/superpowers";
    }
    
    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(SuperPower superPower){
        superPowerDao.deleteSuperPower(superPower);
        return "redirect:/superpowers";
    }
    
    @GetMapping("editSuperPower")
    public String editSuperPower(Integer superPowerId, Model model){
        SuperPower superPower = superPowerDao.getSuperPowerById(superPowerId);
        model.addAttribute("superPower", superPower);
        return "editSuperPower";
    }
    
    @PostMapping("editSuperPower")
    public String updateSuperPower(SuperPower superPower){
        superPowerDao.updateSuperPower(superPower);
        return "redirect:/superpowers";
    }
}
