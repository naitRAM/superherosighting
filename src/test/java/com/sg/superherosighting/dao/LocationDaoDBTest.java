/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Organization;
import com.sg.superherosighting.entity.Sighting;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

/**
 *
 * @author rmans
 */
@SpringBootTest
public class LocationDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    SightingDao sightingDao;

    public LocationDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDao.deleteSighting(sighting);
        }
        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero hero : heroes) {
            heroDao.deleteHero(hero);
        }
        
        List<Location> locations = locationDao.getAllLocations();
        for (Location location : locations) {
            locationDao.deleteLocation(location);
        }
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            organizationDao.deleteOrganization(organization);
        }
        
    }

    @Test
    public void testAddAndGetLocation() {
        Location testLocation = new Location();
        testLocation.setName("Hero Park");
        testLocation.setDescription("Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species");
        testLocation.setStreetNumber("1000");
        testLocation.setStreetName("Park Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13924");
        testLocation.setLatitude(30.2241918);
        testLocation.setLongitude(-70.9027214);

        testLocation = locationDao.addLocation(testLocation);

        Hero testHero = new Hero();
        testHero.setName("Barbee");
        testHero.setDescription("An evil beauty with hair made of blonde barbs used to snare her victims");
        testHero.setSuperPower("Shoots barbed wire");

        testHero = heroDao.addHero(testHero);
        List<Hero> locationHeroes = new ArrayList<>();
        locationHeroes.add(testHero);
        testLocation.setSightedHeroes(locationHeroes);

        Sighting testSighting = new Sighting();
        testSighting.setLocation(testLocation);
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(testHero);
        testSighting.setHeroesSighted(sightingHeroes);
        LocalDate date = LocalDate.now();
        testSighting.setDate(date);
        sightingDao.addSighting(testSighting);

        Location fromDao = locationDao.getLocationById(testLocation.getLocationId());

        assertEquals(fromDao, testLocation);
    }

    @Test
    public void testGetAllLocations() {
        Location testLocation = new Location();
        testLocation.setName("Hero Park");
        testLocation.setDescription("Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species");
        testLocation.setStreetNumber("1000");
        testLocation.setStreetName("Park Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13924");
        testLocation.setLatitude(30.2241918);
        testLocation.setLongitude(-70.9027214);

        Location testLocation2 = new Location();
        testLocation2.setName("Hero Docks");
        testLocation2.setDescription("Hero City ship dock where commercial sea exports and imports happen. Houses thousands of shipping cointainers");
        testLocation2.setStreetNumber("9");
        testLocation2.setStreetName("Coast Avenue");
        testLocation2.setCity("Hero City");
        testLocation2.setState("Hero State");
        testLocation2.setZipcode("13888");
        testLocation2.setLatitude(29.3158501);
        testLocation2.setLongitude(-69.8004975);

        testLocation = locationDao.addLocation(testLocation);
        testLocation2 = locationDao.addLocation(testLocation2);

        Hero testHero = new Hero();
        testHero.setName("Barbee");
        testHero.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero.setSuperPower("Shoots barbed wire");
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("Checkers");
        testHero2.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero2.setSuperPower("Apparition");
        testHero2 = heroDao.addHero(testHero2);

        Hero testHero3 = new Hero();
        testHero3.setName("Stoneman");
        testHero3.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero3.setSuperPower("Morphs into concrete");
        testHero3 = heroDao.addHero(testHero3);

        Hero testHero4 = new Hero();
        testHero4.setName("Teapot");
        testHero4.setDescription("A sumo wrestler and former Yakuza gang member, known for his tea-stained teeth");
        testHero4.setSuperPower("Immense weight and strength");
        testHero4 = heroDao.addHero(testHero4);

        List<Hero> testLocationHeroes = new ArrayList<>();
        testLocationHeroes.add(testHero);
        testLocationHeroes.add(testHero2);

        List<Hero> testLocationHeroes2 = new ArrayList<>();
        testLocationHeroes2.add(testHero2);
        testLocationHeroes2.add(testHero3);
        testLocationHeroes2.add(testHero4);

        testLocation.setSightedHeroes(testLocationHeroes);
        testLocation2.setSightedHeroes(testLocationHeroes2);

        Sighting testSighting = new Sighting();
        List<Hero> testSightingHeroes = new ArrayList<>();
        testSightingHeroes.add(testHero);
        testSightingHeroes.add(testHero2);
        testSighting.setHeroesSighted(testSightingHeroes);
        testSighting.setLocation(testLocation);
        testSighting.setDate(LocalDate.now());
        sightingDao.addSighting(testSighting);

        Sighting testSighting2 = new Sighting();
        List<Hero> testSightingHeroes2 = new ArrayList<>();
        testSightingHeroes2.add(testHero2);
        testSightingHeroes2.add(testHero3);
        testSightingHeroes2.add(testHero4);
        testSighting2.setHeroesSighted(testSightingHeroes2);
        testSighting2.setLocation(testLocation2);
        testSighting2.setDate(LocalDate.now());
        sightingDao.addSighting(testSighting2);

        List<Location> allLocations = locationDao.getAllLocations();

        assertTrue(allLocations.contains(testLocation));
        assertTrue(allLocations.contains(testLocation2));
        assertEquals(allLocations.size(), 2);

    }

    @Test
    public void testUpdateLocation() {
        Location testLocation = new Location();
        testLocation.setName("Hero Park");
        testLocation.setDescription("Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species");
        testLocation.setStreetNumber("1000");
        testLocation.setStreetName("Park Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13924");
        testLocation.setLatitude(30.2241918);
        testLocation.setLongitude(-70.9027214);

        testLocation = locationDao.addLocation(testLocation);

        Location fromDao = locationDao.getLocationById(testLocation.getLocationId());

        assertEquals(testLocation, fromDao);
        testLocation.setDescription("Central Park of Hero City. Used to be the "
                + "largest park in Hero City before 1 square mile was destroyed "
                + "in a hero war.");
        // a recent geological survey corrected the longitude coordinate of this park
        testLocation.setLongitude(-70.8967438);
        locationDao.updateLocation(testLocation);

        assertNotEquals(testLocation, fromDao);
        fromDao = locationDao.getLocationById(testLocation.getLocationId());
        assertEquals(testLocation, fromDao);

    }

    @Test
    public void testDeleteLocation() {
        Location testLocation = new Location();
        testLocation.setName("Hero Park");
        testLocation.setDescription("Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species");
        testLocation.setStreetNumber("1000");
        testLocation.setStreetName("Park Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13924");
        testLocation.setLatitude(30.2241918);
        testLocation.setLongitude(-70.9027214);

        Location testLocation2 = new Location();
        testLocation2.setName("Hero Docks");
        testLocation2.setDescription("Hero City ship dock where commercial sea exports and imports happen. Houses thousands of shipping cointainers");
        testLocation2.setStreetNumber("9");
        testLocation2.setStreetName("Coast Avenue");
        testLocation2.setCity("Hero City");
        testLocation2.setState("Hero State");
        testLocation2.setZipcode("13888");
        testLocation2.setLatitude(29.3158501);
        testLocation2.setLongitude(-69.8004975);

        testLocation = locationDao.addLocation(testLocation);
        testLocation2 = locationDao.addLocation(testLocation2);
        List<Location> locations = locationDao.getAllLocations();
        assertEquals(locations.size(), 2);
        assertTrue(locations.contains(testLocation));
        assertTrue(locations.contains(testLocation2));

        locationDao.deleteLocation(testLocation);
        locations = locationDao.getAllLocations();
        assertEquals(locations.size(), 1);

        assertTrue(locations.contains(testLocation2));
        assertTrue(!locations.contains(testLocation));
        
        try {
            locationDao.getLocationById(testLocation.getLocationId());
        } catch (EmptyResultDataAccessException ex) {
            return;
        }

    }

}
