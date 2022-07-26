package com.sg.superherosighting.controller;

import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Organization;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
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

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();
    
     private Organization buildOrganizationLists(Organization organization,
            String[] heroIds) {
        List<Hero> organizationMembers = new ArrayList<>();
        if (heroIds != null) {
            for (String memberId : heroIds) {
                organizationMembers.add(service.getHeroById(Integer.parseInt(memberId)));
            }
        }
        organization.setMembers(organizationMembers);
        return organization;
    }

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> allOrganizations = service.getAllOrganizations();
        List<Hero> allHeroes = service.getAllHeroes();
        model.addAttribute("organizations", allOrganizations);
        model.addAttribute("heroes", allHeroes);
        model.addAttribute("errors", new ArrayList<>());
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request, Model model) {
        String[] organizationMemberIds = request.getParameterValues("heroIds");
        organization = buildOrganizationLists(organization, organizationMemberIds);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("organizations", service.getAllOrganizations());
            model.addAttribute("heroes", service.getAllHeroes());
            model.addAttribute("organization", organization);
            return "organizations";
        }
        service.addOrganization(organization);
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
        model.addAttribute("errors", new ArrayList<>());
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String updateOrganization(Organization organization, HttpServletRequest request, Model model) {
        String[] organizationMemberIds = request.getParameterValues("heroIds");
        organization = buildOrganizationLists(organization, organizationMemberIds);
        organization = buildOrganizationLists(organization, organizationMemberIds);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);
        if (!violations.isEmpty()) {
            model.addAttribute("errors", violations);
            model.addAttribute("organizations", service.getAllOrganizations());
            model.addAttribute("heroes", service.getAllHeroes());
            model.addAttribute("organization", organization);
            return "editOrganization";
        }
        service.updateOrganization(organization);
        return "redirect:/organizations";
    }
    
    @GetMapping("organizationDetail")
    public String displayOrganization(Model model, Integer id) {
        Organization organization = service.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }
}
