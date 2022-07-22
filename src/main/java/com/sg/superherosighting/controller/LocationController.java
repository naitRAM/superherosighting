package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.LocationImageDao;
import com.sg.superherosighting.dao.LocationImageDaoException;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entity.Location;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    SuperPowerDao superPowerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    LocationImageDao locationImageDao;
    
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }
    
    @PostMapping("addLocation")
    public String addLocation(Location location) throws LocationImageDaoException {
        locationDao.addLocation(location);
        locationImageDao.saveLocationImage(location);
        return "redirect:/locations";
    }
    
    @GetMapping("editLocation")
    public String editLocation(Model model, Integer id){
        Location location = locationDao.getLocationById(id);
        
        model.addAttribute("location", location);
        return "editLocation";
    }
    
    @PostMapping("editLocation")
    public String updateLocation(Location location) throws LocationImageDaoException {
        locationDao.updateLocation(location);
        locationImageDao.saveLocationImage(location);
        return "redirect:/locations";
    }
    
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) throws LocationImageDaoException {
        Location location = locationDao.getLocationById(id);
        locationDao.deleteLocation(location);
        locationImageDao.deleteLocationImage(location);
        return "redirect:/locations";
    }
    
    @GetMapping("locationDetail")
    public String locationDetail(Integer id, Model model){
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        return "locationDetail";
    
    }
    
    @GetMapping(value = "locationImage/{locationId}")
    public void sendImage(HttpServletResponse response, @PathVariable Integer locationId) throws MalformedURLException, IOException, LocationImageDaoException{
        
        InputStream image = locationImageDao.getLocationImage(locationDao.getLocationById(locationId));
        
        StreamUtils.copy(image, response.getOutputStream());
    }
}
