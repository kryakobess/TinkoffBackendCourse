--liquibase formatted sql

--changeset nvoxland:1
CREATE TABLE tg_user
(
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL UNIQUE
);