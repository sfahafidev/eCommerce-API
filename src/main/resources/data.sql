-- Productos fijos (catálogo)
INSERT INTO product (name, price) VALUES ('Auriculares', 150.00);
INSERT INTO product (name, price) VALUES ('Teclado', 300.00);
INSERT INTO product (name, price) VALUES ('Mouse', 200.00);
INSERT INTO product (name, price) VALUES ('Camara', 250.00);
INSERT INTO product (name, price) VALUES ('Monitor', 2500.00);
INSERT INTO product (name, price) VALUES ('Notebook', 3000.00);

-- Carritos iniciales
INSERT INTO cart (dni, is_special, date_created, status)
VALUES ('12345678', false, CURRENT_DATE, 'OPEN');

INSERT INTO cart (dni, is_special, date_created, status)
VALUES ('87654321', true, CURRENT_DATE, 'OPEN');

-- Ítems en carritos (relación con productos)
-- Carrito 1: cliente 12345678
INSERT INTO cart_items (quantity, cart_id, product_id) VALUES (2, 1, 1); -- 2 Auriculares
INSERT INTO cart_items (quantity, cart_id, product_id) VALUES (1, 1, 2); -- 1 Teclado

-- Carrito 2: cliente 87654321
INSERT INTO cart_items (quantity, cart_id, product_id) VALUES (1, 2, 3); -- 1 Mouse
INSERT INTO cart_items (quantity, cart_id, product_id) VALUES (1, 2, 4); -- 1 Monitor

-- Compras históricas
-- Cliente 12345678: compra normal con 2 productos
INSERT INTO purchases (dni, total_amount, date) VALUES
('12345678', 1200.00, '2026-05-01');

INSERT INTO purchase_items (purchase_id, product_id, quantity, price) VALUES
(1, 1, 2, 150.00), -- 2 auriculares
(1, 2, 1, 900.00); -- 1 notebook

-- Cliente 87654321: compra con más de 3 productos (descuento $100/$150)
INSERT INTO purchases (dni, total_amount, date) VALUES
('87654321', 3500.00, '2026-05-02');

INSERT INTO purchase_items (purchase_id, product_id, quantity, price) VALUES
(2, 3, 1, 500.00), -- 1 teclado
(2, 4, 2, 200.00), -- 2 mouse
(2, 5, 1, 2600.00); -- 1 monitor

-- Cliente 95762679: compra con 4x3 (4 productos iguales)
INSERT INTO purchases (dni, total_amount, date) VALUES
('95762679', 600.00, '2026-05-03');

INSERT INTO purchase_items (purchase_id, product_id, quantity, price) VALUES
(3, 1, 4, 150.00); -- 4 auriculares (promo 4x3)

-- Cliente 55555555: compras acumuladas >5000 en mayo (VIP)
INSERT INTO purchases (dni, total_amount, date) VALUES
('55555555', 3000.00, '2026-05-04'),
('55555555', 2500.00, '2026-05-10');

INSERT INTO purchase_items (purchase_id, product_id, quantity, price) VALUES
(4, 2, 1, 3000.00), -- notebook
(5, 5, 1, 2500.00); -- monitor

-- Cliente 55555555: siguiente compra >2000, aplica descuento VIP $500
INSERT INTO purchases (dni, total_amount, date) VALUES
('55555555', 2800.00, '2026-06-01');

INSERT INTO purchase_items (purchase_id, product_id, quantity, price) VALUES
(6, 4, 2, 1400.00); -- 2 mouse

-- Cliente 12345678: 15 compras distribuidas en el año
INSERT INTO purchases (dni, total_amount, date) VALUES
('12345678', 1200.00, '2026-01-05'),
('12345678', 800.00, '2026-02-10'),
('12345678', 950.00, '2026-03-12'),
('12345678', 1500.00, '2026-04-20'),
('12345678', 600.00, '2026-05-01'),
('12345678', 2200.00, '2026-06-08'),
('12345678', 700.00, '2026-07-15'),
('12345678', 1300.00, '2026-08-03'),
('12345678', 900.00, '2026-09-09'),
('12345678', 2500.00, '2026-10-21'),
('12345678', 1100.00, '2026-11-05'),
('12345678', 1800.00, '2026-12-01'),
('12345678', 950.00, '2026-12-10'),
('12345678', 1200.00, '2026-12-15'),
('12345678', 2100.00, '2026-12-28');

-- Items de las compras de 12345678
INSERT INTO purchase_items (purchase_id, product_id, quantity, price) VALUES
(1, 1, 2, 150.00), -- 2 auriculares
(1, 2, 1, 900.00), -- 1 notebook
(2, 3, 1, 300.00), -- teclado
(2, 4, 2, 250.00), -- 2 camaras
(3, 5, 1, 950.00), -- monitor
(4, 6, 1, 1500.00), -- notebook
(5, 1, 4, 150.00), -- promo 4x3 auriculares
(6, 5, 1, 2200.00), -- monitor
(7, 3, 2, 350.00), -- teclados
(8, 2, 1, 1300.00), -- notebook
(9, 4, 3, 300.00), -- 3 camaras
(10, 6, 1, 2500.00), -- notebook
(11, 1, 2, 150.00), -- auriculares
(11, 3, 1, 800.00), -- teclado
(12, 5, 1, 1800.00), -- monitor
(13, 2, 1, 950.00), -- notebook
(14, 6, 1, 1200.00), -- notebook
(15, 5, 1, 2100.00); -- monitor

-- Prueba 1: compras de enero
-- GET /purchases/dni/12345678/range?from=2026-01-01&to=2026-01-31

-- Prueba 2: compras de marzo
-- GET /purchases/dni/12345678/range?from=2026-03-01&to=2026-03-31

-- Prueba 3: compras de el primer semestre
-- GET /purchases/dni/12345678/range?from=2026-01-01&to=2026-06-30

-- Prueba 4: compras de diciembre (varias en el mismo mes)
-- GET /purchases/dni/12345678/range?from=2026-12-01&to=2026-12-31

-- Prueba 5: compras exactas de un día puntual
-- GET /purchases/dni/12345678/range?from=2026-05-01&to=2026-05-01

-- Cliente 55555555: 15 compras distribuidas en el año
INSERT INTO purchases (dni, total_amount, date) VALUES
('55555555', 3000.00, '2026-01-07'),
('55555555', 2500.00, '2026-02-14'),
('55555555', 2800.00, '2026-03-01'),
('55555555', 3200.00, '2026-04-11'),
('55555555', 1500.00, '2026-05-04'),
('55555555', 2500.00, '2026-05-10'),
('55555555', 2800.00, '2026-06-01'),
('55555555', 2200.00, '2026-07-18'),
('55555555', 3100.00, '2026-08-25'),
('55555555', 2700.00, '2026-09-09'),
('55555555', 3500.00, '2026-10-21'),
('55555555', 4000.00, '2026-11-05'),
('55555555', 1800.00, '2026-12-02'),
('55555555', 2200.00, '2026-12-15'),
('55555555', 2600.00, '2026-12-28');

-- Items de las compras de 55555555
INSERT INTO purchase_items (purchase_id, product_id, quantity, price) VALUES
(16, 6, 1, 3000.00), -- notebook
(17, 5, 1, 2500.00), -- monitor
(18, 2, 2, 1400.00), -- 2 notebooks
(19, 3, 2, 1600.00), -- 2 teclados
(20, 1, 10, 150.00), -- 10 auriculares
(21, 4, 5, 500.00), -- 5 camaras
(22, 6, 1, 2800.00), -- notebook
(23, 5, 1, 2200.00), -- monitor
(24, 3, 3, 3100.00), -- teclados
(25, 2, 1, 2700.00), -- notebook
(26, 5, 1, 3500.00), -- monitor
(27, 6, 1, 4000.00), -- notebook
(28, 1, 12, 150.00), -- 12 auriculares
(29, 4, 8, 275.00), -- 8 camaras
(30, 2, 1, 2600.00); -- notebook

-- Prueba 1: compras de mayo (acumulado >5000, debería activar VIP)
-- GET /purchases/dni/55555555/range?from=2026-05-01&to=2026-05-31

-- Prueba 2: compras de junio (compra >2000, aplica descuento VIP)
-- GET /purchases/dni/55555555/range?from=2026-06-01&to=2026-06-30

-- Prueba 3: compras de el año
-- GET /purchases/dni/55555555/range?from=2026-01-01&to=2026-12-31

-- Prueba 4: compras de septiembre
-- GET /purchases/dni/55555555/range?from=2026-09-01&to=2026-09-30

-- Prueba 5: compras de un día específico
-- GET /purchases/dni/55555555/range?from=2026-02-14&to=2026-02-14