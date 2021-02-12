INSERT INTO leagues (id,name,sport) VALUES ('l-1','league1','Jalkapallo');
INSERT INTO leagues (id,name,sport) VALUES ('l-2','league2','Koripallo');

INSERT INTO teams (id,name,league_id) VALUES ('t-1','team1','l-1');
INSERT INTO teams (id,name,league_id) VALUES ('t-2','team2','l-1');
INSERT INTO teams (id,name,league_id) VALUES ('t-3','team3','l-1');

INSERT INTO teams (id,name,league_id) VALUES ('t-4','team4','l-2');
INSERT INTO teams (id,name,league_id) VALUES ('t-5','team5','l-2');
INSERT INTO teams (id,name,league_id) VALUES ('t-6','team6','l-2');

INSERT INTO locations (id,address,postalcode,postoffice,municipality,grandarea,sport,lat, lon)
VALUES ('loc-1','address1','00100','Helsinki','Helsinki','Helsinki-Uusimaa','Jalkapallo',60.01,23.03);
INSERT INTO locations (id,address,postalcode,postoffice,municipality,grandarea,sport,lat, lon)
VALUES ('loc-2','address2','02120','Espoo','Espoo','Helsinki-Uusimaa','Jalkapallo',61.21,23.53);
INSERT INTO locations (id,address,postalcode,postoffice,municipality,grandarea,sport,lat, lon)
VALUES ('loc-3','address3','95200','Simo','Simo','Pohjois- ja Itä-Suomi','Koripallo',62.01,24.03);

INSERT INTO matches (id,date,start_date,end_date,time,venue, location_id,league_id,hometeam_id,awayteam_id,score)
VALUES ('m-1','1.3.2021','2021-03-01','2021-03-01','18:00','Venue1','loc-1','l-1','t-1','t-2','-');

INSERT INTO matches (id,date,start_date,end_date,time,venue, location_id,league_id,hometeam_id,awayteam_id,score)
VALUES ('m-2','2.3.2021','2021-03-02','2021-03-02','19:00','Venue2','loc-2','l-1','t-2','t-3','-');

INSERT INTO matches (id,date,start_date,end_date,time,venue, location_id,league_id,hometeam_id,awayteam_id,score)
VALUES ('m-3','4.3.2021','2021-03-04','2021-03-04','20:00','Venue3','loc-3','l-2','t-4','t-5','-');