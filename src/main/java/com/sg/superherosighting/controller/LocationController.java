package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.LocationImageDaoException;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayerImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 10, 2022 purpose:
 */
@Controller
public class LocationController {

    @Autowired
    SuperHeroSightingServiceLayerImpl service;
    
    Set<ConstraintViolation<Location>> violations = new HashSet<>();
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", new ArrayList<>());
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(Location location, Model model) {
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("errorLocation", location);
            model.addAttribute("locations", service.getAllLocations());
            return "locations";
        }  
        service.addLocation(location);
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(Model model, Integer id){
        Location location = service.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String updateLocation(@Valid Location location, BindingResult result, Model model) throws LocationImageDaoException {
        if (result.hasErrors()) {
            model.addAttribute("location", location);
            return "editLocation";
            
        }
        service.updateLocation(location);
        return "redirect:/locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        
        service.deleteLocation(id);
        
        return "redirect:/locations";
    }
    
    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model){
        Location location = service.getLocationById(id);
        model.addAttribute("location", location);
        return "locationDetail";
    
    }
    
    @GetMapping(value = "locationImage/{locationId}")
    public void sendImage(HttpServletResponse response, @PathVariable Integer locationId) throws IOException{
        InputStream image = service.getLocationImage(locationId);
        if (image != null) {
        StreamUtils.copy(image, response.getOutputStream());
        image.close();
        }
        
    }
}
