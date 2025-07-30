CREATE DATABASE if not exists productDB;

USE productDB;
DROP TABLE if exists products;

CREATE TABLE products (
                          id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
                          productName VARCHAR(255) DEFAULT NULL,
                          price VARCHAR(60) DEFAULT NULL
)AUTO_INCREMENT=1;

INSERT INTO products (id, productName, price) VALUES (1, 'shoes', 'EGP2000');
INSERT INTO products (id, productName, price) VALUES (2, 'laptop', 'EGP30000');
INSERT INTO products (id, productName, price) VALUES (3, 'rings', 'EGP300');
INSERT INTO products (id, productName, price) VALUES (4, 'phone', 'EGP15000');
INSERT INTO products (id, productName, price) VALUES (5, 'television', 'EGP80000');