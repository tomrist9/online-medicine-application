INSERT INTO pharmacy.pharmacies(id, name, active)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb45', 'pharmacy_1', TRUE);
INSERT INTO pharmacy.pharmacies(id, name, active)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb46', 'pharmacy_2', FALSE);

INSERT INTO pharmacy.medicines(id, name, price, available)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb47', 'medicine_1', 25.00, FALSE);
INSERT INTO pharmacy.medicines(id, name, price, available)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb48', 'medicine_2', 50.00, TRUE);
INSERT INTO pharmacy.medicines(id, name, price, available)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb49', 'medicine_3', 20.00, FALSE);
INSERT INTO pharmacy.medicines(id, name, price, available)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb50', 'medicine_4', 40.00, TRUE);

INSERT INTO pharmacy.pharmacy_medicines(id, pharmacy_id, medicine_id)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb51', 'd215b5f8-0249-4dc5-89a3-51fd148cfb45', 'd215b5f8-0249-4dc5-89a3-51fd148cfb47');
INSERT INTO pharmacy.pharmacy_medicines(id, pharmacy_id, medicine_id)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb52', 'd215b5f8-0249-4dc5-89a3-51fd148cfb45', 'd215b5f8-0249-4dc5-89a3-51fd148cfb48');
INSERT INTO pharmacy.pharmacy_medicines(id, pharmacy_id, medicine_id)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb53', 'd215b5f8-0249-4dc5-89a3-51fd148cfb46', 'd215b5f8-0249-4dc5-89a3-51fd148cfb49');
INSERT INTO pharmacy.pharmacy_medicines(id, pharmacy_id, medicine_id)
VALUES ('d215b5f8-0249-4dc5-89a3-51fd148cfb54', 'd215b5f8-0249-4dc5-89a3-51fd148cfb46', 'd215b5f8-0249-4dc5-89a3-51fd148cfb50');