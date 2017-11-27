/*
  su postgres
  psql -f kitchen.sql  
*/

DROP DATABASE IF EXISTS kitchen;
CREATE DATABASE kitchen;

\c kitchen;

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name VARCHAR,
  surname VARCHAR,
  age INTEGER,
  sex VARCHAR
);

CREATE TABLE dishes (
  id SERIAL PRIMARY KEY,
  title VARCHAR,
  description VARCHAR,
  image VARCHAR,
  userId SERIAL,
  FOREIGN KEY (userId) REFERENCES users (id)
);

INSERT INTO users (name, surname, age, sex) VALUES 
  ('Edgar', 'Hakobyan', 23, 'male'),
  ('Smbat', 'Sargsyan', 25, 'male');

INSERT INTO dishes (title, description, image, userId) VALUES 
  ('Xorovac', 'shat lava', 'xorovac.png', 1),
  ('Xash', 'kutvi', 'xash.png', 2);
