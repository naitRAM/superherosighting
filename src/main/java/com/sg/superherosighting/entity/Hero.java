package com.sg.superherosighting.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Jun. 13, 2022
 * purpose: 
 */
public class Hero {
    int heroId;
    String name;
    String description;
    List<SuperPower> superPowers = new ArrayList<>();;
    List<Location> locations = new ArrayList<>();
    List<Organization> organizations = new ArrayList<>();

    public Hero (int heroId, String name, String description) {
        this.heroId = heroId;
        this.name = name;
        this.description = description;
    }
    
    public Hero () {
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SuperPower> getSuperPowers() {
        return superPowers;
    }

    public void setSuperPowers(List<SuperPower> superPowers) {
        this.superPowers = superPowers;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.heroId;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.superPowers);
        hash = 29 * hash + Objects.hashCode(this.locations);
        hash = 29 * hash + Objects.hashCode(this.organizations);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hero other = (Hero) obj;
        if (this.heroId != other.heroId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.superPowers, other.superPowers)) {
            return false;
        }
        if (!Objects.equals(this.locations, other.locations)) {
            return false;
        }
        if (!Objects.equals(this.organizations, other.organizations)) {
            return false;
        }
        return true;
    }

    
}
