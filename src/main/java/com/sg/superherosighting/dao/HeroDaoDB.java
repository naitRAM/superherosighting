package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.LocationDaoDB.LocationMapper;
import com.sg.superherosighting.dao.OrganizationDaoDB.OrganizationMapper;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Jun. 13, 2022
 * purpose: 
 */
@Repository
public class HeroDaoDB implements HeroDao {
    @Autowired
    JdbcTemplate jdbc;
            
    @Override
    public Hero getHeroById(int id) {
        String query = "SELECT * FROM Hero WHERE heroId = ?";
        try {
        Hero hero = jdbc.queryForObject(query, new HeroMapper(), id);
        hero.setOrganizations(getOrganizationsForHero(hero));
        hero.setLocations(getLocationsForHero(hero));
        return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    private List<Organization> getOrganizationsForHero(Hero hero) {
        String query = "SELECT Organization.*, Location.* FROM `Organization` "
                + "JOIN Location ON Location.locationId = "
                + "Organization.locationId JOIN HeroOrganization ON "
                + "`Organization`.organizationId = "
                + "HeroOrganization.organizationId JOIN Hero ON "
                + "HeroOrganization.heroId = Hero.heroId WHERE Hero.heroId = ?";
        List<Organization> heroOrganizations = jdbc.query(query, 
                new OrganizationMapper(), hero.getHeroId());
        return heroOrganizations;
    }
    
    private List<Location> getLocationsForHero(Hero hero) {
        String query = "SELECT Location.* FROM Location JOIN Sighting on "
                + "Location.locationId = Sighting.locationId JOIN SightingHero "
                + "ON Sighting.sightingId = SightingHero.sightingId JOIN Hero ON"
                + " Hero.heroId = sightingHero.heroId WHERE Hero.heroId = ? AND "
                + "Location.locationId NOT IN (SELECT locationId FROM `Organization`) "
                + "GROUP BY Location.locationId";
        List<Location> heroLocations = jdbc.query(query, new LocationMapper(),
                hero.getHeroId());
        return heroLocations;
    }
    
    private void associateLocationsToHero(List<Hero> heroes) {
        for (Hero hero : heroes) {
            hero.setLocations(getLocationsForHero(hero));
        }
    }
    
    private void associateOrganizationsToHero(List<Hero> heroes) {
        for (Hero hero: heroes) {
            hero.setOrganizations(getOrganizationsForHero(hero));
        }
    }
    
    @Override
    public List<Hero> getAllHeroes() {
        String query = "SELECT * FROM Hero";
        List<Hero> heroes = jdbc.query(query, new HeroMapper());
        associateLocationsToHero(heroes);
        associateOrganizationsToHero(heroes);
        return heroes;
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        String insertStmt = "INSERT INTO Hero (name, description, superPower)"
                + "VALUES (?, ?, ?)";
        jdbc.update(insertStmt, hero.getName(), hero.getDescription(), 
                hero.getSuperPower());
        int heroId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroId(heroId);
        insertHeroOrganization(hero);
        return hero;
    }
    
    private void insertHeroOrganization(Hero hero) {
        String insertStmt = "INSERT INTO HeroOrganization (heroId, organizationId)"
                + " VALUES (?, ?)";
        for (Organization organization : hero.getOrganizations()) {
            jdbc.update(insertStmt, hero.getHeroId(), organization.getOrganizationId());
        }
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {
        String updateStmt = "UPDATE Hero SET name = ?, description = ?, "
                + "superPower = ? WHERE heroId = ?";
        jdbc.update(updateStmt, hero.getName(), hero.getDescription(), 
                hero.getSuperPower(), hero.getHeroId());
        jdbc.update("DELETE HeroOrganization.* FROM HeroOrganization WHERE "
                + "heroId = ?", hero.getHeroId());
        insertHeroOrganization(hero);
        
    }

    @Transactional
    @Override
    public void deleteHero(Hero hero) {
        String deleteHeroOrganizationsStmt = "DELETE FROM HeroOrganization "
                + "WHERE heroId = ?";
        String deleteSightingHeroesStmt = "DELETE  FROM SightingHero "
                + "WHERE heroId = ?";
        String deleteHeroStmt = "DELETE FROM Hero WHERE heroId = ?";
        // in case our Hero was the only one sighted in a sighting
        String deleteSightingsWithNoHeroSightings = "DELETE FROM Sighting "
                + "WHERE SightingId NOT IN (SELECT SightingId FROM SightingHero)";
        jdbc.update(deleteHeroOrganizationsStmt, hero.getHeroId());
        jdbc.update(deleteSightingHeroesStmt, hero.getHeroId());
        jdbc.update(deleteHeroStmt, hero.getHeroId());
        jdbc.update(deleteSightingsWithNoHeroSightings);
    }

    public static final class HeroMapper implements RowMapper<Hero> {
        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            return new Hero(rs.getInt("heroId"), rs.getString("name"), rs.getString("description"), rs.getString("superPower"));
        }
    }
}
