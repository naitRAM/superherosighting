package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.LocationImageDaoException;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayerImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 10, 2022 purpose:
 */
@Controller
public class LocationController {

    @Autowired
    SuperHeroSightingServiceLayerImpl service;
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = service.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(Location location) {
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
    public String updateLocation(Location location) throws LocationImageDaoException {
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
