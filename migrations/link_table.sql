--liquibase formatted sql

--changeset nvoxland:1
CREATE TABLE link_subscription
(
    id BIGSERIAL PRIMARY KEY,
    tgUserId BIGINT NOT NULL,
    link VARCHAR(256) NOT NULL,
    lastUpdate TIMESTAMP,
    FOREIGN KEY (tgUserId) REFERENCES tg_user (id)
);