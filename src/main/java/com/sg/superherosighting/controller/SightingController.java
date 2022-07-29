package com.sg.superherosighting.controller;

import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Sighting;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 14, 2022 purpose:
 */
@Controller
public class SightingController {

    @Autowired
    SuperHeroSightingServiceLayer service;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    private Sighting buildSightingFields(Sighting sighting, Integer locationId, String[] heroIds, String sightingDate) {
        LocalDate date = LocalDate.now().plusDays(1);
        if (!sightingDate.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(sightingDate, formatter);
            sightingDate = date.format(formatter);
        }
        
        if (locationId != null) {
            Location location = service.getLocationById(locationId);
            sighting.setLocation(location);
        }

        List<Hero> heroes = new ArrayList<>();
        if (heroIds != null) {
            for (String heroId : heroIds) {
                heroes.add(service.getHeroById(Integer.parseInt(heroId)));
                
            }
        }
        sighting.setHeroesSighted(heroes);
        
        sighting.setDate(date);
       
        return sighting;
    }

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = service.getAllSightings();
        List<Hero> heroes = service.getAllHeroes();
        List<Location> locations = service.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", new ArrayList<>());
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, Integer locationId, String[] heroIds, String sightingDate, Model model) {

        buildSightingFields(sighting, locationId, heroIds, sightingDate);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        if (!violations.isEmpty()) {
            model.addAttribute("sighting", sighting);
            model.addAttribute("date", sightingDate);
            model.addAttribute("sightings", service.getAllSightings());
            model.addAttribute("heroes", service.getAllHeroes());
            model.addAttribute("locations", service.getAllLocations());
            model.addAttribute("errors", violations);
            return "sightings";
        }
        service.addSighting(sighting);
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
        model.addAttribute("errors", violations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String updateSighting(Sighting sighting, Integer locationId, String[] heroIds, String sightingDate, Model model) {
        buildSightingFields(sighting, locationId, heroIds, sightingDate);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        if (!violations.isEmpty()) {
            model.addAttribute("sighting", sighting);
            model.addAttribute("date", sightingDate);
            model.addAttribute("heroes", service.getAllHeroes());
            model.addAttribute("locations", service.getAllLocations());
            model.addAttribute("errors", violations);
            return "editsighting";
        }
        service.updateSighting(sighting);
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        service.deleteSighting(id);
        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String displaySighting(Model model, Integer id) {
        Sighting sighting = service.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

}
