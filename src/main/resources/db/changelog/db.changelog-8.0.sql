--liquibase formatted sql

--changeset eronryabets:1
ALTER TABLE revision
    ADD COLUMN author VARCHAR(20);
