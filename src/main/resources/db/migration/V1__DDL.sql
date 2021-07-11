CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 1;

CREATE TABLE quotes
(
    id         BIGINT PRIMARY KEY,
    isin       VARCHAR(12) NOT NULL,
    time_stamp TIMESTAMPTZ NOT NULL,
    bid        NUMERIC(15, 2),
    ask        NUMERIC(15, 2) DEFAULT 0,
    CONSTRAINT check_isin_length check (length(isin) = 12)
);

CREATE TABLE elvls
(
    isin       VARCHAR(50) PRIMARY KEY,
    elvl       NUMERIC(15, 2) DEFAULT 0,
    time_stamp TIMESTAMPTZ NOT NULL
);

