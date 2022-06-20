DROP DATABASE IF EXISTS HeroDB;
CREATE DATABASE HeroDB;
USE HeroDB;

CREATE TABLE Location (
locationId INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(25) NOT NULL,
`description` VARCHAR(250) NOT NULL,
streetNumber CHAR(10) NOT NULL,
streetName VARCHAR(25) NOT NULL,
city VARCHAR(25) NOT NULL,
state VARCHAR(25) NOT NULL,
zipcode CHAR(5) NOT NULL,
latitude DECIMAL(9,7),
longitude DECIMAL(10,7)
);

CREATE TABLE `Organization` (
organizationId INT PRIMARY KEY AUTO_INCREMENT,
phone CHAR(10),
email VARCHAR(25),
locationId INT NOT NULL,
FOREIGN KEY (locationId) REFERENCES Location(locationId)
);

CREATE TABLE Hero (
heroId INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(25) NOT NULL,
`description` VARCHAR(250) NOT NULL,
superPower VARCHAR(50) NOT NULL
);

CREATE TABLE Sighting (
sightingId INT PRIMARY KEY AUTO_INCREMENT,
sightingDate Date NOT NULL,
locationId INT NOT NULL,
FOREIGN KEY (locationId) REFERENCES Location(locationId)
);

CREATE TABLE HeroOrganization (
heroId INT NOT NULL,
organizationId INT NOT NULL,
PRIMARY KEY (heroId, organizationId),
FOREIGN KEY (heroId) REFERENCES Hero(heroId),
FOREIGN KEY (organizationId) REFERENCES `Organization`(organizationId)
);

CREATE TABLE SightingHero (
heroId INT NOT NULL,
sightingId INT  NOT NULL,
PRIMARY KEY (heroId, sightingId),
FOREIGN KEY (heroId) REFERENCES Hero(heroId),
FOREIGN KEY (sightingId) REFERENCES Sighting(sightingId)
);



