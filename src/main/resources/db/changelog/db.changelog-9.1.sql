--liquibase formatted sql

--changeset eronryabets:1
ALTER TABLE notes
    ADD COLUMN title VARCHAR(32);
