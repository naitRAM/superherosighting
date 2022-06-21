## Requirements  
With the rising popularity of superhero movies, there has been a heightened awareness of superheroes in our midst.  
 The frequency of superhero (and supervillain) sightings is increasing at an alarming rate. Given this development,  
 The Hero Education and Relationship Organization (HERO) has asked our company to develop a database and data layer  
 for their new superhero sightings web application.  
  
The system has the following requirements:  
  
It must keep track of all superhero/supervillain information.  
Heroes have names, descriptions, and a superpower.  
Heroes are affiliated with one or more superhero/supervillain organizations.  
It must keep track of all location information:  
Locations have names, descriptions, address information, and latitude/longitude coordinates.  
It must keep track of all superhero/supervillain organization information:  
Organizations have names, descriptions, and address/contact information.  
Organizations have members.  
A user must be able to record a superhero/supervillain sighting for a particular location and date.  
The system must be able to report all of the superheroes sighted at a particular location.  
The system must be able to report all of the locations where a particular superhero has been seen.  
The system must be able to report all sightings (hero and location) for a particular date.  
The system must be able to report all of the members of a particular organization.  
The system must be able to report all of the organizations a particular superhero/villain belongs to.  
  
## Deliverables  
To complete this assignment, you must deliver the following items:  
  
### An Entity-Relationship-Diagram  
You may use MySQL Workbench to create a diagram or your choice of alternative tools such as Pencil, Draw.IO, or LucidChart.  
The database must achieve 2nd normal form at minimum.  
Proper naming conventions should be used.  
The ERD should be very easy to read, with all components clearly labeled.  
Use a common and appropriate file format, such as a png or jpg image or a PDF document.  
  
### A database creation script  
The script should create the database with all tables, columns, and relationships.  
Make reasonable assumptions about column data types; be prepared to justify your decisions.  
The script should be re-runnable. This means it should drop the database and all objects if they exist and recreate them.  
You should be able to execute the scriptmany times in a row without error. See the scripts provided for the databases used   
in the Relational Database unit for examples.  
  
### DAO Implementation and Unit Tests  
DAO should have an interface and an implementation.  
DAO and DTOs must fully represent all data and relationships contained in the database design.  
Implementation must make proper use of transactions.  
Unit tests must fully test all create, read, update, and delete functionality for all entities and test all many-to-many and  
one-to-many relationships in the database.  
   
   
![Enity Relationship Diagram for superherosighting database](https://github.com/naitRAM/superherosighting/blob/main/sql_scripts/superhero_sightings_ERD.jpg?raw=true)
  
***
  
## NOTES   
  
- Each Organization created in this DB stores most of it's information in the Location table. Because an Organization contains     
name, description, and address info it is semantically a location, and shares alot of column names with Location. A Location  
entry is created for every Organization entry in the database, which is referenced by a foreign key in the Organization entry.  
But an Organization must also be a separate entity in the application, not related to Location objects. It cannot be retrieved,   
added or edited from the Location data access object, and vice versa. This complicates the sql statements and dao methods used   
for the database implementations of the Location and Organization data access objects. Also, more transactions have to occur  
for certain methods to work. For example:  
  
``` 
    @Override
    @Transactional  
    public void deleteOrganization(Organization organization) {

        String deleteOrganizationHeroesStmt = "DELETE FROM HeroOrganization "
                + "WHERE organizationId = ?";

        int locationId = jdbc.queryForObject("SELECT locationId FROM "
                + "`Organization` WHERE organizationId = ?", Integer.class,
                organization.getOrganizationId());

        String deleteOrganizationStmt = "DELETE FROM `Organization` WHERE "
                + "organizationId = ?";

        String deleteOrganizationLocationStmt = "DELETE FROM Location WHERE "
                + "locationId = ?";

        jdbc.update(deleteOrganizationHeroesStmt,
                organization.getOrganizationId());

        jdbc.update(deleteOrganizationStmt,
                organization.getOrganizationId());

        jdbc.update(deleteOrganizationLocationStmt, locationId);
    }      
``` 



