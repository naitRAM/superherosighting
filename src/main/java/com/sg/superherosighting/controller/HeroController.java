package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.HeroImageDaoException;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Organization;
import com.sg.superherosighting.entity.SuperPower;
import com.sg.superherosighting.service.SuperHeroSightingServiceLayer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rami Mansieh email: rmansieh@gmail.com data: Jul. 9, 2022 purpose:
 */
@Controller
public class HeroController {

    @Autowired
    SuperHeroSightingServiceLayer service;

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> allHeroes = service.getAllHeroes();
        List<Organization> allOrganizations = service.getAllOrganizations();
        List<SuperPower> allSuperPowers = service.getAllSuperPowers();
        model.addAttribute("superPowers", allSuperPowers);
        model.addAttribute("organizations", allOrganizations);
        model.addAttribute("heroes", allHeroes);
        model.addAttribute("errors", new ArrayList<>());
        return "heroes";
    }

    private Hero buildHeroLists(Hero hero, String[] organizationIds,
            String[] superPowerIds) {
        List<Organization> heroOrganizations = new ArrayList<>();
        List<SuperPower> heroSuperPowers = new ArrayList<>();
        if (superPowerIds != null) {
            for (String superPowerId : superPowerIds) {
                heroSuperPowers.add(service.getSuperPowerById(
                        Integer.parseInt(superPowerId)));
            }
        }
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                heroOrganizations.add(service.getOrganizationById(
                        Integer.parseInt(organizationId)));
            }
        }
        hero.setOrganizations(heroOrganizations);
        hero.setSuperPowers(heroSuperPowers);

        return hero;
    }

    @PostMapping("addHero")
    public String addHero(Hero hero, @RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) throws HeroImageDaoException {

        String[] organizationIds = request.getParameterValues("organizationIds");
        String[] superPowerIds = request.getParameterValues("superPowerIds");
        hero = buildHeroLists(hero, organizationIds, superPowerIds);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        violations = validate.validate(hero);
        if (!violations.isEmpty()) {
            List<Hero> allHeroes = service.getAllHeroes();
            List<Organization> allOrganizations = service.getAllOrganizations();
            List<SuperPower> allSuperPowers = service.getAllSuperPowers();
            model.addAttribute("errorHero", hero);
            model.addAttribute("superPowers", allSuperPowers);
            model.addAttribute("organizations", allOrganizations);
            model.addAttribute("heroes", allHeroes);
            model.addAttribute("errors", violations);
            return "heroes";
        }

        service.addHero(hero, file);

        return "redirect:/heroes";
    }

    @GetMapping("heroImage/{heroId}")
    public void displayImage(@PathVariable Integer heroId, HttpServletResponse response) throws IOException, HeroImageDaoException {
        InputStream image = service.getHeroImage(heroId);
        if (image != null) {
            StreamUtils.copy(image, response.getOutputStream());
            image.close();
        }

    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {
        Hero hero = service.getHeroById(id);
        List<SuperPower> allSuperPowers = service.getAllSuperPowers();
        List<Organization> allOrganizations = service.getAllOrganizations();
        for (Organization organization : allOrganizations) {
            organization.setMembers(new ArrayList<>());
        }
        model.addAttribute("superPowers", allSuperPowers);
        model.addAttribute("organizations", allOrganizations);
        model.addAttribute("hero", hero);
        model.addAttribute("errors", new ArrayList<>());
        return "editHero";
    }

    @PostMapping("editHero")
    public String updateHero(Hero hero, HttpServletRequest request, @RequestParam("file") MultipartFile file, Model model) throws HeroImageDaoException {

        String[] organizationIds = request.getParameterValues("organizationIds");
        String[] superPowerIds = request.getParameterValues("superPowerIds");
        hero = buildHeroLists(hero, organizationIds, superPowerIds);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        violations = validate.validate(hero);
        if (!violations.isEmpty()) {
            List<Hero> allHeroes = service.getAllHeroes();
            List<Organization> allOrganizations = service.getAllOrganizations();
            List<SuperPower> allSuperPowers = service.getAllSuperPowers();
            model.addAttribute("hero", hero);
            model.addAttribute("superPowers", allSuperPowers);
            model.addAttribute("organizations", allOrganizations);
            model.addAttribute("heroes", allHeroes);
            model.addAttribute("errors", violations);
            return "editHero";
        }
        service.updateHero(hero, file);
        return "redirect:/heroes";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer id) {
        service.deleteHero(id);
        return "redirect:/heroes";
    }

    @GetMapping("heroDetail")
    public String displayHero(Integer id, Model model) {
        Hero hero = service.getHeroById(id);
        model.addAttribute("hero", hero);
        return "heroDetail";
    }
}
