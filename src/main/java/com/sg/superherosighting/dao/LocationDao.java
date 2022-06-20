/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.Location;
import java.util.List;

/**
 *
 * @author rmans
 */
public interface LocationDao {
    public Location getLocationById(int id);
    public List<Location> getAllLocations ();
    public Location addLocation(Location location);
    public void updateLocation(Location location);
    public void deleteLocation(Location location);
}
