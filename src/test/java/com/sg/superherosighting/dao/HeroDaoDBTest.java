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
public class HeroDaoDBTest {
    
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    SightingDao sightingDao;
    
    public HeroDaoDBTest() {
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
    public void testAddGetHero() {
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
        testOrganization = organizationDao.addOrganization(testOrganization);
        
        
        List<Organization> heroOrganizations = new ArrayList<>();
        heroOrganizations.add(testOrganization);
        
        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero.setSuperPower("Apparition");
        testHero.setOrganizations(heroOrganizations);
        
        Hero fromDao = heroDao.addHero(testHero);
        testHero.setHeroId(fromDao.getHeroId());
        assertEquals(fromDao, testHero);
        
        Sighting testSighting = new Sighting();
        testSighting.setLocation(testLocation);
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(testHero);
        testSighting.setHeroesSighted(sightingHeroes);
        LocalDate date = LocalDate.now();
        testSighting.setDate(date);
        sightingDao.addSighting(testSighting);
        
        List<Location> heroLocations = new ArrayList<>();
        heroLocations.add(testLocation);
        
        testHero.setLocations(heroLocations);
        
        fromDao = heroDao.getHeroById(testHero.getHeroId());
        
        assertEquals(fromDao, testHero);
        
    }

    @Test
    public void testGetAllHeroes() {
        
        Organization testOrganization = new Organization();
        testOrganization.setName("The Syndicate");
        testOrganization.setDescription("A powerful and sinister organization intent on assassinating all good super people");
        testOrganization.setStreetNumber("99A");
        testOrganization.setStreetName("Dagger Lane");
        testOrganization.setCity("Hero City");
        testOrganization.setState("Hero State");
        testOrganization.setZipcode("13666");
        testOrganization.setEmail("syndicate@villain.org");
        testOrganization.setPhone("6664444444");
        testOrganization = organizationDao.addOrganization(testOrganization);
        
        Organization testOrganization2 = new Organization();
        testOrganization2.setName("The Good Guys");
        testOrganization2.setDescription("A small cohort of 'talented' individuals who fight crime and help the helpless");
        testOrganization2.setStreetNumber("88");
        testOrganization2.setStreetName("Clover Road");
        testOrganization2.setCity("Hero City");
        testOrganization2.setState("Hero State");
        testOrganization2.setZipcode("13888");
        testOrganization2.setEmail("goodguys@good.heroes");
        testOrganization2.setPhone("8881234567");
        testOrganization2 = organizationDao.addOrganization(testOrganization2);
        
        
        
        Hero testHero = new Hero();
        testHero.setName("Barbee");
        testHero.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero.setSuperPower("Shoots barbed wire");
        List<Organization> testHeroOrganizations = new ArrayList<>();
        testHeroOrganizations.add(testOrganization);
        testHero.setOrganizations(testHeroOrganizations);
        testHero = heroDao.addHero(testHero);
        

        Hero testHero2 = new Hero();
        testHero2.setName("Checkers");
        testHero2.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero2.setSuperPower("Apparition");
        List<Organization> testHeroOrganizations2 = new ArrayList<>();
        testHeroOrganizations2.add(testOrganization2);
      
        testHero2.setOrganizations(testHeroOrganizations2);
        testHero2 = heroDao.addHero(testHero2);
        
        List<Hero> allHeroes = heroDao.getAllHeroes();
        assertEquals(allHeroes.size(), 2);
        assertTrue(allHeroes.contains(testHero));
        assertTrue(allHeroes.contains(testHero2));
        
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
        testLocation2 = locationDao.addLocation(testLocation2);
        
        Sighting testSighting = new Sighting();
        testSighting.setLocation(testLocation);
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(testHero);
        sightingHeroes.add(testHero2);
        testSighting.setHeroesSighted(sightingHeroes);
        LocalDate date = LocalDate.now();
        testSighting.setDate(date);
        sightingDao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setLocation(testLocation2);
        List<Hero> sightingHeroes2 = new ArrayList<>();
        sightingHeroes2.add(testHero);
        testSighting2.setHeroesSighted(sightingHeroes2);
        LocalDate date2 = LocalDate.now().minusDays(5);
        testSighting2.setDate(date2);
        sightingDao.addSighting(testSighting2);
        
        List<Location> testHeroLocations = new ArrayList<>();
        testHeroLocations.add(testLocation);
        testHeroLocations.add(testLocation2);
        testHero.setLocations(testHeroLocations);
        
        List<Location> testHeroLocations2 = new ArrayList<>();
        testHeroLocations2.add(testLocation);
        testHero2.setLocations(testHeroLocations2);
        
        allHeroes = heroDao.getAllHeroes();
        
        assertEquals(allHeroes.size(), 2);
        assertTrue(allHeroes.contains(testHero));
        assertTrue(allHeroes.contains(testHero2));
    }

    
    @Test
    public void testUpdateHero() {
        
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
        testOrganization = organizationDao.addOrganization(testOrganization);
        
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
        testOrganization2 = organizationDao.addOrganization(testOrganization2);
        
        Hero testHero = new Hero();
        testHero.setName("Stoneman");
        testHero.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero.setSuperPower("Morphs into concrete");
        List<Organization> heroOrganizations = new ArrayList<>();
        heroOrganizations.add(testOrganization);
        testHero.setOrganizations(heroOrganizations);
        Hero fromDao = heroDao.addHero(testHero);
        assertEquals(testHero, fromDao);
        
        testHero.setDescription("Construction worker by day, pawn to evil by night. Used to be good before he"
                + " was mind-controlled by an unknown evil villain");
        
        heroOrganizations.clear();
        heroOrganizations.add(testOrganization2);
        testHero.setOrganizations(heroOrganizations);
        
        heroDao.updateHero(testHero);
        fromDao = heroDao.getHeroById(testHero.getHeroId());
        
        assertEquals(testHero, fromDao);
        
        
        
    }

    @Test
    public void testDeleteHero() {
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
        testOrganization = organizationDao.addOrganization(testOrganization);
        
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
        testOrganization2 = organizationDao.addOrganization(testOrganization2);
        
        Hero testHero = new Hero();
        testHero.setName("Stoneman");
        testHero.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero.setSuperPower("Morphs into concrete");
        List<Organization> heroOrganizations = new ArrayList<>();
        heroOrganizations.add(testOrganization);
        testHero.setOrganizations(heroOrganizations);
        Hero fromDao = heroDao.addHero(testHero);
        
        assertEquals(fromDao, testHero);
        
        Hero testHero2 = new Hero();
        testHero2.setName("Barbee");
        testHero2.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero2.setSuperPower("Shoots barbed wire");
        List<Organization> heroOrganizations2 = new ArrayList<>();
        heroOrganizations2.add(testOrganization2);
        testHero2.setOrganizations(heroOrganizations2);
        Hero fromDao2 = heroDao.addHero(testHero2);
        
        
        assertEquals(fromDao2, testHero2);
        
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
        
        Sighting testSighting = new Sighting();
        testSighting.setLocation(testLocation);
        List<Hero> sightingHeroes = new ArrayList<>();
        sightingHeroes.add(testHero);
        sightingHeroes.add(testHero2);
        testSighting.setHeroesSighted(sightingHeroes);
        LocalDate date = LocalDate.now();
        testSighting.setDate(date);
        testSighting = sightingDao.addSighting(testSighting);
        
        List<Location> heroLocations = new ArrayList<>();
        heroLocations.add(testLocation);
        testHero.setLocations(heroLocations);
        testHero2.setLocations(heroLocations);
        
        fromDao = heroDao.getHeroById(testHero.getHeroId());
        assertEquals(testHero, fromDao);
        
        testHero.setOrganizations(new ArrayList<>());
        testHero.setLocations(new ArrayList<>());
        testLocation = locationDao.getLocationById(testLocation.getLocationId());
        assertTrue(testLocation.getSightedHeroes().contains(testHero));
        testOrganization = organizationDao.getOrganizationById(testOrganization.getOrganizationId());
        assertTrue(testOrganization.getMembers().contains(testHero));
        assertTrue(testSighting.getHeroesSighted().contains(testHero));
        
        heroDao.deleteHero(testHero);
        
        
        
        List<Hero> allHeroes = heroDao.getAllHeroes();
        assertEquals(allHeroes.size(), 1);
        assertTrue(! allHeroes.contains(testHero));
        assertTrue(allHeroes.contains(testHero2));
        testHero2.setOrganizations(new ArrayList<>());
        testHero2.setLocations(new ArrayList<>());
        testLocation = locationDao.getLocationById(testLocation.getLocationId());
        assertTrue(! testLocation.getSightedHeroes().contains(testHero));
        assertEquals(testLocation.getSightedHeroes().size(), 1);
        assertTrue(testLocation.getSightedHeroes().contains(testHero2));
        testOrganization = organizationDao.getOrganizationById(testOrganization.getOrganizationId());
        assertTrue(! testOrganization.getMembers().contains(testHero));
        assertEquals(testOrganization.getMembers().size(), 0);
        testSighting = sightingDao.getSightingById(testSighting.getSightingId());
        assertTrue(! testSighting.getHeroesSighted().contains(testHero));
        assertEquals(testSighting.getHeroesSighted().size(), 1);
        assertTrue(testSighting.getHeroesSighted().contains(testHero2));
        
        heroDao.deleteHero(testHero2);
        allHeroes = heroDao.getAllHeroes();
        assertTrue(allHeroes.isEmpty());
        
        testLocation = locationDao.getLocationById(testLocation.getLocationId());
        assertTrue(testLocation.getSightedHeroes().isEmpty());
        
        List<Organization> organizations = organizationDao.getAllOrganizations();
        for (Organization organization : organizations) {
            assertTrue(organization.getMembers().isEmpty());
        }
        
        // both heroes sighted in the sighting were removed, therefore the sighting must be removed
        List<Sighting> sightings = sightingDao.getAllSightings();
        assertTrue(sightings.isEmpty());
        
    }
}
