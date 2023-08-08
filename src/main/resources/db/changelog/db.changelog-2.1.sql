--liquibase formatted sql

--changeset eronryabets:1
ALTER TABLE wallet_transactions
    ADD current_balance NUMERIC(15, 2);

--changeset eronryabets:2
UPDATE wallet_transactions
SET current_balance = previous_balance + amount;

--changeset eronryabets:3
ALTER TABLE wallet_transactions
    ALTER COLUMN current_balance SET NOT NULL;