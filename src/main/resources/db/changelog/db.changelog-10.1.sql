--liquibase formatted sql

--changeset eronryabets:1
ALTER TABLE users
    ADD COLUMN reset_password_token VARCHAR(30);

