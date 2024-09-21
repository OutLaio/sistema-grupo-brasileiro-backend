CREATE TABLE "Tb_Employees" (
    id BIGSERIAL PRIMARY KEY,
    id_user BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    sector VARCHAR(255) NOT NULL,
    occupation VARCHAR(255) NOT NULL,
    agency VARCHAR(255) NOT NULL,
    avatar INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES "Tb_Users" (id)
);

