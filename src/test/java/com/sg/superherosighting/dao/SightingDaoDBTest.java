/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Organization;
import com.sg.superherosighting.entity.Sighting;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author rmans
 */
@SpringBootTest
public class SightingDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public SightingDaoDBTest() {
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
    public void testAddGetSighting() {
        
        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero.setSuperPower("Apparition");
        Hero fromDaoHero = heroDao.addHero(testHero);
        testHero.setHeroId(fromDaoHero.getHeroId());
        assertEquals(fromDaoHero, testHero);
        // testHero has empty lists for orgs and locations
       
        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2.setSuperPower("Morphs into concrete");
        Hero fromDaoHero2 = heroDao.addHero(testHero2);
        testHero2.setHeroId(fromDaoHero2.getHeroId());
        assertEquals(fromDaoHero2, testHero2);
        //testHero2 has empty lists for orgs and locations
        
        Hero testHero3 = new Hero();
        testHero3.setName("Teapot");
        testHero3.setDescription("A sumo wrestler and former Yakuza gang member, known for his tea-stained teeth");
        testHero3.setSuperPower("Immense weight and strength");
        Hero fromDaoHero3 = heroDao.addHero(testHero3);
        testHero3.setHeroId(fromDaoHero3.getHeroId());
        assertEquals(fromDaoHero3, testHero3);
        //testHero3 has empty lists for orgs and locations
        
        Organization testOrganization = new Organization();
        testOrganization.setName("The Good Guys");
        testOrganization.setDescription("A small cohort of 'talented' individuals who fight crime and help the helpless");
        testOrganization.setStreetNumber("88");
        testOrganization.setStreetName("Clover Road");
        testOrganization.setCity("Hero City");
        testOrganization.setState("Hero State");
        testOrganization.setZipcode("13888");
        testOrganization.setEmail("goodguys@good.heroes");
        testOrganization.setPhone("8881234567");
        List<Hero> organizationHeroes = new ArrayList<>();
        organizationHeroes.add(fromDaoHero);
        organizationHeroes.add(fromDaoHero2);
        testOrganization.setMembers(organizationHeroes);
        Organization fromDaoOrganization = organizationDao.addOrganization(testOrganization);
        testOrganization.setOrganizationId(fromDaoOrganization.getOrganizationId());
        // testOrganizaton has array of members containing two heroes
        
        assertEquals(fromDaoOrganization, testOrganization);
        assertTrue(fromDaoOrganization.getMembers().contains(testHero));
        assertTrue(fromDaoOrganization.getMembers().contains(testHero2));
        
        
        Organization testOrganization2 = new Organization();
        testOrganization2.setName("The Syndicate");
        testOrganization2.setDescription("A powerful and sinister organization intent on assassinating all good super people");
        testOrganization2.setStreetNumber("99A");
        testOrganization2.setStreetName("Dagger Lane");
        testOrganization2.setCity("Hero City");
        testOrganization2.setState("Hero State");
        testOrganization2.setZipcode("13666");
        testOrganization2.setEmail("syndicate@villain.org");
        testOrganization2.setPhone("6664444444");
        List<Hero> organizationHeroes2 = new ArrayList<>();
        organizationHeroes2.add(fromDaoHero3);
        testOrganization2.setMembers(organizationHeroes2);
        // testOrganization2 has third hero only as a member
        
        Organization fromDaoOrganization2 = organizationDao.addOrganization(testOrganization2);
        testOrganization2.setOrganizationId(fromDaoOrganization2.getOrganizationId());
        assertEquals(fromDaoOrganization2, testOrganization2);
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero3));
        // fromDaoOrganization2 has one member in members
        
        List<Organization> heroOrganizations = new ArrayList();
        testOrganization.getMembers().clear();
        heroOrganizations.add(testOrganization);
        testHero.setOrganizations(heroOrganizations);
        testHero2.setOrganizations(heroOrganizations);
        
        
        List<Organization> heroOrganizations2 = new ArrayList();
        testOrganization2.getMembers().clear();
        heroOrganizations2.add(testOrganization2);
        testHero3.setOrganizations(heroOrganizations2);
        // set organization lists to compare with fromDaoHeroes
        
        fromDaoHero = heroDao.getHeroById(testHero.getHeroId());
        fromDaoHero2 = heroDao.getHeroById(testHero2.getHeroId());
        fromDaoHero3 = heroDao.getHeroById(testHero3.getHeroId());
        
        assertEquals(testHero, fromDaoHero);
        assertEquals(testHero2, fromDaoHero2);
        assertEquals(testHero3, fromDaoHero3);
        
        Location testLocation = new Location();
        testLocation.setName("Hero Docks");
        testLocation.setDescription("Hero City ship dock where commercial sea exports and imports happen. Houses thousands of shipping cointainers");
        testLocation.setStreetNumber("9");
        testLocation.setStreetName("Coast Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13888");
        testLocation.setLatitude(29.3158501);
        testLocation.setLongitude(-69.8004975);
     
        Location fromDaoLocation = locationDao.addLocation(testLocation);
        testLocation.setLocationId(fromDaoLocation.getLocationId());
        
        assertEquals(fromDaoLocation, testLocation);
        
        // now we have all objects associated with sighting 
       
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().minusDays(5));
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(fromDaoHero);
        sightingHeroes.add(fromDaoHero2);
        sightingHeroes.add(fromDaoHero3);
        sighting.setLocation(fromDaoLocation);
        sighting.setHeroesSighted(sightingHeroes);
        Sighting fromDaoSighting = sightingDao.addSighting(sighting);
        sighting.setSightingId(fromDaoSighting.getSightingId());
        assertEquals(fromDaoSighting, sighting);
        
        fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());
        for (Hero hero : sightingHeroes) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        // heros obtained from sighting object do not have nested objects
        
        assertEquals(fromDaoSighting, sighting);
    }

    @Test
    public void testGetAllSightings() {
        
        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero.setSuperPower("Apparition");
        Hero fromDaoHero = heroDao.addHero(testHero);
        testHero.setHeroId(fromDaoHero.getHeroId());
        assertEquals(fromDaoHero, testHero);
        // testHero has empty lists for orgs and locations
       
        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2.setSuperPower("Morphs into concrete");
        Hero fromDaoHero2 = heroDao.addHero(testHero2);
        testHero2.setHeroId(fromDaoHero2.getHeroId());
        assertEquals(fromDaoHero2, testHero2);
        //testHero2 has empty lists for orgs and locations
        
        Hero testHero3 = new Hero();
        testHero3.setName("Teapot");
        testHero3.setDescription("A sumo wrestler and former Yakuza gang member, known for his tea-stained teeth");
        testHero3.setSuperPower("Immense weight and strength");
        Hero fromDaoHero3 = heroDao.addHero(testHero3);
        testHero3.setHeroId(fromDaoHero3.getHeroId());
        assertEquals(fromDaoHero3, testHero3);
        //testHero3 has empty lists for orgs and locations
        
        Hero testHero4 = new Hero();
        testHero4.setName("Barbee");
        testHero4.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero4.setSuperPower("Shoots barbed wire");
        Hero fromDaoHero4 = heroDao.addHero(testHero4);
       
        testHero4.setHeroId(fromDaoHero4.getHeroId());
        assertEquals(fromDaoHero4, testHero4);
        
        Organization testOrganization = new Organization();
        testOrganization.setName("The Good Guys");
        testOrganization.setDescription("A small cohort of 'talented' individuals who fight crime and help the helpless");
        testOrganization.setStreetNumber("88");
        testOrganization.setStreetName("Clover Road");
        testOrganization.setCity("Hero City");
        testOrganization.setState("Hero State");
        testOrganization.setZipcode("13888");
        testOrganization.setEmail("goodguys@good.heroes");
        testOrganization.setPhone("8881234567");
        List<Hero> organizationHeroes = new ArrayList<>();
        organizationHeroes.add(fromDaoHero);
        organizationHeroes.add(fromDaoHero2);
        testOrganization.setMembers(organizationHeroes);
        Organization fromDaoOrganization = organizationDao.addOrganization(testOrganization);
        testOrganization.setOrganizationId(fromDaoOrganization.getOrganizationId());
        // testOrganizaton has array of members containing two heroes
        
        assertEquals(fromDaoOrganization, testOrganization);
        assertTrue(fromDaoOrganization.getMembers().contains(testHero));
        assertTrue(fromDaoOrganization.getMembers().contains(testHero2));
        
        
        Organization testOrganization2 = new Organization();
        testOrganization2.setName("The Syndicate");
        testOrganization2.setDescription("A powerful and sinister organization intent on assassinating all good super people");
        testOrganization2.setStreetNumber("99A");
        testOrganization2.setStreetName("Dagger Lane");
        testOrganization2.setCity("Hero City");
        testOrganization2.setState("Hero State");
        testOrganization2.setZipcode("13666");
        testOrganization2.setEmail("syndicate@villain.org");
        testOrganization2.setPhone("6664444444");
        List<Hero> organizationHeroes2 = new ArrayList<>();
        organizationHeroes2.add(fromDaoHero3);
        organizationHeroes2.add(fromDaoHero4);
        testOrganization2.setMembers(organizationHeroes2);
        // testOrganization2 has third hero only as a member
        
        Organization fromDaoOrganization2 = organizationDao.addOrganization(testOrganization2);
        testOrganization2.setOrganizationId(fromDaoOrganization2.getOrganizationId());
        assertEquals(fromDaoOrganization2, testOrganization2);
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero3));
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero4));
        // fromDaoOrganization2 has one member in members
        
        List<Organization> heroOrganizations = new ArrayList();
        testOrganization.getMembers().clear();
        heroOrganizations.add(testOrganization);
        testHero.setOrganizations(heroOrganizations);
        testHero2.setOrganizations(heroOrganizations);
        
        
        List<Organization> heroOrganizations2 = new ArrayList();
        testOrganization2.getMembers().clear();
        heroOrganizations2.add(testOrganization2);
        testHero3.setOrganizations(heroOrganizations2);
        testHero4.setOrganizations(heroOrganizations2);
        // set organization lists to compare with fromDaoHeroes
        
        fromDaoHero = heroDao.getHeroById(testHero.getHeroId());
        fromDaoHero2 = heroDao.getHeroById(testHero2.getHeroId());
        fromDaoHero3 = heroDao.getHeroById(testHero3.getHeroId());
        fromDaoHero4 = heroDao.getHeroById(testHero4.getHeroId());
        
        assertEquals(testHero, fromDaoHero);
        assertEquals(testHero2, fromDaoHero2);
        assertEquals(testHero3, fromDaoHero3);
        assertEquals(testHero4, fromDaoHero4);
        
        Location testLocation = new Location();
        testLocation.setName("Hero Docks");
        testLocation.setDescription("Hero City ship dock where commercial sea exports and imports happen. Houses thousands of shipping cointainers");
        testLocation.setStreetNumber("9");
        testLocation.setStreetName("Coast Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13888");
        testLocation.setLatitude(29.3158501);
        testLocation.setLongitude(-69.8004975);
        
        Location fromDaoLocation = locationDao.addLocation(testLocation);
        testLocation.setLocationId(fromDaoLocation.getLocationId());
        
        assertEquals(fromDaoLocation, testLocation);
        
        Location testLocation2 = new Location();
        testLocation2.setName("Hero Park");
        testLocation2.setDescription("Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species");
        testLocation2.setStreetNumber("1000");
        testLocation2.setStreetName("Park Avenue");
        testLocation2.setCity("Hero City");
        testLocation2.setState("Hero State");
        testLocation2.setZipcode("13924");
        testLocation2.setLatitude(30.2241918);
        testLocation2.setLongitude(-70.9027214);
     
        Location fromDaoLocation2 = locationDao.addLocation(testLocation2);
        testLocation2.setLocationId(fromDaoLocation2.getLocationId());
        
        assertEquals(fromDaoLocation2, testLocation2);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().minusDays(5));
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(fromDaoHero);
        sightingHeroes.add(fromDaoHero2);
        sightingHeroes.add(fromDaoHero3);
        sighting.setLocation(fromDaoLocation);
        sighting.setHeroesSighted(sightingHeroes);
        Sighting fromDaoSighting = sightingDao.addSighting(sighting);
        sighting.setSightingId(fromDaoSighting.getSightingId());
        assertEquals(fromDaoSighting, sighting);
        
        fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());
        for (Hero hero : sightingHeroes) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        // heros obtained from sighting object do not have nested object
        assertEquals(fromDaoSighting, sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now().minusDays(2));
        List<Hero> sightingHeroes2 = new ArrayList<>();
        sightingHeroes2.add(fromDaoHero4);
        sighting2.setLocation(fromDaoLocation2);
        sighting2.setHeroesSighted(sightingHeroes2);
        Sighting fromDaoSighting2 = sightingDao.addSighting(sighting2);
        //sighting2.setSightingId(fromDaoSighting2.getSightingId());
        
        assertEquals(fromDaoSighting2, sighting2);
        
        fromDaoSighting2 = sightingDao.getSightingById(sighting2.getSightingId());
        for (Hero hero : sightingHeroes2) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        assertEquals(fromDaoSighting2, sighting2);
        
        List<Sighting> allSightings = sightingDao.getAllSightings();
        
        assertEquals(allSightings.size(), 2);
        assertTrue(allSightings.contains(sighting));
        assertTrue(allSightings.contains(sighting2));
    }

    
    @Test
    public void testUpdateSighting() {
        
        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero.setSuperPower("Apparition");
        Hero fromDaoHero = heroDao.addHero(testHero);
        testHero.setHeroId(fromDaoHero.getHeroId());
        assertEquals(fromDaoHero, testHero);
        // testHero has empty lists for orgs and locations
       
        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2.setSuperPower("Morphs into concrete");
        Hero fromDaoHero2 = heroDao.addHero(testHero2);
        testHero2.setHeroId(fromDaoHero2.getHeroId());
        assertEquals(fromDaoHero2, testHero2);
        //testHero2 has empty lists for orgs and locations
        
        Hero testHero3 = new Hero();
        testHero3.setName("Teapot");
        testHero3.setDescription("A sumo wrestler and former Yakuza gang member, known for his tea-stained teeth");
        testHero3.setSuperPower("Immense weight and strength");
        Hero fromDaoHero3 = heroDao.addHero(testHero3);
        testHero3.setHeroId(fromDaoHero3.getHeroId());
        assertEquals(fromDaoHero3, testHero3);
        //testHero3 has empty lists for orgs and locations
        
        Hero testHero4 = new Hero();
        testHero4.setName("Barbee");
        testHero4.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero4.setSuperPower("Shoots barbed wire");
        Hero fromDaoHero4 = heroDao.addHero(testHero4);
       
        testHero4.setHeroId(fromDaoHero4.getHeroId());
        assertEquals(fromDaoHero4, testHero4);
        
        Organization testOrganization = new Organization();
        testOrganization.setName("The Good Guys");
        testOrganization.setDescription("A small cohort of 'talented' individuals who fight crime and help the helpless");
        testOrganization.setStreetNumber("88");
        testOrganization.setStreetName("Clover Road");
        testOrganization.setCity("Hero City");
        testOrganization.setState("Hero State");
        testOrganization.setZipcode("13888");
        testOrganization.setEmail("goodguys@good.heroes");
        testOrganization.setPhone("8881234567");
        List<Hero> organizationHeroes = new ArrayList<>();
        organizationHeroes.add(fromDaoHero);
        organizationHeroes.add(fromDaoHero2);
        testOrganization.setMembers(organizationHeroes);
        Organization fromDaoOrganization = organizationDao.addOrganization(testOrganization);
        testOrganization.setOrganizationId(fromDaoOrganization.getOrganizationId());
        // testOrganizaton has array of members containing two heroes
        
        assertEquals(fromDaoOrganization, testOrganization);
        assertTrue(fromDaoOrganization.getMembers().contains(testHero));
        assertTrue(fromDaoOrganization.getMembers().contains(testHero2));
        
        
        Organization testOrganization2 = new Organization();
        testOrganization2.setName("The Syndicate");
        testOrganization2.setDescription("A powerful and sinister organization intent on assassinating all good super people");
        testOrganization2.setStreetNumber("99A");
        testOrganization2.setStreetName("Dagger Lane");
        testOrganization2.setCity("Hero City");
        testOrganization2.setState("Hero State");
        testOrganization2.setZipcode("13666");
        testOrganization2.setEmail("syndicate@villain.org");
        testOrganization2.setPhone("6664444444");
        List<Hero> organizationHeroes2 = new ArrayList<>();
        organizationHeroes2.add(fromDaoHero3);
        organizationHeroes2.add(fromDaoHero4);
        testOrganization2.setMembers(organizationHeroes2);
        // testOrganization2 has third hero only as a member
        
        Organization fromDaoOrganization2 = organizationDao.addOrganization(testOrganization2);
        testOrganization2.setOrganizationId(fromDaoOrganization2.getOrganizationId());
        assertEquals(fromDaoOrganization2, testOrganization2);
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero3));
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero4));
        // fromDaoOrganization2 has one member in members
        
        List<Organization> heroOrganizations = new ArrayList();
        testOrganization.getMembers().clear();
        heroOrganizations.add(testOrganization);
        testHero.setOrganizations(heroOrganizations);
        testHero2.setOrganizations(heroOrganizations);
        
        
        List<Organization> heroOrganizations2 = new ArrayList();
        testOrganization2.getMembers().clear();
        heroOrganizations2.add(testOrganization2);
        testHero3.setOrganizations(heroOrganizations2);
        testHero4.setOrganizations(heroOrganizations2);
        // set organization lists to compare with fromDaoHeroes
        
        fromDaoHero = heroDao.getHeroById(testHero.getHeroId());
        fromDaoHero2 = heroDao.getHeroById(testHero2.getHeroId());
        fromDaoHero3 = heroDao.getHeroById(testHero3.getHeroId());
        fromDaoHero4 = heroDao.getHeroById(testHero4.getHeroId());
        
        assertEquals(testHero, fromDaoHero);
        assertEquals(testHero2, fromDaoHero2);
        assertEquals(testHero3, fromDaoHero3);
        assertEquals(testHero4, fromDaoHero4);
        
        Location testLocation = new Location();
        testLocation.setName("Hero Docks");
        testLocation.setDescription("Hero City ship dock where commercial sea exports and imports happen. Houses thousands of shipping cointainers");
        testLocation.setStreetNumber("9");
        testLocation.setStreetName("Coast Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13888");
        testLocation.setLatitude(29.3158501);
        testLocation.setLongitude(-69.8004975);
        
        Location fromDaoLocation = locationDao.addLocation(testLocation);
        testLocation.setLocationId(fromDaoLocation.getLocationId());
        
        assertEquals(fromDaoLocation, testLocation);
        
        Location testLocation2 = new Location();
        testLocation2.setName("Hero Park");
        testLocation2.setDescription("Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species");
        testLocation2.setStreetNumber("1000");
        testLocation2.setStreetName("Park Avenue");
        testLocation2.setCity("Hero City");
        testLocation2.setState("Hero State");
        testLocation2.setZipcode("13924");
        testLocation2.setLatitude(30.2241918);
        testLocation2.setLongitude(-70.9027214);
     
        Location fromDaoLocation2 = locationDao.addLocation(testLocation2);
        testLocation2.setLocationId(fromDaoLocation2.getLocationId());
        
        assertEquals(fromDaoLocation2, testLocation2);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().minusDays(5));
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(fromDaoHero2);
        sightingHeroes.add(fromDaoHero3);
        sighting.setLocation(fromDaoLocation);
        sighting.setHeroesSighted(sightingHeroes);
        Sighting fromDaoSighting = sightingDao.addSighting(sighting);
        sighting.setSightingId(fromDaoSighting.getSightingId());
        assertEquals(fromDaoSighting, sighting);
        
        fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());
        for (Hero hero : sightingHeroes) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        // heros obtained from sighting object do not have nested object
        assertEquals(fromDaoSighting, sighting);
        
        sighting.setDate(LocalDate.now().minusDays(2));
        sightingHeroes.clear();
        sightingHeroes.add(fromDaoHero);
        sightingHeroes.add(fromDaoHero4);
        sighting.setLocation(fromDaoLocation2);
        
        
        sightingDao.updateSighting(sighting);
        fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());
        
        for (Hero hero : sightingHeroes) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        assertEquals(fromDaoSighting, sighting);
        
    }

    @Test
    public void testDeleteSighting() {
        
        
        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero.setSuperPower("Apparition");
        Hero fromDaoHero = heroDao.addHero(testHero);
        testHero.setHeroId(fromDaoHero.getHeroId());
        assertEquals(fromDaoHero, testHero);
        // testHero has empty lists for orgs and locations
       
        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2.setSuperPower("Morphs into concrete");
        Hero fromDaoHero2 = heroDao.addHero(testHero2);
        testHero2.setHeroId(fromDaoHero2.getHeroId());
        assertEquals(fromDaoHero2, testHero2);
        //testHero2 has empty lists for orgs and locations
        
        Hero testHero3 = new Hero();
        testHero3.setName("Teapot");
        testHero3.setDescription("A sumo wrestler and former Yakuza gang member, known for his tea-stained teeth");
        testHero3.setSuperPower("Immense weight and strength");
        Hero fromDaoHero3 = heroDao.addHero(testHero3);
        testHero3.setHeroId(fromDaoHero3.getHeroId());
        assertEquals(fromDaoHero3, testHero3);
        //testHero3 has empty lists for orgs and locations
        
        Hero testHero4 = new Hero();
        testHero4.setName("Barbee");
        testHero4.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero4.setSuperPower("Shoots barbed wire");
        Hero fromDaoHero4 = heroDao.addHero(testHero4);
       
        testHero4.setHeroId(fromDaoHero4.getHeroId());
        assertEquals(fromDaoHero4, testHero4);
        
        Organization testOrganization = new Organization();
        testOrganization.setName("The Good Guys");
        testOrganization.setDescription("A small cohort of 'talented' individuals who fight crime and help the helpless");
        testOrganization.setStreetNumber("88");
        testOrganization.setStreetName("Clover Road");
        testOrganization.setCity("Hero City");
        testOrganization.setState("Hero State");
        testOrganization.setZipcode("13888");
        testOrganization.setEmail("goodguys@good.heroes");
        testOrganization.setPhone("8881234567");
        List<Hero> organizationHeroes = new ArrayList<>();
        organizationHeroes.add(fromDaoHero);
        organizationHeroes.add(fromDaoHero2);
        testOrganization.setMembers(organizationHeroes);
        Organization fromDaoOrganization = organizationDao.addOrganization(testOrganization);
        testOrganization.setOrganizationId(fromDaoOrganization.getOrganizationId());
        // testOrganizaton has array of members containing two heroes
        
        assertEquals(fromDaoOrganization, testOrganization);
        assertTrue(fromDaoOrganization.getMembers().contains(testHero));
        assertTrue(fromDaoOrganization.getMembers().contains(testHero2));
        
        
        Organization testOrganization2 = new Organization();
        testOrganization2.setName("The Syndicate");
        testOrganization2.setDescription("A powerful and sinister organization intent on assassinating all good super people");
        testOrganization2.setStreetNumber("99A");
        testOrganization2.setStreetName("Dagger Lane");
        testOrganization2.setCity("Hero City");
        testOrganization2.setState("Hero State");
        testOrganization2.setZipcode("13666");
        testOrganization2.setEmail("syndicate@villain.org");
        testOrganization2.setPhone("6664444444");
        List<Hero> organizationHeroes2 = new ArrayList<>();
        organizationHeroes2.add(fromDaoHero3);
        organizationHeroes2.add(fromDaoHero4);
        testOrganization2.setMembers(organizationHeroes2);
        // testOrganization2 has third hero only as a member
        
        Organization fromDaoOrganization2 = organizationDao.addOrganization(testOrganization2);
        testOrganization2.setOrganizationId(fromDaoOrganization2.getOrganizationId());
        assertEquals(fromDaoOrganization2, testOrganization2);
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero3));
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero4));
        // fromDaoOrganization2 has one member in members
        
        List<Organization> heroOrganizations = new ArrayList();
        testOrganization.getMembers().clear();
        heroOrganizations.add(testOrganization);
        testHero.setOrganizations(heroOrganizations);
        testHero2.setOrganizations(heroOrganizations);
        
        
        List<Organization> heroOrganizations2 = new ArrayList();
        testOrganization2.getMembers().clear();
        heroOrganizations2.add(testOrganization2);
        testHero3.setOrganizations(heroOrganizations2);
        testHero4.setOrganizations(heroOrganizations2);
        // set organization lists to compare with fromDaoHeroes
        
        fromDaoHero = heroDao.getHeroById(testHero.getHeroId());
        fromDaoHero2 = heroDao.getHeroById(testHero2.getHeroId());
        fromDaoHero3 = heroDao.getHeroById(testHero3.getHeroId());
        fromDaoHero4 = heroDao.getHeroById(testHero4.getHeroId());
        
        assertEquals(testHero, fromDaoHero);
        assertEquals(testHero2, fromDaoHero2);
        assertEquals(testHero3, fromDaoHero3);
        assertEquals(testHero4, fromDaoHero4);
        
        Location testLocation = new Location();
        testLocation.setName("Hero Docks");
        testLocation.setDescription("Hero City ship dock where commercial sea exports and imports happen. Houses thousands of shipping cointainers");
        testLocation.setStreetNumber("9");
        testLocation.setStreetName("Coast Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13888");
        testLocation.setLatitude(29.3158501);
        testLocation.setLongitude(-69.8004975);
        
        Location fromDaoLocation = locationDao.addLocation(testLocation);
        testLocation.setLocationId(fromDaoLocation.getLocationId());
        
        assertEquals(fromDaoLocation, testLocation);
        
        Location testLocation2 = new Location();
        testLocation2.setName("Hero Park");
        testLocation2.setDescription("Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species");
        testLocation2.setStreetNumber("1000");
        testLocation2.setStreetName("Park Avenue");
        testLocation2.setCity("Hero City");
        testLocation2.setState("Hero State");
        testLocation2.setZipcode("13924");
        testLocation2.setLatitude(30.2241918);
        testLocation2.setLongitude(-70.9027214);
     
        Location fromDaoLocation2 = locationDao.addLocation(testLocation2);
        testLocation2.setLocationId(fromDaoLocation2.getLocationId());
        
        assertEquals(fromDaoLocation2, testLocation2);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().minusDays(5));
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(fromDaoHero);
        sightingHeroes.add(fromDaoHero2);
        sightingHeroes.add(fromDaoHero3);
        sighting.setLocation(fromDaoLocation);
        sighting.setHeroesSighted(sightingHeroes);
        Sighting fromDaoSighting = sightingDao.addSighting(sighting);
        sighting.setSightingId(fromDaoSighting.getSightingId());
        assertEquals(fromDaoSighting, sighting);
        
        fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());
        for (Hero hero : sightingHeroes) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        // heros obtained from sighting object do not have nested object
        assertEquals(fromDaoSighting, sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now().minusDays(2));
        List<Hero> sightingHeroes2 = new ArrayList<>();
        sightingHeroes2.add(fromDaoHero4);
        sighting2.setLocation(fromDaoLocation2);
        sighting2.setHeroesSighted(sightingHeroes2);
        Sighting fromDaoSighting2 = sightingDao.addSighting(sighting2);
        //sighting2.setSightingId(fromDaoSighting2.getSightingId());
        
        assertEquals(fromDaoSighting2, sighting2);
        
        fromDaoSighting2 = sightingDao.getSightingById(sighting2.getSightingId());
        for (Hero hero : sightingHeroes2) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        assertEquals(fromDaoSighting2, sighting2);
        
        List<Sighting> allSightings = sightingDao.getAllSightings();
        
        assertEquals(allSightings.size(), 2);
        assertTrue(allSightings.contains(sighting));
        assertTrue(allSightings.contains(sighting2));
    
        sightingDao.deleteSighting(sighting2);
        
        allSightings = sightingDao.getAllSightings();
        assertEquals(allSightings.size(), 1);
        assertTrue(allSightings.contains(fromDaoSighting));
        
        fromDaoLocation2 = locationDao.getLocationById(fromDaoLocation2.getLocationId());
        assertTrue(fromDaoLocation2.getSightedHeroes().isEmpty());
        
        fromDaoHero4 = heroDao.getHeroById(fromDaoHero4.getHeroId());
        assertTrue(fromDaoHero4.getLocations().isEmpty());
        
    }
    
    @Test 
    public void testGetSightingsByDate() {
        
        
        
        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero.setSuperPower("Apparition");
        Hero fromDaoHero = heroDao.addHero(testHero);
        testHero.setHeroId(fromDaoHero.getHeroId());
        assertEquals(fromDaoHero, testHero);
        // testHero has empty lists for orgs and locations
       
        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2.setSuperPower("Morphs into concrete");
        Hero fromDaoHero2 = heroDao.addHero(testHero2);
        testHero2.setHeroId(fromDaoHero2.getHeroId());
        assertEquals(fromDaoHero2, testHero2);
        //testHero2 has empty lists for orgs and locations
        
        Hero testHero3 = new Hero();
        testHero3.setName("Teapot");
        testHero3.setDescription("A sumo wrestler and former Yakuza gang member, known for his tea-stained teeth");
        testHero3.setSuperPower("Immense weight and strength");
        Hero fromDaoHero3 = heroDao.addHero(testHero3);
        testHero3.setHeroId(fromDaoHero3.getHeroId());
        assertEquals(fromDaoHero3, testHero3);
        //testHero3 has empty lists for orgs and locations
        
        Hero testHero4 = new Hero();
        testHero4.setName("Barbee");
        testHero4.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero4.setSuperPower("Shoots barbed wire");
        Hero fromDaoHero4 = heroDao.addHero(testHero4);
       
        testHero4.setHeroId(fromDaoHero4.getHeroId());
        assertEquals(fromDaoHero4, testHero4);
        
        Organization testOrganization = new Organization();
        testOrganization.setName("The Good Guys");
        testOrganization.setDescription("A small cohort of 'talented' individuals who fight crime and help the helpless");
        testOrganization.setStreetNumber("88");
        testOrganization.setStreetName("Clover Road");
        testOrganization.setCity("Hero City");
        testOrganization.setState("Hero State");
        testOrganization.setZipcode("13888");
        testOrganization.setEmail("goodguys@good.heroes");
        testOrganization.setPhone("8881234567");
        List<Hero> organizationHeroes = new ArrayList<>();
        organizationHeroes.add(fromDaoHero);
        organizationHeroes.add(fromDaoHero2);
        testOrganization.setMembers(organizationHeroes);
        Organization fromDaoOrganization = organizationDao.addOrganization(testOrganization);
        testOrganization.setOrganizationId(fromDaoOrganization.getOrganizationId());
        // testOrganizaton has array of members containing two heroes
        
        assertEquals(fromDaoOrganization, testOrganization);
        assertTrue(fromDaoOrganization.getMembers().contains(testHero));
        assertTrue(fromDaoOrganization.getMembers().contains(testHero2));
        
        
        Organization testOrganization2 = new Organization();
        testOrganization2.setName("The Syndicate");
        testOrganization2.setDescription("A powerful and sinister organization intent on assassinating all good super people");
        testOrganization2.setStreetNumber("99A");
        testOrganization2.setStreetName("Dagger Lane");
        testOrganization2.setCity("Hero City");
        testOrganization2.setState("Hero State");
        testOrganization2.setZipcode("13666");
        testOrganization2.setEmail("syndicate@villain.org");
        testOrganization2.setPhone("6664444444");
        List<Hero> organizationHeroes2 = new ArrayList<>();
        organizationHeroes2.add(fromDaoHero3);
        organizationHeroes2.add(fromDaoHero4);
        testOrganization2.setMembers(organizationHeroes2);
        // testOrganization2 has third hero only as a member
        
        Organization fromDaoOrganization2 = organizationDao.addOrganization(testOrganization2);
        testOrganization2.setOrganizationId(fromDaoOrganization2.getOrganizationId());
        assertEquals(fromDaoOrganization2, testOrganization2);
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero3));
        assertTrue(fromDaoOrganization2.getMembers().contains(testHero4));
        // fromDaoOrganization2 has one member in members
        
        List<Organization> heroOrganizations = new ArrayList();
        testOrganization.getMembers().clear();
        heroOrganizations.add(testOrganization);
        testHero.setOrganizations(heroOrganizations);
        testHero2.setOrganizations(heroOrganizations);
        
        
        List<Organization> heroOrganizations2 = new ArrayList();
        testOrganization2.getMembers().clear();
        heroOrganizations2.add(testOrganization2);
        testHero3.setOrganizations(heroOrganizations2);
        testHero4.setOrganizations(heroOrganizations2);
        // set organization lists to compare with fromDaoHeroes
        
        fromDaoHero = heroDao.getHeroById(testHero.getHeroId());
        fromDaoHero2 = heroDao.getHeroById(testHero2.getHeroId());
        fromDaoHero3 = heroDao.getHeroById(testHero3.getHeroId());
        fromDaoHero4 = heroDao.getHeroById(testHero4.getHeroId());
        
        assertEquals(testHero, fromDaoHero);
        assertEquals(testHero2, fromDaoHero2);
        assertEquals(testHero3, fromDaoHero3);
        assertEquals(testHero4, fromDaoHero4);
        
        Location testLocation = new Location();
        testLocation.setName("Hero Docks");
        testLocation.setDescription("Hero City ship dock where commercial sea exports and imports happen. Houses thousands of shipping cointainers");
        testLocation.setStreetNumber("9");
        testLocation.setStreetName("Coast Avenue");
        testLocation.setCity("Hero City");
        testLocation.setState("Hero State");
        testLocation.setZipcode("13888");
        testLocation.setLatitude(29.3158501);
        testLocation.setLongitude(-69.8004975);
        
        Location fromDaoLocation = locationDao.addLocation(testLocation);
        testLocation.setLocationId(fromDaoLocation.getLocationId());
        
        assertEquals(fromDaoLocation, testLocation);
        
        Location testLocation2 = new Location();
        testLocation2.setName("Hero Park");
        testLocation2.setDescription("Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species");
        testLocation2.setStreetNumber("1000");
        testLocation2.setStreetName("Park Avenue");
        testLocation2.setCity("Hero City");
        testLocation2.setState("Hero State");
        testLocation2.setZipcode("13924");
        testLocation2.setLatitude(30.2241918);
        testLocation2.setLongitude(-70.9027214);
     
        Location fromDaoLocation2 = locationDao.addLocation(testLocation2);
        testLocation2.setLocationId(fromDaoLocation2.getLocationId());
        
        assertEquals(fromDaoLocation2, testLocation2);
        
        Sighting sighting = new Sighting();
        sighting.setDate(LocalDate.now().minusDays(5));
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(fromDaoHero);
        sightingHeroes.add(fromDaoHero2);
        sightingHeroes.add(fromDaoHero3);
        sighting.setLocation(fromDaoLocation);
        sighting.setHeroesSighted(sightingHeroes);
        Sighting fromDaoSighting = sightingDao.addSighting(sighting);
        sighting.setSightingId(fromDaoSighting.getSightingId());
        assertEquals(fromDaoSighting, sighting);
        
        fromDaoSighting = sightingDao.getSightingById(sighting.getSightingId());
        for (Hero hero : sightingHeroes) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        // heros obtained from sighting object do not have nested object
        assertEquals(fromDaoSighting, sighting);
        
        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDate.now().minusDays(2));
        List<Hero> sightingHeroes2 = new ArrayList<>();
        sightingHeroes2.add(fromDaoHero4);
        sighting2.setLocation(fromDaoLocation2);
        sighting2.setHeroesSighted(sightingHeroes2);
        Sighting fromDaoSighting2 = sightingDao.addSighting(sighting2);
        //sighting2.setSightingId(fromDaoSighting2.getSightingId());
        
        assertEquals(fromDaoSighting2, sighting2);
        
        fromDaoSighting2 = sightingDao.getSightingById(sighting2.getSightingId());
        for (Hero hero : sightingHeroes2) {
            hero.setOrganizations(new ArrayList<>());
            hero.setLocations(new ArrayList<>());
        }
        assertEquals(fromDaoSighting2, sighting2);
        
        List<Sighting> allSightings = sightingDao.getSightingsByDate(LocalDate.now().minusDays(5));
        
        assertEquals(allSightings.size(), 1);
        assertTrue(allSightings.contains(fromDaoSighting));
       
    }
    
}
