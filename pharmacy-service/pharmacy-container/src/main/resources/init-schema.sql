DROP SCHEMA IF EXISTS pharmacy CASCADE;

CREATE SCHEMA pharmacy;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS pharmacy.pharmacies CASCADE;

CREATE TABLE pharmacy.pharmacies
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    active boolean NOT NULL,
    CONSTRAINT pharmacies_pkey PRIMARY KEY (id)
);

DROP TYPE IF EXISTS approval_status;

CREATE TYPE approval_status AS ENUM ('APPROVED', 'REJECTED');

DROP TABLE IF EXISTS pharmacy.order_approval CASCADE;

CREATE TABLE pharmacy.order_approval
(
    id uuid NOT NULL,
    pharmacy_id uuid NOT NULL,
    order_id uuid NOT NULL,
    status approval_status NOT NULL,
    CONSTRAINT order_approval_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS pharmacy.medicines CASCADE;

CREATE TABLE pharmacy.medicines
(
    id uuid NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    price numeric(10,2) NOT NULL,
    available boolean NOT NULL,
    CONSTRAINT medicines_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS pharmacy.pharmacy_medicines CASCADE;

CREATE TABLE pharmacy.pharmacy_medicines
(
    id uuid NOT NULL,
    pharmacy_id uuid NOT NULL,
    medicine_id uuid NOT NULL,
    CONSTRAINT pharmacy_medicines_pkey PRIMARY KEY (id)
);

ALTER TABLE pharmacy.pharmacy_medicines
    ADD CONSTRAINT FK_PHARMACY_ID FOREIGN KEY (pharmacy_id)
        REFERENCES pharmacy.pharmacies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
    NOT VALID;

ALTER TABLE pharmacy.pharmacy_medicines
    ADD CONSTRAINT "FK_MEDICINE_ID" FOREIGN KEY (medicine_id)
        REFERENCES pharmacy.medicines (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE RESTRICT
    NOT VALID;

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'COMPLETED', 'FAILED');

DROP TABLE IF EXISTS pharmacy.order_outbox CASCADE;

CREATE TABLE pharmacy.order_outbox
(
    id uuid NOT NULL,
    saga_id uuid NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    processed_at TIMESTAMP WITH TIME ZONE,
    type character varying COLLATE pg_catalog."default" NOT NULL,
    payload jsonb NOT NULL,
    outbox_status outbox_status NOT NULL,
    approval_status approval_status NOT NULL,
    version integer NOT NULL,
    CONSTRAINT order_outbox_pkey PRIMARY KEY (id)
);

CREATE INDEX pharmacy_order_outbox_saga_status
    ON "pharmacy".order_outbox
        (type, approval_status);

CREATE UNIQUE INDEX pharmacy_order_outbox_saga_id
    ON "pharmacy".order_outbox
        (type, saga_id, approval_status, outbox_status);

DROP MATERIALIZED VIEW IF EXISTS pharmacy.order_pharmacy_m_view;

CREATE MATERIALIZED VIEW pharmacy.order_pharmacy_m_view
TABLESPACE pg_default
AS
SELECT r.id AS pharmacy_id,
       r.name AS pharmacy_name,
       r.active AS pharmacy_active,
       p.id AS medicine_id,
       p.name AS medicine_name,
       p.price AS medicine_price,
       p.available AS medicine_available
FROM pharmacy.pharmaciess p,
     pharmacy.medicines m,
     pharmacy.pharmacy_products pm
WHERE p.id = pm.pharmacy_id AND m.id = pm.medicine_id
    WITH DATA;

refresh materialized VIEW pharmacy.order_pharmacy_m_view;

DROP function IF EXISTS pharmacy.refresh_order_pharmacy_m_view;

CREATE OR replace function pharmacy.refresh_order_pharmacy_m_view()
returns trigger
AS '
BEGIN
    refresh materialized VIEW pharmacy.order_pharmacy_m_view;
    return null;
END;
'  LANGUAGE plpgsql;

DROP trigger IF EXISTS refresh_order_pharmacy_m_view ON pharmacy.pharmacy_medicines;

CREATE trigger refresh_order_pharmacy_m_view
    after INSERT OR UPDATE OR DELETE OR truncate
                    ON pharmacy.pharmacy_medicines FOR each statement
                        EXECUTE PROCEDURE pharmacy.refresh_order_pharmacy_m_view();