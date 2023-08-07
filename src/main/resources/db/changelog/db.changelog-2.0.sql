--liquibase formatted sql

--changeset eronryabets:1
CREATE TABLE IF NOT EXISTS wallets
(
    id BIGSERIAL PRIMARY KEY,
    wallet_name VARCHAR(100) NOT NULL,
    wallet_type VARCHAR(20) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    balance NUMERIC(15, 2) NOT NULL,
    owner_user_id BIGINT NOT NULL REFERENCES users(id)
    );

--changeset eronryabets:2
CREATE TABLE IF NOT EXISTS wallet_transactions
(
    id BIGSERIAL PRIMARY KEY,
    wallet_id BIGINT NOT NULL REFERENCES wallets(id),
    previous_balance NUMERIC(15, 2) NOT NULL,
    operation_type VARCHAR(20) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    amount NUMERIC(15, 2) NOT NULL
    );