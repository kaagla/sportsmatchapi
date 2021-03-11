CREATE TABLE IF NOT EXISTS locations(id varchar primary key,address varchar,
    postalcode varchar,postoffice varchar, municipality varchar,grandarea varchar,
    lat float, lon float);

CREATE TABLE IF NOT EXISTS venues(name varchar, id varchar primary key,
    location_id varchar references locations(id));

CREATE TABLE IF NOT EXISTS leagues(name varchar, sport varchar, id varchar primary key);

CREATE TABLE IF NOT EXISTS clubs(name varchar, sport varchar, id varchar primary key);

CREATE TABLE IF NOT EXISTS teams(league_id varchar references leagues(id), name varchar,
    id varchar primary key, club_id varchar references clubs(id));

CREATE TABLE IF NOT EXISTS matches(
    id varchar primary key,date varchar,
    start_date date,
    end_date date,
    time varchar,
    venue_id varchar references venues(id),
    league_id varchar references leagues(id),
    hometeam_id varchar references teams(id),
    awayteam_id varchar references teams(id),
    score varchar);

CREATE OR REPLACE VIEW team_view AS
SELECT t.id, t.name, t.club_id, c.name AS club, t.league_id, l.name AS league, l.sport
FROM teams as t
LEFT JOIN clubs as c ON t.club_id = c.id
LEFT JOIN leagues as l ON t.league_id = l.id;

CREATE OR REPLACE VIEW venue_view AS
SELECT v.id, v.name, v.location_id, loc.postoffice, loc.municipality
FROM venues as v
LEFT JOIN locations as loc ON v.location_id = loc.id;

CREATE VIEW match_view AS
SELECT m.id, m.date, m.start_date, m.time, v.id AS venue_id, v.name AS venue, v.location_id, m.league_id, l.name AS league, l.sport, m.hometeam_id, h.name AS hometeam, m.awayteam_id, h.club_id AS homeclub_id, a.name AS awayteam, a.club_id AS awayclub_id, m.score, loc.grandarea, loc.municipality, loc.postoffice
FROM matches as m
LEFT JOIN leagues as l ON m.league_id = l.id
LEFT JOIN teams as h ON m.hometeam_id = h.id
LEFT JOIN teams as a ON m.awayteam_id = a.id
LEFT JOIN venues as v ON m.venue_id = v.id
LEFT JOIN locations as loc ON v.location_id = loc.id