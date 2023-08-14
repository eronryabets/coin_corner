--liquibase formatted sql

--changeset eronryabets:1
CREATE TABLE roles
(
    id        SERIAL PRIMARY KEY,
    user_id   BIGINT REFERENCES users (id),
    role VARCHAR(255) NOT NULL
);

