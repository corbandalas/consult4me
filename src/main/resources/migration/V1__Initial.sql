CREATE SEQUENCE hibernate_sequence;

CREATE TABLE property
(
    id SERIAL,
    description character varying(255),
    key character varying(255),
    value character varying(255),
    CONSTRAINT property_pkey PRIMARY KEY (id)
);

CREATE TABLE partner
(
    id SERIAL,
    company_name character varying(255),
    contact_person_name character varying(255),
    email character varying(255),
    merchantid character varying(255),
    phone_number character varying(255),
    private_key character varying(255),
    registered_address character varying(255),
    CONSTRAINT partner_pkey PRIMARY KEY (id)
);

CREATE TABLE menu
(
    id SERIAL,
    category character varying(255),
    role character varying(255),
    title character varying(255),
    url character varying(255),
    CONSTRAINT menu_pkey PRIMARY KEY (id)
);

CREATE TABLE customer
(
    id character varying(255),
    active boolean,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    password character varying(255),
    phone character varying(255),
    position character varying(255),
    token character varying(255),
    partner_id integer,
    CONSTRAINT customer_pkey PRIMARY KEY (id),
    CONSTRAINT customer_partner FOREIGN KEY (partner_id)
        REFERENCES partner (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE customer_merchant_accounts
(
    customer_id character varying(255),
    merchant_account character varying(255),
    CONSTRAINT customer_merchant_accounts_customer FOREIGN KEY (customer_id)
        REFERENCES customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE customer_roles
(
    customer_id character varying(255),
    role character varying(255),
    CONSTRAINT customer_roles_customer FOREIGN KEY (customer_id)
        REFERENCES customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE batch_transaction
(
    id SERIAL,
    date timestamp without time zone,
    CONSTRAINT batch_transaction_pkey PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id SERIAL,
    date timestamp without time zone,
    orderid character varying(255),
    request character varying(255),
    response character varying(255),
    status character varying(255),
    batch_transaction_id integer,
    customer_id character varying(255),
    CONSTRAINT transaction_pkey PRIMARY KEY (id),
    CONSTRAINT transaction_customer FOREIGN KEY (customer_id)
        REFERENCES customer (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT transaction_batch_transaction FOREIGN KEY (batch_transaction_id)
        REFERENCES batch_transaction (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);