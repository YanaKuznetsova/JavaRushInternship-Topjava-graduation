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

INSERT INTO dishes (name, price, restaurant_id, datetime) VALUES
  ('Soup-1', 100, 100006, '2015-05-30 10:00:00'),
  ('Salad-1', 100, 100006, '2015-05-30 10:00:00'),
  ('Main course-1', 100, 100006, '2015-05-30 10:00:00'),
  ('Desert-1', 100, 100006, '2015-05-30 10:00:00'),
  ('Soup-2', 100, 100007, '2015-05-30 10:00:00'),
  ('Salad-2', 100, 100007, '2015-05-30 10:00:00'),
  ('Main course-2', 100, 100007, '2015-05-30 10:00:00'),
  ('Desert-2', 100, 100007, '2015-05-30 10:00:00'),
  ('Soup-3', 100, 100008, '2015-05-30 10:00:00'),
  ('Salad-3', 100, 100008, '2015-05-30 10:00:00'),
  ('Main course-3', 100, 100008, '2015-05-30 10:00:00'),
  ('Desert-3', 100, 100008, '2015-05-30 10:00:00'),
  ('Soup-4', 100, 100006, '2015-05-31 10:00:00'),
  ('Salad-4', 100, 100006, '2015-05-31 10:00:00'),
  ('Main course-4', 100, 100006, '2015-05-31 10:00:00'),
  ('Desert-4', 100, 100006, '2015-05-31 10:00:00');

INSERT INTO votes (restaurant_id, user_id, datetime) VALUES
  (100006, 100000, '2015-05-30 10:00:00'),
  (100006, 100001, '2015-05-30 10:00:00'),
  (100006, 100002, '2015-05-30 10:00:00'),
  (100007, 100003, '2015-05-30 10:00:00'),
  (100008, 100004, '2015-05-30 10:00:00'),
  (100007, 100000, '2015-05-31 10:00:00'),
  (100007, 100001, '2015-05-31 10:00:00'),
  (100007, 100002, '2015-05-31 10:00:00'),
  (100007, 100003, '2015-05-31 10:00:00'),
  (100006, 100004, '2015-05-31 10:00:00');