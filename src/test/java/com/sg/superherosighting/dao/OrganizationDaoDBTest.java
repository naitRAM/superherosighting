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
import com.sg.superherosighting.entity.SuperPower;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author rmans
 */
@SpringBootTest
public class OrganizationDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperPowerDao superPowerDao;

    public OrganizationDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {

        List<Sighting> sightings = sightingDao.getAllSightings();
        sightings.forEach(sighting -> {
            sightingDao.deleteSighting(sighting);
        });
        List<Hero> heroes = heroDao.getAllHeroes();
        heroes.forEach(hero -> {
            heroDao.deleteHero(hero);
        });

        List<Location> locations = locationDao.getAllLocations();
        locations.forEach(location -> {
            locationDao.deleteLocation(location);
        });

        List<Organization> organizations = organizationDao.getAllOrganizations();
        organizations.forEach(organization -> {
            organizationDao.deleteOrganization(organization);
        });
        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();
        superPowers.forEach(superPower -> {
            superPowerDao.deleteSuperPower(superPower);
        });

    }

    @Test
    public void testAddGetOrganization() {
        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2 = heroDao.addHero(testHero2);

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
        organizationHeroes.add(testHero);
        organizationHeroes.add(testHero2);
        testOrganization.setMembers(organizationHeroes);

        Organization fromDao = organizationDao.addOrganization(testOrganization);
        testOrganization.setOrganizationId(fromDao.getOrganizationId());

        assertEquals(testOrganization, fromDao);

        fromDao = organizationDao.getOrganizationById(fromDao.getOrganizationId());

        assertEquals(testOrganization, fromDao);

    }

    @Test
    public void testGetAllOrganizations() {

        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2 = heroDao.addHero(testHero2);

        Hero testHero3 = new Hero();
        testHero3.setName("Barbee");
        testHero3.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero3 = heroDao.addHero(testHero3);

        Hero testHero4 = new Hero();
        testHero4.setName("Teapot");
        testHero4.setDescription("A sumo wrestler and former Yakuza gang member, known for his tea-stained teeth");
        testHero4 = heroDao.addHero(testHero4);

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

        List<Hero> organizationHeroes = new ArrayList<>();
        organizationHeroes.add(testHero);
        organizationHeroes.add(testHero2);
        testOrganization.setMembers(organizationHeroes);

        List<Hero> organizationHeroes2 = new ArrayList<>();
        organizationHeroes2.add(testHero3);
        organizationHeroes2.add(testHero4);
        testOrganization2.setMembers(organizationHeroes2);

        Organization fromDao = organizationDao.addOrganization(testOrganization);
        Organization fromDao2 = organizationDao.addOrganization(testOrganization2);

        testOrganization.setOrganizationId(fromDao.getOrganizationId());
        testOrganization2.setOrganizationId(fromDao2.getOrganizationId());

        List<Organization> allOrganizations = organizationDao.getAllOrganizations();

        assertEquals(allOrganizations.size(), 2);
        assertTrue(allOrganizations.contains(testOrganization));
        assertTrue(allOrganizations.contains(testOrganization2));

    }

    @Test
    public void testUpdateOrganization() {

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

        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2 = heroDao.addHero(testHero2);

        List<Hero> organizationHeroes = new ArrayList<>();
        organizationHeroes.add(testHero);
        organizationHeroes.add(testHero2);
        testOrganization.setMembers(organizationHeroes);
        assertEquals(testOrganization.getMembers().size(), 2);

        Organization fromDao = organizationDao.addOrganization(testOrganization);
        testOrganization.setOrganizationId(fromDao.getOrganizationId());
        assertEquals(fromDao, testOrganization);

        testOrganization.setName("The Best Guys");
        testOrganization.setPhone("8888765432");
        testOrganization.setStreetNumber("4");
        testOrganization.setStreetName("Benevolence Way");
        boolean isRemoved = organizationHeroes.remove(testHero2);
        assertTrue(isRemoved);
        assertEquals(testOrganization.getMembers().size(), 1);
        organizationDao.updateOrganization(testOrganization);

        fromDao = organizationDao.getOrganizationById(testOrganization.getOrganizationId());
        assertEquals(fromDao, testOrganization);
    }

    @Test
    public void testDeleteOrganization() {
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

        Hero testHero = new Hero();
        testHero.setName("Checkers");
        testHero.setDescription("An elderly magician with sleight of hand and a cheeky smile, known for his checkered suits");
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("Stoneman");
        testHero2.setDescription("Construction worker by day, crime-fighter by night, can turn his arms into stone");
        testHero2 = heroDao.addHero(testHero2);

        Hero testHero3 = new Hero();
        testHero3.setName("Barbee");
        testHero3.setDescription("An evil madwoman with hair made of blonde barbs used to snare her victims");
        testHero3 = heroDao.addHero(testHero3);

        Hero testHero4 = new Hero();
        testHero4.setName("Teapot");
        testHero4.setDescription("A sumo wrestler and former Yakuza gang member, known for his tea-stained teeth");
        testHero4 = heroDao.addHero(testHero4);

        List<Hero> organizationHeroes = new ArrayList<>();
        organizationHeroes.add(testHero);
        organizationHeroes.add(testHero2);
        testOrganization.setMembers(organizationHeroes);
        assertEquals(testOrganization.getMembers().size(), 2);

        List<Hero> organizationHeroes2 = new ArrayList<>();
        organizationHeroes2.add(testHero3);
        organizationHeroes2.add(testHero4);
        testOrganization2.setMembers(organizationHeroes2);
        assertEquals(testOrganization2.getMembers().size(), 2);

        Organization fromDao = organizationDao.addOrganization(testOrganization);
        testOrganization.setOrganizationId(fromDao.getOrganizationId());
        assertEquals(fromDao, testOrganization);

        Organization fromDao2 = organizationDao.addOrganization(testOrganization2);
        testOrganization2.setOrganizationId(fromDao2.getOrganizationId());
        assertEquals(fromDao2, testOrganization2);

        List<Organization> organizations = organizationDao.getAllOrganizations();
        assertTrue(organizations.contains(testOrganization2));
        assertTrue(organizations.contains(testOrganization));

        organizationDao.deleteOrganization(testOrganization2);
        organizations = organizationDao.getAllOrganizations();
        assertEquals(organizations.size(), 1);
        assertTrue(organizations.contains(testOrganization));
        assertFalse(organizations.contains(testOrganization2));

        List<Hero> heroes = heroDao.getAllHeroes();
        assertEquals(heroes.size(), 4);
        assertTrue(heroes.contains(testHero3));
        assertTrue(heroes.contains(testHero4));
        testHero3 = heroDao.getHeroById(testHero3.getHeroId());
        testHero4 = heroDao.getHeroById(testHero4.getHeroId());
        assertTrue(testHero3.getOrganizations().isEmpty());
        assertTrue(testHero4.getOrganizations().isEmpty());
        testHero = heroDao.getHeroById(testHero.getHeroId());
        testHero2 = heroDao.getHeroById(testHero2.getHeroId());

        testOrganization.setMembers(new ArrayList<>());
        assertEquals(testHero.getOrganizations().size(), 1);
        assertTrue(testHero.getOrganizations().contains(testOrganization));

        assertEquals(testHero2.getOrganizations().size(), 1);
        assertTrue(testHero2.getOrganizations().contains(testOrganization));
    }

}
