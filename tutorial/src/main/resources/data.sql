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

INSERT INTO client(name) VALUES
('Pablo'),
('Jordi'),
('Carla'),
('Salva'),
('Guillermo'),
('Anna'),
('Carlos'),
('Marta'),
('Luis'),
('Laura');

INSERT INTO loan(client_id, game_id, rental_date, return_date) VALUES
(1, 1, '2020-01-01', '2020-01-15'),
(2, 2, '2020-01-02', '2020-01-16'),
(3, 3, '2020-01-03', '2020-01-17'),
(4, 4, '2020-01-04', '2020-01-18'),
(5, 5, '2020-01-05', '2020-01-19'),
(6, 6, '2020-01-06', '2020-01-20'),
(7, 1, '2020-01-07', '2020-01-21'),
(8, 2, '2020-01-08', '2020-01-22'),
(9, 3, '2020-01-09', '2020-01-23'),
(10, 4, '2020-01-10', '2020-01-24');