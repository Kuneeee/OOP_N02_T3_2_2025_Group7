-- Seed data matching SampleDataProvider

-- Categories
INSERT INTO categories(category_id, category_name, description, display_order)
VALUES
 ('CAT_BANH', 'Bánh kẹo', 'Các loại bánh và kẹo', 1),
 ('CAT_NUOC', 'Nước uống', 'Các loại nước uống', 2),
 ('CAT_CHE',  'Chè',       'Các loại chè và tráng miệng', 3);

-- Products
INSERT INTO products(product_id, product_name, category_id, category_name, price, cost_price, stock_quantity, min_stock_level, unit, brand, description, status)
VALUES
 ('PRD001', 'Bánh tráng nướng', 'CAT_BANH', 'Bánh kẹo', 15000, 8000, 50, 10, 'cái', 'Homemade', 'Bánh tráng nướng giòn rụm với đầy đủ topping', 'Active'),
 ('PRD002', 'Trà sữa trân châu', 'CAT_NUOC', 'Nước uống', 25000, 12000, 30, 5, 'ly', 'Tea House', 'Trà sữa thơm ngon với trân châu dai dai', 'Active'),
 ('PRD003', 'Bánh mì nướng muối ớt', 'CAT_BANH', 'Bánh kẹo', 20000, 10000, 25, 5, 'cái', 'Saigon Bakery', 'Bánh mì nướng giòn với muối ớt đặc biệt', 'Active'),
 ('PRD004', 'Chè thái', 'CAT_CHE', 'Chè', 18000, 9000, 20, 3, 'ly', 'Chè Bà Ba', 'Chè thái mát lạnh với nhiều topping', 'Active');

-- Customers
INSERT INTO customers(customer_id, customer_name, phone_number, email, address, customer_type, total_purchased, total_orders, total_spent, loyalty_points, join_date)
VALUES
 ('CUS001', 'Chị Bảo', '0355696858', 'giabaomc0903@gmail.com', 'Thôn 6 Hải Tiến, xã Hải Ninh, tỉnh Quảng Ninh', 'VIP', 500000, 25, 500000, 0, CURRENT_DATE),
 ('CUS002', 'Anh Long', '0866198289', 'dangduclong100@gmail.com', 'Thôn 4 Hải Tiến, xã Hải Ninh, tỉnh Quảng Ninh', 'Regular', 150000, 8, 150000, 0, CURRENT_DATE),
 ('CUS003', 'Em Hương', '0923456789', 'huong123@gmail.com', '789 Hai Bà Trưng, Quận 1, TP.HCM', 'New', 0, 0, 0, 0, CURRENT_DATE);

-- Orders
INSERT INTO orders(order_id, customer_id, customer_name, employee_id, employee_name, order_date, status, payment_method, payment_status, discount_amount, notes)
VALUES
 ('ORD001', 'CUS001', 'Chị Bảo', 'USR003', 'Nhân Viên', DATEADD('DAY', -2, CURRENT_TIMESTAMP), 'COMPLETED', 'CASH', 'PAID', 0, 'Khách VIP - giao tận bàn'),
 ('ORD002', 'CUS002', 'Anh Long', 'USR003', 'Nhân Viên', DATEADD('HOUR', -2, CURRENT_TIMESTAMP), 'PROCESSING', 'CARD', 'PAID', 0, 'Đang chuẩn bị');

-- Order Items
INSERT INTO order_items(order_id, product_id, product_name, category_id, unit_price, quantity)
VALUES
 ('ORD001', 'PRD001', 'Bánh tráng nướng', 'CAT_BANH', 15000, 2),
 ('ORD001', 'PRD002', 'Trà sữa trân châu', 'CAT_NUOC', 25000, 1),
 ('ORD002', 'PRD004', 'Chè thái', 'CAT_CHE', 18000, 1);
