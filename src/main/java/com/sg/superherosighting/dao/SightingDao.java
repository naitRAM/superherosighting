/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author rmans
 */
public interface SightingDao {
    public Sighting getSightingById(int id);
    public List<Sighting> getAllSightings();
    public List<Sighting> getSightingsByDate(LocalDate date);
    public Sighting addSighting (Sighting sighting);
    public void updateSighting (Sighting sighting);
    public void deleteSighting (Sighting sighting);
}
