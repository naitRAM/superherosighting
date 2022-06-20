/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entity.Hero;
import java.util.List;

/**
 *
 * @author rmans
 */
public interface HeroDao {
    public Hero getHeroById(int id);
    public List<Hero> getAllHeroes();
    public Hero addHero(Hero hero);
    public void updateHero(Hero hero);
    public void deleteHero(Hero hero);
    
    
}
