USE taco;

-- DROP TABLE
DROP TABLE IF EXISTS taco_ingredients;
DROP TABLE IF EXISTS taco_order_tacos;
DROP TABLE IF EXISTS ingredient;
DROP TABLE IF EXISTS taco;
DROP TABLE IF EXISTS taco_order;

CREATE TABLE IF NOT EXISTS ingredient
(
    id   VARCHAR(4)  NOT NULL PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS taco
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(50) NOT NULL,
    created DATETIME    NOT NULL
);

CREATE TABLE IF NOT EXISTS taco_ingredients
(
    taco       INT        NOT NULL,
    ingredient VARCHAR(4) NOT NULL
);

ALTER TABLE taco_ingredients
    ADD FOREIGN KEY (taco) REFERENCES taco (id);

ALTER TABLE taco_ingredients
    ADD FOREIGN KEY (ingredient) REFERENCES ingredient (id);

CREATE TABLE IF NOT EXISTS taco_order
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    deliveryname   VARCHAR(50) NOT NULL,
    deliverystreet VARCHAR(50) NOT NULL,
    deliverycity   VARCHAR(50) NOT NULL,
    deliverystate  VARCHAR(2)  NOT NULL,
    deliveryzip    VARCHAR(10) NOT NULL,
    ccnumber       VARCHAR(16) NOT NULL,
    ccexpiration   VARCHAR(5)  NOT NULL,
    cccvv          VARCHAR(3)  NOT NULL,
    placed         DATETIME    NOT NULL
);

CREATE TABLE IF NOT EXISTS taco_order_tacos
(
    tacoorder INT NOT NULL,
    taco      INT NOT NULL
);

ALTER TABLE taco_order_tacos
    ADD FOREIGN KEY (tacoorder) REFERENCES taco_order (id);

ALTER TABLE taco_order_tacos
    ADD FOREIGN KEY (taco) REFERENCES taco (id);
