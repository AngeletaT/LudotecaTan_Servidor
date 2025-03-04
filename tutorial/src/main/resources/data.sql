INSERT INTO category(name) VALUES 
('Eurogames'),
('Ameritrash'),
('Familiar');

INSERT INTO author(name, nationality) VALUES 
('Alan R. Moon', 'US'),
('Vital Lacerda', 'PT'),
('Simone Luciani', 'IT'),
('Perepau Llistosella', 'ES'),
('Michael Kiesling', 'DE'),
('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES 
('On Mars', '14', 1, 2),
('Aventureros al tren', '8', 3, 1),
('1920: Wall Street', '12', 1, 4),
('Barrage', '14', 1, 3),
('Los viajes de Marco Polo', '12', 1, 3),
('Azul', '8', 3, 5);