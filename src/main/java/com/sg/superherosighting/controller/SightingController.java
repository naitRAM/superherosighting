package com.sg.superherosighting.controller;

import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Sighting;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayer;
import java.util.ArrayList;
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
 * data: Jul. 14, 2022
 * purpose: 
 */
@Controller
public class SightingController {

    @Autowired
    SuperHeroSightingServiceLayer service;
    
    @GetMapping("sightings")
    public String displaySightings (Model model) {
        List<Sighting> sightings = service.getAllSightings();
        List<Hero> heroes = service.getAllHeroes();
        List<Location> locations = service.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "sightings";
    }
    
    @PostMapping("addSighting")
    public String addSighting (Sighting sighting, Integer locationId, String[] heroIds, String sightingDate) {
        
        service.addSighting(sighting, locationId, heroIds, sightingDate);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = service.getSightingById(id);
        List<Hero> heroes = service.getAllHeroes();
        List<Location> locations = service.getAllLocations();
        for (Hero hero : heroes) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
            hero.setSuperPowers(new ArrayList<>());
        }
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }
            
    @PostMapping("editSighting")
    public String updateSighting (Sighting sighting, Integer locationId, String[] heroIds, String sightingDate) {
        
        service.updateSighting(sighting, locationId, heroIds, sightingDate);
        return "redirect:/sightings";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting (Integer id) {
        service.deleteSighting(id);
        return "redirect:/sightings";
    }
    
    @GetMapping("sightingDetail")
    public String displaySighting (Model model, Integer id) {
        Sighting sighting = service.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }
    
   
}
