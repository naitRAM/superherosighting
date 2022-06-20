package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.HeroDaoDB.HeroMapper;
import com.sg.superherosighting.entity.Hero;
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
public class OrganizationDaoDB implements OrganizationDao {
    
    @Autowired
    JdbcTemplate jdbc;
    
    @Override
    public Organization getOrganizationById(int id) {
        String query = "SELECT * FROM `Organization` JOIN Location ON "
                + "`Organization`.locationId = Location.locationId "
                + "WHERE organizationId = ?";
        Organization organization = jdbc.queryForObject(query, 
                new OrganizationMapper(), id);
        organization.setMembers(getHeroesForOrganization(organization));
        return organization;
    }
    
    private List<Hero> getHeroesForOrganization(Organization organization) {
        String query = "SELECT * FROM Hero JOIN HeroOrganization ON Hero.heroId "
                + "= HeroOrganization.heroId JOIN `Organization` ON "
                + "HeroOrganization.organizationId = "
                + "`Organization`.organizationId WHERE "
                + "`Organization`.organizationId = ?";
        List<Hero> heroes = jdbc.query(query, new HeroMapper(), 
                organization.getOrganizationId());
        return heroes;
    }
    
    private void associateHeroesToOrganization(List<Organization> organizations) {
        for (Organization organization : organizations) {
            organization.setMembers(getHeroesForOrganization(organization));
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        String query = "SELECT * FROM `Organization` JOIN Location ON"
                + "`Organization`.locationId = Location.locationId";
        List<Organization> organizations = jdbc.query(query, 
                new OrganizationMapper());
        associateHeroesToOrganization(organizations);
        return organizations;
    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        String insertStmt = "INSERT INTO `Organization` (email, phone, locationId) VALUES "
                + "(?, ?, ?)";
        String insertLocationInfo = "INSERT INTO Location(name, description, "
                + "streetNumber, streetName, city, state, zipcode) VALUES "
                + "(?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbc.update(insertLocationInfo, 
                    organization.getName(), organization.getDescription(), 
                    organization.getStreetNumber(), organization.getStreetName(), 
                    organization.getCity(), organization.getState(), 
                    organization.getZipcode());
            int locationId = jdbc.queryForObject("SELECT LAST_INSERT_ID()",
                    Integer.class);
            jdbc.update(insertStmt, organization.getEmail(), 
                    organization.getPhone(), locationId);
            int organizationId = jdbc.queryForObject("SELECT LAST_INSERT_ID()",
                    Integer.class);
            organization.setOrganizationId(organizationId);
            insertOrganizationHeroes(organization);
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }
    
    private void insertOrganizationHeroes (Organization organization) {
        String insertStmt = "INSERT INTO HeroOrganization (heroId, organizationId)"
                + " VALUES (?, ?)";
        for (Hero hero : organization.getMembers()) {
            jdbc.update(insertStmt, hero.getHeroId(), 
                    organization.getOrganizationId());
        }
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        String updateLocationStmt = "UPDATE Location SET name = ?, description "
                + "= ?, streetNumber = ?, streetName = ?, city = ?, state = ?, "
                + "zipcode = ? WHERE locationId = "
                + "(SELECT Organization.locationId FROM `Organization` WHERE "
                + "organizationId = ?)";
        jdbc.update(updateLocationStmt, organization.getName(), 
                organization.getDescription(), organization.getStreetNumber(),
                organization.getStreetName(), organization.getCity(), 
                organization.getState(), organization.getZipcode(), 
                organization.getOrganizationId());
        String updateOrganizationStmt = "UPDATE `Organization` SET phone = ?, "
                + "email = ? WHERE organizationId = ?";
        jdbc.update(updateOrganizationStmt, organization.getPhone(), 
                organization.getEmail(), organization.getOrganizationId());
        String deleteOrganizationHeroesStmt = "DELETE FROM HeroOrganization "
                + "WHERE organizationId = ?";
        jdbc.update(deleteOrganizationHeroesStmt, 
                organization.getOrganizationId());
        insertOrganizationHeroes(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(Organization organization) {
        
        String deleteOrganizationHeroesStmt = "DELETE FROM HeroOrganization "
                + "WHERE organizationId = ?";
        int locationId = jdbc.queryForObject("SELECT locationId FROM "
                + "`Organization` WHERE organizationId = ?", Integer.class, 
                organization.getOrganizationId());
        String deleteOrganizationStmt = "DELETE FROM `Organization` WHERE "
                + "organizationId = ?";
        String deleteOrganizationLocationStmt = "DELETE FROM Location WHERE "
                + "locationId = ?";
        jdbc.update(deleteOrganizationHeroesStmt, 
                organization.getOrganizationId());
        jdbc.update(deleteOrganizationStmt, 
                organization.getOrganizationId());
        jdbc.update(deleteOrganizationLocationStmt, locationId);
    }

    public static final class OrganizationMapper implements RowMapper<Organization>  {
        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            return new Organization(rs.getInt("organizationId"), 
                    rs.getString("name"), rs.getString("description"), 
                    rs.getString("streetNumber"),rs.getString("streetName"), 
                    rs.getString("city"), rs.getString("state"), 
                    rs.getString("zipcode"), rs.getString("email"),
                    rs.getString("phone"));
        }
    }
}
