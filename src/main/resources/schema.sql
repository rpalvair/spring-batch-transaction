DROP TABLE user IF EXISTS;

CREATE TABLE user  (
    user_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(20)
);