--liquibase formatted sql

--changeset eronryabets:1
ALTER TABLE users
ADD COLUMN image VARCHAR(64);

