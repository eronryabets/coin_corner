--liquibase formatted sql

--changeset eronryabets:1
CREATE TABLE IF NOT EXISTS revision
(
    id SERIAL PRIMARY KEY ,
    timestamp BIGINT NOT NULL
);

--changeset eronryabets:2
CREATE TABLE IF NOT EXISTS wallets_aud
(
    id BIGINT,
    rev INT REFERENCES revision (id),
    revtype SMALLINT ,
    id_wallet BIGINT,
    wallet_name VARCHAR(100),
    wallet_type VARCHAR(20),
    currency VARCHAR(10),
    balance NUMERIC(15, 2),
    owner_user_id BIGINT
    );