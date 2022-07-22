package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Organization;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 9, 2022 purpose:
 */
@Controller
public class OrganizationController {

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> allOrganizations = organizationDao.getAllOrganizations();
        List<Hero> allHeroes = heroDao.getAllHeroes();
        model.addAttribute("organizations", allOrganizations);
        model.addAttribute("heroes", allHeroes);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String[] organizationMemberIds = request.getParameterValues("heroIds");
        List<Hero> organizationMembers = new ArrayList<>();
        if (organizationMemberIds != null) {
            for (String memberId : organizationMemberIds) {
                organizationMembers.add(heroDao.getHeroById(Integer.parseInt(memberId)));
            }
        }
        organization.setMembers(organizationMembers);
        organizationDao.addOrganization(organization);
        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        Organization organization = organizationDao.getOrganizationById(id);
        organizationDao.deleteOrganization(organization);
        return "redirect:/organizations";

    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);
        List<Hero> allHeroes = heroDao.getAllHeroes();
        for (Hero hero : allHeroes) {
            hero.setLocations(new ArrayList<>());
            hero.setOrganizations(new ArrayList<>());
            hero.setSuperPowers(new ArrayList<>());
        }
        model.addAttribute("organization", organization);
        model.addAttribute("heroes", allHeroes);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String updateOrganization(Organization organization, HttpServletRequest request) {
        String[] organizationMemberIds = request.getParameterValues("heroIds");
        List<Hero> organizationMembers = new ArrayList<>();
        if (organizationMemberIds != null) {
            for (String memberId : organizationMemberIds) {
                organizationMembers.add(heroDao.getHeroById(Integer.parseInt(memberId)));
            }
        }
        organization.setMembers(organizationMembers);
        organizationDao.updateOrganization(organization);
        return "redirect:/organizations";
    }
    
    @GetMapping("organizationDetail")
    public String displayOrganization(Model model, Integer id) {
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }
}
