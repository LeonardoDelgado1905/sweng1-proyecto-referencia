INSERT INTO declaracion (id, estado, fecha_creacion)
SELECT 1, 'BORRADOR', DATE '2026-10-01'
WHERE NOT EXISTS (SELECT 1 FROM declaracion WHERE id = 1);
INSERT INTO declaracion (id, estado, fecha_creacion)
SELECT 2, 'BORRADOR', DATE '2026-10-02'
WHERE NOT EXISTS (SELECT 1 FROM declaracion WHERE id = 2);

INSERT INTO documento_soporte (id, declaracion_id, tipo, archivo)
SELECT 1, 1, 'FACTURA', 'factura-1.pdf'
WHERE NOT EXISTS (SELECT 1 FROM documento_soporte WHERE id = 1);
INSERT INTO documento_soporte (id, declaracion_id, tipo, archivo)
SELECT 2, 1, 'DOCUMENTO_TRANSPORTE', 'transporte-1.pdf'
WHERE NOT EXISTS (SELECT 1 FROM documento_soporte WHERE id = 2);
INSERT INTO documento_soporte (id, declaracion_id, tipo, archivo)
SELECT 3, 1, 'LISTA_EMPAQUE', 'empaque-1.pdf'
WHERE NOT EXISTS (SELECT 1 FROM documento_soporte WHERE id = 3);
INSERT INTO documento_soporte (id, declaracion_id, tipo, archivo)
SELECT 4, 2, 'FACTURA', 'factura-2.pdf'
WHERE NOT EXISTS (SELECT 1 FROM documento_soporte WHERE id = 4);
INSERT INTO documento_soporte (id, declaracion_id, tipo, archivo)
SELECT 5, 2, 'DOCUMENTO_TRANSPORTE', 'transporte-2.pdf'
WHERE NOT EXISTS (SELECT 1 FROM documento_soporte WHERE id = 5);
INSERT INTO documento_soporte (id, declaracion_id, tipo, archivo)
SELECT 6, 2, 'LISTA_EMPAQUE', 'empaque-2.pdf'
WHERE NOT EXISTS (SELECT 1 FROM documento_soporte WHERE id = 6);

INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 1, 1, 1, 'NIT proveedor', '900123456', 0.99, 'CONFIABLE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 1);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 2, 1, 1, 'Valor FOB', '2500.00', 0.98, 'CONFIABLE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 2);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 3, 1, 2, 'Flete', '350.00', 0.95, 'CONFIABLE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 3);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 4, 1, 2, 'Peso bruto', '120 kg / 125 kg', 0.60, 'CONFLICTO', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 4);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 5, 1, 3, 'Subpartida', '6403999000', 0.55, 'BAJA_CONFIANZA', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 5);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 6, 1, NULL, 'Seguro', '', NULL, 'VACIO', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 6);

INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 7, 2, 4, 'NIT proveedor', '800987654', 0.99, 'CONFIABLE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 7);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 8, 2, 4, 'Valor FOB', '1800.00', 0.98, 'CONFIABLE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 8);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 9, 2, 5, 'Flete', '240.00', 0.97, 'CONFIABLE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 9);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 10, 2, 5, 'Peso bruto', '90 kg', 0.96, 'CONFIABLE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 10);
INSERT INTO campo_declaracion (id, declaracion_id, origen_id, nombre, valor, nivel_confianza, estado, revisado)
SELECT 11, 2, 6, 'Subpartida', '6403999000', 0.96, 'CONFIABLE', FALSE
WHERE NOT EXISTS (SELECT 1 FROM campo_declaracion WHERE id = 11);
