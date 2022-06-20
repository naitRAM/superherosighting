package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.HeroDaoDB.HeroMapper;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Location getLocationById(int id) {
        String query = "SELECT * FROM Location WHERE locationId = ? AND "
                + "locationId NOT IN (SELECT locationId FROM `Organization`)";
        Location location =  jdbc.queryForObject(query, new LocationMapper(), id);
        List<Hero> heroes = getHeroesForLocation(location);
        location.setSightedHeroes(heroes);
        return location;
    }

    @Override
    public List<Location> getAllLocations() {
        String query = "SELECT * FROM Location WHERE locationId NOT IN "
                + "(SELECT locationId FROM `Organization`)";
        List<Location> locations =  jdbc.query(query, new LocationMapper());
        associateHeroesToLocation(locations);
        return locations;
        
    }
    
    private List<Hero> getHeroesForLocation(Location location) {
        String query = "SELECT Hero.* FROM Hero LEFT JOIN sightingHero ON "
                + "Hero.heroId = SightingHero.heroId LEFT JOIN Sighting ON "
                + "SightingHero.sightingId = Sighting.sightingId LEFT JOIN "
                + "Location ON Sighting.locationId = Location.locationId WHERE "
                + "Location.locationId = ? AND Location.LocationId NOT IN "
                + "(SELECT locationId FROM `Organization` GROUP BY Hero.heroId)";
        return jdbc.query(query, new HeroMapper(), location.getLocationId());
    }
    
    private void associateHeroesToLocation(List<Location> locations) {
        for (Location location : locations) {
            List<Hero> heroes = getHeroesForLocation(location);
            location.setSightedHeroes(heroes);
        }
    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        String insertStmt = "INSERT INTO Location (name, description,"
                + " streetNumber, streetName, city, state, zipcode, latitude,"
                + " longitude) VALUES (?,?,?,?,?,?,?,?,?)";
        jdbc.update(insertStmt, location.getName(), location.getDescription(),
                location.getStreetNumber(), location.getStreetName(), 
                location.getCity(), location.getState(), location.getZipcode(),
                location.getLatitude(), location.getLongitude());
        int locationId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationId(locationId);
        return location;
    }

    @Override
    public void updateLocation(Location location) {
        String updateStmt = "UPDATE Location SET name = ?, description = ?, "
                + "streetNumber = ?, streetName = ?, city = ?, state = ?,"
                + "zipcode = ?, latitude = ?, longitude = ? WHERE locationId = "
                + "? AND locationId NOT IN (SELECT locationId FROM `Organization`)";
        jdbc.update(updateStmt, location.getName(), location.getDescription(),
                location.getStreetNumber(), location.getStreetName(), 
                location.getCity(), location.getState(), location.getZipcode(),
                location.getLatitude(), location.getLongitude(), 
                location.getLocationId());
    }

    @Override
    @Transactional
    public void deleteLocation(Location location) {
        String deleteSightingHeroStmt = "DELETE sightingHero.* FROM sightingHero"
                + " JOIN sighting ON sightingHero.sightingId = "
                + "sighting.sightingId JOIN location ON sighting.locationId "
                + "= location.locationId WHERE location.locationId = ?";
        jdbc.update(deleteSightingHeroStmt, location.getLocationId());
        String deleteSightingStmt = "DELETE FROM Sighting WHERE locationId = ?";
        jdbc.update(deleteSightingStmt, location.getLocationId());
        String deleteLocationStmt = "DELETE FROM Location WHERE locationId = ?"
                + " AND locationId NOT IN (SELECT locationId FROM `Organization`)";
        jdbc.update(deleteLocationStmt, location.getLocationId());                
    }

    public static final class LocationMapper implements RowMapper<Location> {
        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            return new Location(rs.getInt("locationId"), rs.getString("name"), 
                    rs.getString("description"), 
                    rs.getString("streetNumber"),
                    rs.getString("streetName"), rs.getString("city"),
                    rs.getString("state"), rs.getString("zipcode"),
                    rs.getDouble("latitude"), rs.getDouble("longitude"));
        }
    }
}
