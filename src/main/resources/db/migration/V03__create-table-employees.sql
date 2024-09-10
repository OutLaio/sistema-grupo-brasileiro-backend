CREATE TABLE Tb_Employees (
    id BIGSERIAL PRIMARY KEY,
    id_user BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    sector VARCHAR(255),
    occupation VARCHAR(255),
    agency VARCHAR(255),
    avatar INT,
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES Tb_Users (id)
);

