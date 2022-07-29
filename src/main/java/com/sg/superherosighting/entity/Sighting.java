package com.sg.superherosighting.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Jun. 13, 2022
 * purpose: 
 */
public class Sighting {
    int sightingId;
    @NotNull(message = "Location was not selected")
    Location location;
    @Past(message = "Date must be in the past")
    LocalDate date;
    @Size(min = 1, message = "At least 1 hero must be selected")
    List<Hero> heroesSighted = new ArrayList<>();
    
    public Sighting (int sightingId, LocalDate date) {
        this.sightingId = sightingId;
        this.date = date;
    }
    
    public Sighting() {
        
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    public List<Hero> getHeroesSighted() {
        return heroesSighted;
    }

    public void setHeroesSighted(List<Hero> heroesSighted) {
        this.heroesSighted = heroesSighted;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.sightingId;
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + Objects.hashCode(this.heroesSighted);
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
        final Sighting other = (Sighting) obj;
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.heroesSighted, other.heroesSighted)) {
            return false;
        }
        return true;
    }

    
 
}
