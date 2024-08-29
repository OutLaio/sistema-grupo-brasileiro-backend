CREATE TABLE companiesCities (
    id BIGSERIAL PRIMARY KEY,
    id_company INT NOT NULL,
    id_city INT NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY (id_company) REFERENCES companies (id),
    CONSTRAINT fk_city FOREIGN KEY (id_city) REFERENCES cities (id)
);

