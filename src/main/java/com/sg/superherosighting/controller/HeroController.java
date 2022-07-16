package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entity.Hero;
import com.sg.superherosighting.entity.Organization;
import com.sg.superherosighting.entity.SuperPower;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    SuperPowerDao superPowerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> allHeroes = heroDao.getAllHeroes();
        List<Organization> allOrganizations = organizationDao.getAllOrganizations();
        List<SuperPower> allSuperPowers = superPowerDao.getAllSuperPowers();
        model.addAttribute("superPowers", allSuperPowers);
        model.addAttribute("organizations", allOrganizations);
        model.addAttribute("heroes", allHeroes);
        return "heroes";
    }

    @PostMapping("addHero")
    public String addHero(Hero hero, HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        String[] superPowerIds = request.getParameterValues("superPowerIds");
        String[] organizationIds = request.getParameterValues("organizationIds");

        List<Organization> heroOrganizations = new ArrayList<>();
        List<SuperPower> heroSuperPowers = new ArrayList<>();
        if (superPowerIds != null) {
            for (String superPowerId : superPowerIds) {
                heroSuperPowers.add(superPowerDao.getSuperPowerById(Integer.parseInt(superPowerId)));
            }
        }
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                heroOrganizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        }
        hero.setOrganizations(heroOrganizations);
        hero.setSuperPowers(heroSuperPowers);
        hero = heroDao.addHero(hero);
        String fileName = "images/" + "hero_" + hero.getHeroId() + ".jpeg";
        File diskFile = new File(fileName);
        Files.copy(file.getInputStream(), Paths.get(fileName), REPLACE_EXISTING);
        return "redirect:/heroes";
    }
    
    @GetMapping(value = "heroImage/{heroId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void sendImage(@PathVariable Integer heroId, HttpServletResponse response) throws FileNotFoundException, IOException {
        InputStream image = new FileInputStream("images/" + "hero_" + heroId + ".jpeg");
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(image, response.getOutputStream());
        
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        List<SuperPower> allSuperPowers = superPowerDao.getAllSuperPowers();
        List<Organization> allOrganizations = organizationDao.getAllOrganizations();
        for (Organization organization : allOrganizations) {
            organization.setMembers(new ArrayList<>());
        }
        model.addAttribute("superPowers", allSuperPowers);
        model.addAttribute("organizations", allOrganizations);
        model.addAttribute("hero", hero);
        return "editHero";
    }
    
    @PostMapping("editHero")
    public String updateHero(Hero hero, HttpServletRequest request) {
        String[] superPowerIds = request.getParameterValues("superPowerIds");
        String[] organizationIds = request.getParameterValues("organizationIds");

        List<Organization> heroOrganizations = new ArrayList<>();
        List<SuperPower> heroSuperPowers = new ArrayList<>();
        if (superPowerIds != null) {
            for (String superPowerId : superPowerIds) {
                heroSuperPowers.add(superPowerDao.getSuperPowerById(Integer.parseInt(superPowerId)));
            }
        }
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                heroOrganizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        }
        hero.setOrganizations(heroOrganizations);
        hero.setSuperPowers(heroSuperPowers);
        heroDao.updateHero(hero);
        return "redirect:/heroes";
    }
    
    @GetMapping("deleteHero")
    public String deleteHero(Integer id) {
        Hero hero = heroDao.getHeroById(id);
        heroDao.deleteHero(hero);
        return "redirect:/heroes";
    }
}
