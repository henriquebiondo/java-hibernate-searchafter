CREATE TABLE tbl_quotation (
    id INT NOT NULL AUTO_INCREMENT,
    quotation_name VARCHAR(255),
    expiration_date DATE,
    delivery_date DATE,
    delivery_price DECIMAL(4,2),
    description VARCHAR(255),
    observation VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE tbl_product (
    id INT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(255),
    quantity INTEGER,
    cod_product VARCHAR(255),
    price DECIMAL(4,2),
    description VARCHAR(255),
    PRIMARY KEY (id)
);
