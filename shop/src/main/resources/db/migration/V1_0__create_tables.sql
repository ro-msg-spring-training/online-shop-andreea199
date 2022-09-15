CREATE TABLE IF NOT EXISTS `supplier` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(50)
);

CREATE TABLE IF NOT EXISTS `customer` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email_address` varchar(50),
    `first_name` varchar(50),
    `last_name` varchar(50),
    `password` varchar(10),
    `username` varchar(20)
);

CREATE TABLE IF NOT EXISTS `location` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `address_city` varchar(30),
    `address_country` varchar(30),
    `address_street` varchar(40),
    `name` varchar(20)
);

CREATE TABLE IF NOT EXISTS `orders` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `address_city` varchar(30),
    `address_country` varchar(30),
    `address_street` varchar(30),
    `created_at` datetime,
    `customer_id` integer,
     FOREIGN KEY (customer_id) references customer (id)
);

CREATE TABLE IF NOT EXISTS `product_category` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `description` varchar(255),
    `name` varchar(40)
);

CREATE TABLE IF NOT EXISTS `product` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `image_url` varchar(120),
    `description` varchar(255),
    `name` varchar(40),
    `price` decimal(19,2),
    `weight` double precision,
    `product_category_id` integer,
    `supplier_id` integer,
    FOREIGN KEY (product_category_id) references product_category (id),
    FOREIGN KEY (supplier_id) references supplier (id)
);

CREATE TABLE IF NOT EXISTS `order_detail` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `quantity` integer,
    `order_id` integer,
    `product_id` integer,
    FOREIGN KEY (order_id)  references orders(id),
    FOREIGN KEY (product_id)  references product (id)
);

 CREATE TABLE IF NOT EXISTS order_location (

    primary key (order_id, location_id),
    `order_id` integer not null,
    `location_id` integer not null,
    FOREIGN KEY (order_id) references orders(id),
    FOREIGN KEY (location_id) references location(id)
 );

CREATE TABLE IF NOT EXISTS `revenue` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `date` date,
    `sum` decimal(19,2),
    `location_id` integer,
    FOREIGN KEY (location_id) REFERENCES location (id)
);

CREATE TABLE IF NOT EXISTS `stock` (

    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `quantity` integer,
    `location_id` integer,
   `product_id` integer,
    FOREIGN KEY (location_id) REFERENCES location (id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);