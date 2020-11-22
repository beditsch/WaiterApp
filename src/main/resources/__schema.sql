DROP TABLE IF EXISTS restaurants cascade;
DROP TABLE IF EXISTS food_categories cascade;
DROP TABLE IF EXISTS tables cascade;
DROP TABLE IF EXISTS meals cascade;
DROP TABLE IF EXISTS orders cascade;
DROP TABLE IF EXISTS order_positions cascade;
DROP TABLE IF EXISTS users cascade;

CREATE TABLE restaurants
(
    restaurant_id NUMBER(4) AUTO_INCREMENT
        CONSTRAINT restaurants_PK PRIMARY KEY,
    name          VARCHAR(40)
);

CREATE TABLE food_categories
(
    food_category_id NUMBER(4) AUTO_INCREMENT
        CONSTRAINT food_categories_PK PRIMARY KEY,
    name             VARCHAR(40),
    priority         NUMBER(4) DEFAULT 0,
    restaurant_id    NUMBER(4) NOT NULL REFERENCES restaurants (restaurant_id)
);

CREATE TABLE tables
(
    table_id      NUMBER(4) AUTO_INCREMENT
        CONSTRAINT tables_PK PRIMARY KEY,
    restaurant_id NUMBER(4) NOT NULL REFERENCES restaurants (restaurant_id)
);

CREATE TABLE meals
(
    meal_id          NUMBER(4) AUTO_INCREMENT
        CONSTRAINT meals_PK PRIMARY KEY,
    name             VARCHAR(80),
    price            NUMBER(4, 2),
    is_available     BIT DEFAULT 1,
    restaurant_id    NUMBER(4) NOT NULL REFERENCES restaurants (restaurant_id),
    food_category_id NUMBER(4) REFERENCES food_categories (food_category_id)
);

CREATE TABLE orders
(
    order_id      NUMBER(4) AUTO_INCREMENT
        CONSTRAINT orders_PK PRIMARY KEY,
    status        NUMBER(4) DEFAULT 0,
    restaurant_id NUMBER(4) NOT NULL REFERENCES restaurants (restaurant_id),
    table_id      NUMBER(4) NOT NULL REFERENCES tables (table_id)
);

-- CREATE TABLE order_positions
-- (
--     order_position_id NUMBER(4) AUTO_INCREMENT
--         CONSTRAINT order_positions_PK PRIMARY KEY,
--     order_id          NUMBER(4) NOT NULL REFERENCES orders (order_id),
--     meal_id           NUMBER(4) NOT NULL REFERENCES meals (meal_id)
-- );

CREATE TABLE order_positions
(
    order_id NUMBER(4) NOT NULL REFERENCES orders (order_id),
    meal_id  NUMBER(4) NOT NULL REFERENCES meals (meal_id),
    CONSTRAINT order_positions_PK PRIMARY KEY (order_id, meal_id)
);

CREATE TABLE users
(
    user_id       NUMBER(4) AUTO_INCREMENT
        CONSTRAINT users_PK PRIMARY KEY,
    username      VARCHAR(50) NOT NULL,
    password      VARCHAR(50) NOT NULL,
    restaurant_id NUMBER(4) REFERENCES restaurants (restaurant_id)
);


