--liquibase formatted sql

--changeset eronryabets:1
ALTER TABLE wallets
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE wallets
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE wallets
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE wallets
    ADD COLUMN modified_by VARCHAR(32);
