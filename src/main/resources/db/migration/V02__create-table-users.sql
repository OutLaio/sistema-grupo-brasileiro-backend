CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    disabled BOOLEAN NOT NULL DEFAULT FALSE,
    id_profile INT,
    CONSTRAINT fk_profile FOREIGN KEY (id_profile) REFERENCES profiles (id)
);

