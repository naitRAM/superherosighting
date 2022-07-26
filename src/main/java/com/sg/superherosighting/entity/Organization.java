package com.sg.superherosighting.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author Rami Mansieh
 * email: rmansieh@gmail.com
 * data: Jun. 13, 2022
 * purpose: 
 */
public class Organization {
    int organizationId;
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
    @NotBlank(message = "email must not be blank")
    @Size(max = 25, message = "email must be less than 25 characters")
    String email;
    @NotBlank(message = "phone must not be blank")
    @Size(max =10, message = "phone must be 10 characters or less")
    String phone;
    List<Hero> members = new ArrayList<>();
    
    public Organization (int organizationId, String name, String description, 
            String streetNumber, String streetName, String city, String state,
            String zipcode, String email, String phone) {
        this.organizationId = organizationId;
        this.name = name;
        this.description = description;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.email = email;
        this.phone = phone;
    }
    
    public Organization() {
        
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public List<Hero> getMembers() {
        return members;
    }

    public void setMembers(List<Hero> members) {
        this.members = members;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.organizationId;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.streetNumber);
        hash = 29 * hash + Objects.hashCode(this.streetName);
        hash = 29 * hash + Objects.hashCode(this.city);
        hash = 29 * hash + Objects.hashCode(this.state);
        hash = 29 * hash + Objects.hashCode(this.zipcode);
        hash = 29 * hash + Objects.hashCode(this.email);
        hash = 29 * hash + Objects.hashCode(this.phone);
        hash = 29 * hash + Objects.hashCode(this.members);
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
        final Organization other = (Organization) obj;
        if (this.organizationId != other.organizationId) {
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
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.members, other.members)) {
            return false;
        }
        return true;
    }

    

   

    
}
