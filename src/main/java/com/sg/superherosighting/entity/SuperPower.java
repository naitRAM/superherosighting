package com.sg.superherosighting.entity;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Jul. 8, 2022
 * purpose: 
 */
public class SuperPower {
    
    int superPowerId;
    
    @NotBlank(message = "name must not be blank")
    @Size(max = 25, message = "name must be less than 25 characters")
    String name;
    
    public SuperPower(int superPowerId, String name) {
        this.superPowerId = superPowerId;
        this.name = name;
        
    }
    
    public SuperPower(){
        
    }

    public int getSuperPowerId() {
        return superPowerId;
    }

    public void setSuperPowerId(int superPowerId) {
        this.superPowerId = superPowerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.superPowerId;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final SuperPower other = (SuperPower) obj;
        if (this.superPowerId != other.superPowerId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
}
