package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Sighting;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
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
    SuperPowerDao superPowerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("sightings")
    public String displaySightings (Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "sightings";
    }
    
    @PostMapping("addSighting")
    public String addSighting (Sighting sighting, Integer locationId, String[] heroIds, String sightingDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(sightingDate, formatter);
        Location location = locationDao.getLocationById(locationId);
        List<Hero> heroes = new ArrayList<>();
        for (String heroId : heroIds) {
            heroes.add(heroDao.getHeroById(Integer.parseInt(heroId)));
        }
        sighting.setDate(date);
        sighting.setLocation(location);
        sighting.setHeroesSighted(heroes);
        sightingDao.addSighting(sighting);
        return "redirect:/sightings";
    }
    
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero hero : heroes) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
            hero.setSuperPowers(new ArrayList<>());
        }
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);
        return "editSighting";
    }
            
    @PostMapping("editSighting")
    public String updateSighting (Sighting sighting, Integer locationId, String[] heroIds, String sightingDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(sightingDate, formatter);
        Location location = locationDao.getLocationById(locationId);
        List<Hero> heroes = new ArrayList<>();
        for (String heroId : heroIds) {
            heroes.add(heroDao.getHeroById(Integer.parseInt(heroId)));
        }
        sighting.setDate(date);
        sighting.setLocation(location);
        sighting.setHeroesSighted(heroes);
        sightingDao.updateSighting(sighting);
        return "redirect:/sightings";
    }
    
    @GetMapping("deleteSighting")
    public String deleteSighting (Integer id) {
        Sighting sighting = sightingDao.getSightingById(id);
        sightingDao.deleteSighting(sighting);
        return "redirect:/sightings";
    }
    
    @GetMapping("sightingDetail")
    public String displaySighting (Model model, Integer id) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }
    
   
}
