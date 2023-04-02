CREATE TABLE tg_user
(
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    userName VARCHAR(128)
);