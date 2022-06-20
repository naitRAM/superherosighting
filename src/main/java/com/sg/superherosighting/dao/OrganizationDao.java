/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.Organization;
import java.util.List;

/**
 *
 * @author rmans
 */
public interface OrganizationDao {
    public Organization getOrganizationById(int id);
    public List<Organization> getAllOrganizations();
    public Organization addOrganization (Organization organization);
    public void updateOrganization(Organization organization);
    public void deleteOrganization(Organization organization);
}
