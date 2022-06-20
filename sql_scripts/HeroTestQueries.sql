-- get all heroes spotted in a sighting
SELECT 
    *
FROM
    Hero AS h
        JOIN
    SightingHero AS sh ON h.heroId = sh.heroId
        JOIN
    Sighting AS s ON sh.sightingId = s.sightingId
        JOIN
    Location AS l ON l.locationId = s.locationId
WHERE
    sh.sightingId = 2;

-- get all heroSightings  for a location
SELECT 
    *
FROM
    Location
        JOIN
    Sighting ON Sighting.LocationId = Location.LocationId
        JOIN
    SightingHero ON SightingHero.SightingId = Sighting.SightingId
WHERE
    Location.LocationId = 1;

-- remove sightingHeroes for a location 
DELETE SightingHero . * FROM SightingHero
        JOIN
    Sighting ON SightingHero.SightingId = Sighting.SightingId
        JOIN
    Location ON Sighting.LocationId = Location.LocationId 
WHERE
    Location.LocationId = 1;

-- remove all sightings for a location
DELETE FROM Sighting 
WHERE
    Sighting.LocationId = 1;

-- get all heroes spotted at a specific location
SELECT 
    Hero.*
FROM
    Hero
        JOIN
    SightingHero ON Hero.HeroId = SightingHero.HeroId
        JOIN
    Sighting ON SightingHero.SightingId = Sighting.SightingId
        JOIN
    Location ON Sighting.LocationId = Location.LocationId
WHERE
    Location.LocationId = 2
GROUP BY Hero.HeroId;

-- get all organization data for a hero (most of an organization's data is stored in a location entry referenced by a mandatory foreign key in organization)
SELECT 
    Organization.*, Location.*
FROM
    `Organization`
        JOIN
    Location ON Location.LocationId = Organization.LocationId
        JOIN
    HeroOrganization ON `Organization`.OrganizationId = HeroOrganization.OrganizationId
        JOIN
    Hero ON HeroOrganization.HeroId = Hero.HeroId
WHERE
    Hero.HeroId = 1;

-- get all of the locations where a hero has been spotted 
SELECT 
    Location.*
FROM
    Location
        JOIN
    Sighting ON Location.LocationId = Sighting.LocationId
        JOIN
    SightingHero ON Sighting.SightingId = SightingHero.SightingId
        JOIN
    Hero ON Hero.HeroId = SightingHero.HeroId
WHERE
    Hero.HeroId = 2
        && Location.LocationId NOT IN (SELECT 
            LocationId
        FROM
            `Organization`)
GROUP BY Location.LocationId;

-- delete all HeroOrganization entries for a hero
DELETE HeroOrganization . * FROM HeroOrganization 
WHERE
    HeroId = 1;

-- get all heroes in a specific sighting
SELECT 
    *
FROM
    Hero
        JOIN
    SightingHero ON Hero.HeroId = SightingHero.heroId
WHERE
    SightingId = 2;

-- get location data for a sighting
SELECT 
    *
FROM
    Location
        JOIN
    Sighting ON Location.LocationId = Sighting.LocationId
WHERE
    Sighting.SightingId = 2;

-- get a specific organization's data (a location entry contains most of an organization's data)
SELECT 
    *
FROM
    `Organization`
        JOIN
    Location ON `Organization`.LocationId = Location.LocationId
WHERE
    `OrganizationId` = 1;

-- get all of an organization's heroes 
SELECT 
    *
FROM
    Hero
        JOIN
    HeroOrganization ON Hero.HeroId = HeroOrganization.HeroId
        JOIN
    `Organization` ON HeroOrganization.OrganizationId = `Organization`.OrganizationId
WHERE
    `Organization`.OrganizationId = 2;