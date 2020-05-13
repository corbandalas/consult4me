CREATE SEQUENCE hibernate_sequence;

CREATE TABLE property
(
    id SERIAL,
    description character varying(255),
    key character varying(255),
    value character varying(255),
    CONSTRAINT property_pkey PRIMARY KEY (id)
);
