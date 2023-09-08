--liquibase formatted sql

--changeset eronryabets:1
ALTER TABLE users
    ADD COLUMN account_non_locked BOOLEAN,
    ADD COLUMN failed_attempt INTEGER,
    ADD COLUMN lock_time TIMESTAMP;

