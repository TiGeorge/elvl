CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 100;

CREATE TABLE quotes
(
    id   BIGINT PRIMARY KEY,
    isin VARCHAR(12) NOT NULL,
    time_stamp TIMESTAMPTZ NOT NULL,
    bid  NUMERIC(15, 2),
    ask  NUMERIC(15, 2) DEFAULT 0
);

CREATE TABLE elvls
(
    isin     VARCHAR(50) PRIMARY KEY,
    elvl     NUMERIC(15, 2) DEFAULT 0,
    time_stamp TIMESTAMPTZ NOT NULL
);

