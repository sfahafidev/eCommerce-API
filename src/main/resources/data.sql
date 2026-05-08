-- Productos fijos (catálogo)
INSERT INTO product (name, price) VALUES ('Auriculares', 150.00);
INSERT INTO product (name, price) VALUES ('Teclado', 300.00);
INSERT INTO product (name, price) VALUES ('Mouse', 200.00);
INSERT INTO product (name, price) VALUES ('Monitor', 2500.00);

-- Carritos iniciales
INSERT INTO cart (dni, is_special, price, date_created, status)
VALUES ('12345678', false, 0, CURRENT_TIMESTAMP, 'OPEN');

INSERT INTO cart (dni, is_special, price, date_created, status)
VALUES ('87654321', true, 0, CURRENT_TIMESTAMP, 'OPEN');

-- Ítems en carritos (relación con productos)
-- Carrito 1: cliente 12345678
INSERT INTO cart_items (quantity, cart_id, product_id) VALUES (2, 1, 1); -- 2 Auriculares
INSERT INTO cart_items (quantity, cart_id, product_id) VALUES (1, 1, 2); -- 1 Teclado

-- Carrito 2: cliente 87654321
INSERT INTO cart_items (quantity, cart_id, product_id) VALUES (1, 2, 3); -- 1 Mouse
INSERT INTO cart_items (quantity, cart_id, product_id) VALUES (1, 2, 4); -- 1 Monitor

-- Compras históricas
INSERT INTO purchases (id, dni, date, total_amount)
VALUES (1, '12345678', '2026-05-01 10:00:00', 3200.00);

INSERT INTO purchases (id, dni, date, total_amount)
VALUES (2, '12345678', '2026-05-03 15:00:00', 2500.00);

-- Items de compras
INSERT INTO purchase_items (id, name, price, quantity)
VALUES (1, 'Mouse', 200.00, 2);

INSERT INTO purchase_items (id, name, price, quantity)
VALUES (2, 'Monitor', 2500.00, 1);