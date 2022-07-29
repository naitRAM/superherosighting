package com.sg.superherosighting.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Jun. 13, 2022
 * purpose: 
 */
public class Location {
    int locationId;
    
    @NotBlank(message = "name must not be blank")
    @Size(max = 25, message = "name must be less than 25 characters")
    String name;
    
    @NotBlank(message = "description must not be blank")
    @Size(max = 250, message = "description must be less than 250 characters")
    String description;
    
    @NotBlank(message = "street number must not be blank")
    @Size(max = 10, message = "street number must be less than 10 characters")
    String streetNumber;
    
    @NotBlank(message = "street name must not be blank")
    @Size(max = 25, message = "street name must be less than 25 characters")
    String streetName;
    
    @NotBlank(message = "city must not be blank")
    @Size(max = 25, message = "city must be less than 25 characters")
    String city;
    
    @NotBlank(message = "state must not be blank")
    @Size(max = 25, message = "state must be less than 25 characters")
    String state;
    
    @NotBlank(message = "zipcode must not be blank")
    @Size(max =6, message = "zipcode must be 6 characters or less")
    String zipcode;
    
    @NotNull(message = "must supply a valid latitude")
    @Min(value = -90, message = "latitude must be -90 or greater")
    @Max(value = 90, message = "longitude must be 90 or less")
    Double latitude;
    
    @NotNull(message = "must supply a valid longitude")
    @Min(value = -180, message = "longitude must be -180 or greater")
    @Max(value = 180, message = "longitude must be 180 or less")
    Double longitude;
    
    List<Hero> sightedHeroes = new ArrayList<>();

    public Location (int locationId, String name, String description, String streetNumber,
            String streetName, String city, String state, String zipcode, Double latitude, 
            Double longitude) {
        this.locationId = locationId;
        this.name = name;
        this.description = description;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Location (String name, String description, String streetNumber,
            String streetName, String city, String state, String zipcode, Double latitude, 
            Double longitude) {
        this.name = name;
        this.description = description;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Location () {
        
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

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Hero> getSightedHeroes() {
        return sightedHeroes;
    }

    public void setSightedHeroes(List<Hero> sightedHeroes) {
        this.sightedHeroes = sightedHeroes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.locationId;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.description);
        hash = 23 * hash + Objects.hashCode(this.streetNumber);
        hash = 23 * hash + Objects.hashCode(this.streetName);
        hash = 23 * hash + Objects.hashCode(this.city);
        hash = 23 * hash + Objects.hashCode(this.state);
        hash = 23 * hash + Objects.hashCode(this.zipcode);
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 23 * hash + Objects.hashCode(this.sightedHeroes);
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
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.streetNumber, other.streetNumber)) {
            return false;
        }
        if (!Objects.equals(this.streetName, other.streetName)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zipcode, other.zipcode)) {
            return false;
        }
        if (!Objects.equals(this.sightedHeroes, other.sightedHeroes)) {
            return false;
        }
        return true;
    }

   
    

    

   
    
}
