package com.sg.superherosighting.controller;

import com.sg.superherosighting.entity.Sighting;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Jul. 28, 2022
 * purpose: 
 */

@Controller
public class IndexController {

    @Autowired
    SuperHeroSightingServiceLayer service;
    
    @GetMapping("/")
    public String getIndexPage(Model model) {
        
        List<Sighting> recentSightings = service.getAllSightingsOrderedByDate();
        int size = recentSightings.size() >= 10 ? 10 : recentSightings.size();
        recentSightings = recentSightings.subList(0, size);
        model.addAttribute("sightings", recentSightings);
        return "index";
    }
}
