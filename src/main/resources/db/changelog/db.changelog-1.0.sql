--liquibase formatted sql

--changeset eronryabets:1
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    email VARCHAR(64) NOT NULL UNIQUE ,
    username VARCHAR(64) NOT NULL UNIQUE ,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    birth_date DATE
);