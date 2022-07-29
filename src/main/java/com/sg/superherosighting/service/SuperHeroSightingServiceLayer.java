/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.HeroImageDaoException;
import com.sg.superherosighting.dao.LocationImageDaoException;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Organization;
import com.sg.superherosighting.entity.Sighting;
import com.sg.superherosighting.entity.SuperPower;
import java.io.InputStream;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rmans
 */
public interface SuperHeroSightingServiceLayer {
    public Hero getHeroById (int heroId);
    public Location getLocationById (int locationId);
    public Organization getOrganizationById(int organizationId);
    public Sighting getSightingById(int sightingId);
    public SuperPower getSuperPowerById(int SuperPowerId);
    public List<Hero> getAllHeroes();
    public List<Location> getAllLocations();
    public List<Organization> getAllOrganizations();
    public List<Sighting> getAllSightings();
    public List<Sighting> getAllSightingsOrderedByDate();
    public List<SuperPower> getAllSuperPowers();
    public Hero addHero(Hero hero, MultipartFile heroImage) throws HeroImageDaoException;
    public Location addLocation(Location location);
    public Organization addOrganization(Organization organization);
    public Sighting addSighting(Sighting sighting);
    public SuperPower addSuperPower(SuperPower superPower);
    public void updateHero(Hero hero, MultipartFile heroImage) throws HeroImageDaoException;
    public void updateLocation(Location location) throws LocationImageDaoException;
    public void updateOrganization(Organization organization);
    public void updateSighting(Sighting sighting);
    public void updateSuperPower(SuperPower superPower);
    public void deleteHero(int heroId);
    public void deleteLocation(int locationId);
    public void deleteOrganization(int organizationId);
    public void deleteSighting(int sightingId);
    public void deleteSuperPower(int superPowerId);
    public InputStream getHeroImage(int heroId);
    public InputStream getLocationImage(int locationId);
    public void saveHeroImage(MultipartFile imageFile, int heroId) throws HeroImageDaoException;
    public void saveLocationImage(Location location) throws LocationImageDaoException;
    public void deleteHeroImage(int heroId) throws HeroImageDaoException;
    public void deleteLocationImage(Location location) throws LocationImageDaoException;
    
}
