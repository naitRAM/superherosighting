package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.HeroDaoDB.HeroMapper;
import com.sg.superherosighting.dao.LocationDaoDB.LocationMapper;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Location;
import com.sg.superherosighting.entity.Sighting;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jun. 13, 2022 purpose:
 */
@Repository
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {
        String query = "SELECT sightingId, sightingDate FROM Sighting WHERE sightingId "
                + "= ?";
        Sighting sighting = jdbc.queryForObject(query, new SightingMapper(), id);
        sighting.setHeroesSighted(getSightingHeroes(sighting));
        sighting.setLocation(getSightingLocation(sighting));
        return sighting;
    }

    @Override
    public List<Sighting> getAllSightings() {
        String query = "SELECT sightingId, sightingDate FROM Sighting";
        List<Sighting> sightings = jdbc.query(query, new SightingMapper());
        associateHeroesToSighting(sightings);
        associateLocationToSighting(sightings);
        return sightings;
    }
    
    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        Date sqlDate = Date.valueOf(date);
        String query = "SELECT sightingId, sightingDate FROM Sighting WHERE sightingDate = ?";
        List<Sighting> sightings = jdbc.query(query, new SightingMapper(), sqlDate);
        associateHeroesToSighting(sightings);
        associateLocationToSighting(sightings);
        return sightings;
    }

    private List<Hero> getSightingHeroes(Sighting sighting) {
        String query = "SELECT * FROM Hero JOIN SightingHero ON Hero.heroId = "
                + "SightingHero.heroId WHERE sightingId = ?";
        List<Hero> heroes = jdbc.query(query, new HeroMapper(), sighting.getSightingId());
        return heroes;
    }
    
    private Location getSightingLocation(Sighting sighting) {
        String query = "SELECT * FROM Location JOIN Sighting ON "
                + "Location.LocationId = Sighting.LocationId WHERE "
                + "Sighting.SightingId = ? AND Location.LocationId NOT IN "
                + "(SELECT locationId FROM `Organization`)";
        try {
            Location location = jdbc.queryForObject(query, new LocationMapper(), sighting.getSightingId());
            return location;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
     private void associateHeroesToSighting(List<Sighting> sightings) {
         sightings.forEach(sighting -> {
             sighting.setHeroesSighted(getSightingHeroes(sighting));
        });
    }
    
    private void associateLocationToSighting(List<Sighting> sightings) {
        sightings.forEach(sighting -> {
            sighting.setLocation(getSightingLocation(sighting));
        });
    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        String insertStmt = "INSERT INTO Sighting (sightingDate, locationId) VALUES (?, ?)";
        jdbc.update(insertStmt, Date.valueOf(sighting.getDate()), sighting.getLocation().getLocationId());
        int id = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingId(id);
        insertSightingHeroes(sighting);
        //sighting.setLocation(getSightingLocation(sighting));
        return sighting;
    }
    
    private void insertSightingHeroes(Sighting sighting) {
        String insertStmt = "INSERT INTO SightingHero (sightingId, heroId) VALUES (?, ?)";
        List<Hero> heroes = sighting.getHeroesSighted();
        for (Hero hero : heroes) {
            jdbc.update(insertStmt, sighting.getSightingId(), hero.getHeroId());
        }
    }

    @Override
    public void updateSighting(Sighting sighting) {
        String updateStmt = "UPDATE Sighting SET sightingDate = ?, "
                + "locationId = ? WHERE sightingId = ?";
        jdbc.update(updateStmt, Date.valueOf(sighting.getDate()), 
                sighting.getLocation().getLocationId(), 
                sighting.getSightingId());
        jdbc.update("DELETE FROM SightingHero WHERE "
                + "sightingId = ?", sighting.getSightingId());
        insertSightingHeroes(sighting);
    }

    @Override
    public void deleteSighting(Sighting sighting) {
        String deleteSightingHeroStmt = "DELETE FROM SightingHero"
                + " WHERE sightingId = ?";
        String deleteSightingStmt = "DELETE FROM Sighting WHERE "
                + "sightingId = ?";
        jdbc.update(deleteSightingHeroStmt, sighting.getSightingId());
        jdbc.update(deleteSightingStmt, sighting.getSightingId());
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting(rs.getInt("sightingId"),
                    rs.getDate("sightingDate").toLocalDate());
            return sighting;
        }
    }
}
