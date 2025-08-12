-- Schema for snack shop demo (H2 compatible)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS customers;

CREATE TABLE customers (
  customer_id VARCHAR(20) PRIMARY KEY,
  customer_name VARCHAR(255) NOT NULL,
  phone_number VARCHAR(20),
  email VARCHAR(255),
  address VARCHAR(500),
  customer_type VARCHAR(20),
  total_purchased DECIMAL(15,2) DEFAULT 0,
  total_orders INT DEFAULT 0,
  total_spent DECIMAL(15,2) DEFAULT 0,
  loyalty_points INT DEFAULT 0,
  join_date DATE
);

CREATE TABLE categories (
  category_id VARCHAR(50) PRIMARY KEY,
  category_name VARCHAR(255) NOT NULL,
  description VARCHAR(500),
  parent_category_id VARCHAR(50),
  display_order INT DEFAULT 0
);

CREATE TABLE products (
  product_id VARCHAR(20) PRIMARY KEY,
  product_name VARCHAR(255) NOT NULL,
  category_id VARCHAR(50),
  category_name VARCHAR(255),
  price DECIMAL(15,2),
  cost_price DECIMAL(15,2),
  stock_quantity INT,
  min_stock_level INT,
  unit VARCHAR(50),
  brand VARCHAR(100),
  description VARCHAR(1000),
  image_url VARCHAR(1000),
  supplier_id VARCHAR(50),
  status VARCHAR(50),
  CONSTRAINT fk_products_category FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE orders (
  order_id VARCHAR(20) PRIMARY KEY,
  customer_id VARCHAR(20) NOT NULL,
  customer_name VARCHAR(255),
  employee_id VARCHAR(20),
  employee_name VARCHAR(255),
  order_date TIMESTAMP,
  status VARCHAR(30),
  payment_method VARCHAR(20),
  payment_status VARCHAR(20),
  discount_amount DECIMAL(15,2) DEFAULT 0,
  notes VARCHAR(1000),
  CONSTRAINT fk_orders_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_items (
  id IDENTITY PRIMARY KEY,
  order_id VARCHAR(20) NOT NULL,
  product_id VARCHAR(20) NOT NULL,
  product_name VARCHAR(255),
  category_id VARCHAR(50),
  unit_price DECIMAL(15,2),
  quantity INT,
  CONSTRAINT fk_items_order FOREIGN KEY (order_id) REFERENCES orders(order_id),
  CONSTRAINT fk_items_product FOREIGN KEY (product_id) REFERENCES products(product_id)
);
