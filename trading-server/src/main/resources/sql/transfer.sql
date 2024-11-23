CREATE TABLE transfers
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id   VARCHAR(255)   NOT NULL UNIQUE,
    sender_id        VARCHAR(255)   NOT NULL,
    receiver_id      VARCHAR(255)   NOT NULL,
    source_currency  VARCHAR(10)    NOT NULL,
    target_currency  VARCHAR(10)    NOT NULL,
    amount           DECIMAL(19, 4) NOT NULL,
    converted_amount DECIMAL(19, 4) NOT NULL,
    exchange_rate    DECIMAL(19, 6) NOT NULL,
    fee              DECIMAL(19, 4) NOT NULL,
    description      TEXT,
    status           VARCHAR(50)    NOT NULL,
    created_at       DATETIME       NOT NULL,
    updated_at       DATETIME
);


