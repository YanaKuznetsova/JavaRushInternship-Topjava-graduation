DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, role) VALUES
  ('User_1', 'user-1@yandex.ru', 'password1', 'ROLE_USER'),
  ('User_2', 'user-2@yandex.ru', 'password2', 'ROLE_USER'),
  ('User_3', 'user-3@yandex.ru', 'password3', 'ROLE_USER'),
  ('User_4', 'user-4@yandex.ru', 'password4', 'ROLE_USER'),
  ('User_5', 'user-5@yandex.ru', 'password5', 'ROLE_USER'),
  ('Admin', 'admin@gmail.com', 'admin', 'ROLE_ADMIN');

INSERT INTO restaurants (name) VALUES
  ('Dinner In The Sky'),
  ('Funky Gourmet'),
  ('Scala Vinoteca');

INSERT INTO dishes (name, price, restaurant_id, date) VALUES
  ('Soup-1', 100, 100006, '2015-05-30'),
  ('Salad-1', 100, 100006, '2015-05-30'),
  ('Main course-1', 100, 100006, '2015-05-30'),
  ('Desert-1', 100, 100006, '2015-05-30'),
  ('Soup-2', 100, 100007, '2015-05-30'),
  ('Salad-2', 100, 100007, '2015-05-30'),
  ('Main course-2', 100, 100007, '2015-05-30'),
  ('Desert-2', 100, 100007, '2015-05-30'),
  ('Soup-3', 100, 100008, '2015-05-30'),
  ('Salad-3', 100, 100008, '2015-05-30'),
  ('Main course-3', 100, 100008, '2015-05-30'),
  ('Desert-3', 100, 100008, '2015-05-30'),
  ('Soup-4', 100, 100007, '2015-05-31'),
  ('Salad-4', 100, 100007, '2015-05-31'),
  ('Main course-4', 100, 100007, '2015-05-31'),
  ('Desert-4', 100, 100007, '2015-05-31'),
  ('Soup-5', 100, 100008, '2015-05-31'),
  ('Salad-5', 100, 100008, '2015-05-31'),
  ('Main course-5', 100, 100008, '2015-05-31'),
  ('Desert-5', 100, 100008, '2015-05-31');

INSERT INTO votes (restaurant_id, user_id, date) VALUES
  (100006, 100000, '2015-05-30'),
  (100006, 100001, '2015-05-30'),
  (100006, 100002, '2015-05-30'),
  (100007, 100003, '2015-05-30'),
  (100008, 100004, '2015-05-30'),
  (100007, 100000, '2015-05-31'),
  (100007, 100001, '2015-05-31'),
  (100007, 100002, '2015-05-31'),
  (100007, 100003, '2015-05-31'),
  (100008, 100004, '2015-05-31');


INSERT INTO rating (restaurant_id, date, summary_votes) VALUES
  (100006, '2015-05-30', 3),
  (100007, '2015-05-30', 1),
  (100008, '2015-05-30', 1),
  (100007, '2015-05-31', 4),
  (100008, '2015-05-31', 1);