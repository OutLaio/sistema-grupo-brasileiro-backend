CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    id_user INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    sector VARCHAR(255),
    occupation VARCHAR(255),
    agency VARCHAR(255),
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users (id)
);

