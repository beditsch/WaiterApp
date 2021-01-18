INSERT INTO restaurants(name) VALUES ('Pizza, pasta i basta');
INSERT INTO restaurants(name) VALUES ('Uki Uki');
INSERT INTO restaurants(name) VALUES ('KFC');
INSERT INTO restaurants(name) VALUES ('Ciao a tutti');

INSERT INTO food_categories(name, restaurant_id) VALUES ('Pizza', 1);
INSERT INTO food_categories(name, restaurant_id) VALUES ('Makarony', 1);

INSERT INTO food_categories(name, restaurant_id) VALUES ('Udon', 2);
INSERT INTO food_categories(name, restaurant_id) VALUES ('Ramen', 2);

INSERT INTO food_categories(name, restaurant_id) VALUES ('Burgery', 3);
INSERT INTO food_categories(name, restaurant_id) VALUES ('Wrapy', 3);

INSERT INTO food_categories(name, restaurant_id) VALUES ('Pizze', 4);
INSERT INTO food_categories(name, restaurant_id) VALUES ('Napoje', 4);
INSERT INTO food_categories(name, restaurant_id) VALUES ('Alkohole', 4);


INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Pizza Hawajska', 29.99, 1, 1);
INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Pizza Salame', 24.99, 1, 1);
INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Pizza Capriciosa', 26.99, 1, 1);
INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Spaghetti Bolognese', 28.99, 1, 2);
INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Tagiatelle z krewetkami', 34.99, 1, 2);

INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Kama-Age', 30, 2, 3);
INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Kama-Ebi', 34, 2, 3);
INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Tonkotsu', 36, 2, 4);

INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Twister', 14.5, 3, 5);
INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Sok pomarańczowy', 6, 4, 8);
INSERT INTO meals(name, price, restaurant_id, food_category_id) VALUES ('Sex on the beach', 16, 4, 9);

INSERT INTO tables(restaurant_id) VALUES (1);
INSERT INTO tables(restaurant_id) VALUES (1);
INSERT INTO tables(restaurant_id) VALUES (2);
INSERT INTO tables(restaurant_id) VALUES (3);
INSERT INTO tables(restaurant_id) VALUES (3);
INSERT INTO tables(restaurant_id) VALUES (3);
INSERT INTO tables(restaurant_id) VALUES (4);

INSERT INTO orders(restaurant_id, table_id, public_order_id) VALUES (1, 1, 'byuvewbkjawndiuawd');
INSERT INTO orders(restaurant_id, table_id, public_order_id) VALUES (1, 1, 'grstyebsgvafe');
INSERT INTO orders(restaurant_id, table_id, public_order_id) VALUES (1, 2, 'ervsacervstbyndbs');

INSERT INTO order_positions(order_id, meal_id, quantity) VALUES (1, 5, 1);
INSERT INTO order_positions(order_id, meal_id) VALUES (1, 1);
INSERT INTO order_positions(order_id, meal_id) VALUES (2, 1);
INSERT INTO order_positions(order_id, meal_id) VALUES (2, 2);