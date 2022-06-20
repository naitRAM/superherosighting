INSERT INTO Location (`name`, `description`, streetNumber, streetName, city, state, zipcode, latitude, longitude)
VALUES 
("Hero Park", 
"Central Park of Hero City. Over 2 square miles and home to hundreds of animals and plant species",
"1000",
"Park Avenue",
"Hero City",
"Hero State",
"13924",
30.2241918,
-70.9027214
), 
("Hero Docks",
"Hero City ship dock where commercial sea exports and imports happen. Houses thousands of shipping cointainers",
"9",
"Coast Avenue",
"Hero City",
"Hero State",
"13888",
29.3158501,
-69.8004975
);

INSERT INTO Location (`name`, `description`, streetNumber, streetName, city, state, zipcode)  VALUES 
("Evil Corp",
"An organization run by masterminds bent on world destruction",
"1344",
"Evil Drive",
"Hero City",
"Hero State",
"13666"),
("The Syndicate",
"A powerful and sinister organization intent on assassinating all good super people",
"99",
"Dagger Lane",
"Hero City",
"Hero State", 
"13666"),
("The Good Guys",
"small cohort of 'talented' individuals helping the helpless and fighting crime/corruption",
"88",
"Clover Road",
"Hero City",
"Hero State",
"13888"),
("Heroes For Justice",
"A NGO formed to protect heroes and all others from the wrath of Evil Corp",
"1",
"Justice Way",
"Hero City",
"Hero State",
"13924");

INSERT INTO `Organization` (phone, email, locationId) VALUES 
("6666666666", "evilcorp@evil.evil", (SELECT locationId FROM Location WHERE `name` like "Evil Corp")),
("6664444444", "syndicate@synd.org", (SELECT locationId FROM Location WHERE `name` like "The Syndicate")),
("8881234567", "goodguys@good.heroes", (SELECT locationId FROM Location WHERE `name` like "The Good Guys")),
("8889876543", "heroes@justice.com", (SELECT locationId FROM Location WHERE `name` like "Heroes For Justice"));

-- test DELETE method for OrganizationDaoDB
-- DELETE FROM location WHERE locationId NOT IN (SELECT locationId FROM `Organization`) AND locationId = 5;

INSERT INTO Hero (`name`, `description`, superPower) VALUES
("Barbee",
"An evil beauty, her hair made of blonde barbs used to snare her victims",
"Shoots barbed wire"
),
("Checkers",
"An aging magician with a good heart and cheeky smile, known for his checkered clothes",
"Apparition"
),
("Stoneman",
"Construction worker by day, crime-fighter by night, can turn his arms into stone",
"Morphs into concrete"
),
("Teapot", 
"A Sumo wrestler with a dark past and tea-stained teeth. Former Yakuza",
"Immense weight and strength"
);

INSERT INTO HeroOrganization (heroId, organizationId) VALUES 
(1, 2),
(1, 1),
(2, 3),
(2, 4), 
(3, 3),
(4, 2);

INSERT INTO Sighting (locationId, sightingDate) VALUES 
(1, "2022-06-11"),
(2, "2022-06-10"),
(1, "2021-02-04"),
(1, "2020-09-29");

INSERT INTO SightingHero (heroId, sightingId) VALUES 
(1, 1),
(2, 1), 
(2, 2), 
(3, 2), 
(4, 2),
(1, 3), 
(1, 4);




