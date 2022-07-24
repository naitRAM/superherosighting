package com.sg.superherosighting.controller;

import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Organization;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 9, 2022 purpose:
 */
@Controller
public class OrganizationController {

    @Autowired
    SuperHeroSightingServiceLayer service;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> allOrganizations = service.getAllOrganizations();
        List<Hero> allHeroes = service.getAllHeroes();
        model.addAttribute("organizations", allOrganizations);
        model.addAttribute("heroes", allHeroes);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {
        String[] organizationMemberIds = request.getParameterValues("heroIds");
        service.addOrganization(organization, organizationMemberIds);
        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        service.deleteOrganization(id);
        return "redirect:/organizations";

    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = service.getOrganizationById(id);
        List<Hero> allHeroes = service.getAllHeroes();
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
        service.updateOrganization(organization, organizationMemberIds);
        return "redirect:/organizations";
    }
    
    @GetMapping("organizationDetail")
    public String displayOrganization(Model model, Integer id) {
        Organization organization = service.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }
}
