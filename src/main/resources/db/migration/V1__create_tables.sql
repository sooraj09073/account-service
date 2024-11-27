CREATE TABLE IF NOT EXISTS account_user
(
    user_id BIGSERIAL PRIMARY KEY,
    email_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS user_address
(
    id BIGSERIAL PRIMARY KEY,
    address_line1 VARCHAR(225),
    address_line2 VARCHAR(255),
    city VARCHAR(100),
    country VARCHAR(255),
    postal_code VARCHAR(20),
    state VARCHAR (50),
    account_user_id BIGINT NOT NULL,
    CONSTRAINT account_user_fk FOREIGN KEY (account_user_id)
    REFERENCES account_user(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_auth
(
    id BIGSERIAL PRIMARY KEY,
    enabled BOOLEAN NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role
(
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(10) NOT NULL,
    user_auth_id BIGINT NOT NULL,
    CONSTRAINT user_auth_fk FOREIGN KEY (user_auth_id)
    REFERENCES user_auth(id) ON DELETE CASCADE
);