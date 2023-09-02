--liquibase formatted sql

--changeset eronryabets:1
CREATE TABLE notes
(
    id         BIGSERIAL PRIMARY KEY,
    text       TEXT        NOT NULL,
    date_added TIMESTAMPTZ NOT NULL,
    user_id    BIGINT REFERENCES users (id)
);
