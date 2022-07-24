package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.HeroImageDao;
import com.sg.superherosighting.dao.HeroImageDaoException;
import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.LocationImageDao;
import com.sg.superherosighting.dao.LocationImageDaoException;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Organization;
import com.sg.superherosighting.entity.Sighting;
import com.sg.superherosighting.entity.SuperPower;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 22, 2022 purpose:
 */
@Service
public class SuperHeroSightingServiceLayerImpl implements SuperHeroSightingServiceLayer {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    HeroImageDao heroImageDao;

    @Autowired
    LocationImageDao locationImageDao;

    @Override
    public Hero getHeroById(int heroId) {
        return heroDao.getHeroById(heroId);
    }

    @Override
    public Location getLocationById(int locationId) {
        return locationDao.getLocationById(locationId);
    }

    @Override
    public Organization getOrganizationById(int organizationId) {
        return organizationDao.getOrganizationById(organizationId);
    }

    @Override
    public Sighting getSightingById(int sightingId) {
        return sightingDao.getSightingById(sightingId);
    }

    @Override
    public SuperPower getSuperPowerById(int superPowerId) {
        return superPowerDao.getSuperPowerById(superPowerId);
    }

    @Override
    public List<Hero> getAllHeroes() {
        return heroDao.getAllHeroes();
    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return superPowerDao.getAllSuperPowers();
    }

    

    @Override
    public Hero addHero(Hero hero, MultipartFile file) throws HeroImageDaoException {
        
        hero = heroDao.addHero(hero);
        saveHeroImage(file, hero.getHeroId());
        return hero;
    }

    @Override
    public Location addLocation(Location location) {
        location = locationDao.addLocation(location);
        try {
            saveLocationImage(location);
        } catch (LocationImageDaoException ex) {
        }
        return location;
    }

    private Organization buildOrganizationLists(Organization organization,
            String[] heroIds) {
        List<Hero> organizationMembers = new ArrayList<>();
        if (heroIds != null) {
            for (String memberId : heroIds) {
                organizationMembers.add(heroDao.getHeroById(Integer.parseInt(memberId)));
            }
        }
        organization.setMembers(organizationMembers);
        return organization;
    }

    @Override
    public Organization addOrganization(Organization organization, String[] heroIds) {
        organization = buildOrganizationLists(organization, heroIds);
        return organizationDao.addOrganization(organization);
    }

    private Sighting buildSightingFields(Sighting sighting, int locationId, String[] heroIds, String sightingDate) {
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
        return sighting;
    }

    @Override
    public Sighting addSighting(Sighting sighting, int locationId, String[] heroIds, String sightingDate) {
        sighting = buildSightingFields(sighting, locationId, heroIds, sightingDate);
        return sightingDao.addSighting(sighting);
    }

    @Override
    public SuperPower addSuperPower(SuperPower superPower) {
        return superPowerDao.addSuperPower(superPower);
    }

    @Override
    public void updateHero(Hero hero, MultipartFile file) throws
            HeroImageDaoException {
        
        heroDao.updateHero(hero);
        saveHeroImage(file, hero.getHeroId());

    }

    @Override
    public void updateLocation(Location location) throws LocationImageDaoException {
        Location currentLocation
                = locationDao.getLocationById(location.getLocationId());
        boolean equalCoordinates = currentLocation.getLatitude()
                == location.getLatitude()
                && currentLocation.getLongitude() == location.getLongitude();
        if (!equalCoordinates) {
            saveLocationImage(location);
        }
        locationDao.updateLocation(location);
    }

    @Override
    public void updateOrganization(Organization organization, String[] heroIds) {

        organization = buildOrganizationLists(organization, heroIds);
        organizationDao.updateOrganization(organization);
    }

    @Override
    public void updateSighting(Sighting sighting, int locationId, String[] heroIds, String sightingDate) {
        sighting = buildSightingFields(sighting, locationId, heroIds, sightingDate);
        sightingDao.updateSighting(sighting);
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {
        superPowerDao.updateSuperPower(superPower);
    }

    @Override
    public void deleteHero(int heroId) {
        Hero toDelete = heroDao.getHeroById(heroId);
        heroDao.deleteHero(toDelete);
        if (heroImageDao.getFilePath(heroId) != null) {
            try {
                heroImageDao.deleteHeroImage(heroId);
            } catch (HeroImageDaoException ex) {
            }
        }
    }

    @Override
    public void deleteLocation(int locationId) {
        Location toDelete = locationDao.getLocationById(locationId);
        locationDao.deleteLocation(toDelete);
        try {
            locationImageDao.deleteLocationImage(toDelete);
        } catch (LocationImageDaoException ex) {
        }
    }

    @Override
    public void deleteOrganization(int organizationId) {
        Organization toDelete
                = organizationDao.getOrganizationById(organizationId);
        organizationDao.deleteOrganization(toDelete);
    }

    @Override
    public void deleteSighting(int sightingId) {
        Sighting toDelete = sightingDao.getSightingById(sightingId);
        sightingDao.deleteSighting(toDelete);
    }

    @Override
    public void deleteSuperPower(int superPowerId) {
        SuperPower toDelete = superPowerDao.getSuperPowerById(superPowerId);
        superPowerDao.deleteSuperPower(toDelete);
    }

    @Override
    public InputStream getHeroImage(int heroId) {
        return heroImageDao.getHeroImage(heroId);
    }

    @Override
    public InputStream getLocationImage(int locationId) {
        Location location = locationDao.getLocationById(locationId);
        try {
            return locationImageDao.getLocationImage(location);
        } catch (LocationImageDaoException ex) {
            return null;
        }
    }

    @Override
    public void saveHeroImage(MultipartFile imageFile, int heroId) throws
            HeroImageDaoException {

        if (!imageFile.isEmpty()) {
            heroImageDao.saveHeroImage(imageFile, heroId);
        }
    }

    @Override
    public void saveLocationImage(Location location) throws
            LocationImageDaoException {

        locationImageDao.saveLocationImage(location);
    }

    @Override
    public void deleteHeroImage(int heroId) throws
            HeroImageDaoException {

        heroImageDao.deleteHeroImage(heroId);
    }

    @Override
    public void deleteLocationImage(Location location) throws
            LocationImageDaoException {

        locationImageDao.deleteLocationImage(location);
    }

}
