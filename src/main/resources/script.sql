INSERT INTO orders (reg_date, dcto, total) VALUES
(now(), 0.0, 50);

INSERT INTO order_details(price, quantity, dcto, total, fk_order, fk_product) VALUES
(50, 1, 0, 50, 1, 3),
(50, 1, 0, 50, 1, 4);
