package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.SuperPower;
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
 * data: Jul. 8, 2022
 * purpose: 
 */
@Repository
public class SuperPowerDaoDB implements SuperPowerDao {

    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public SuperPower getSuperPowerById(int id) {
        String query = "SELECT * FROM SuperPower WHERE superPowerId = ?";
        try {
            return jdbc.queryForObject(query, new SuperPowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        String query = "SELECT * FROM SuperPower";
        return jdbc.query(query, new SuperPowerMapper());
    }

    @Override
    @Transactional
    public SuperPower addSuperPower(SuperPower superPower) {
        String insertStmt = "INSERT INTO SuperPower(name) VALUES (?)";
        jdbc.update(insertStmt, superPower.getName());
        int superPowerId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superPower.setSuperPowerId(superPowerId);
        return superPower;
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {
        String updateStmt = "UPDATE SuperPower SET name = ? WHERE superPowerId = ?";
        jdbc.update(updateStmt, superPower.getName(), superPower.getSuperPowerId());
    }

    @Override
    @Transactional
    public void deleteSuperPower(SuperPower superPower) {
        String deleteHeroPowerStmt = "DELETE FROM HeroPower WHERE superPowerId = ?";
        String deleteSuperPowerStmt = "DELETE FROM SuperPower WHERE superPowerId = ?";
        jdbc.update(deleteHeroPowerStmt, superPower.getSuperPowerId());
        jdbc.update(deleteSuperPowerStmt, superPower.getSuperPowerId());
    }
    
   
    
    public static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new SuperPower(rs.getInt("superPowerId"), rs.getString("name"));
        }
    }
}
