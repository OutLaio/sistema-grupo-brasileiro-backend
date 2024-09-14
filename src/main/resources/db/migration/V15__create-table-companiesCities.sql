CREATE TABLE "Tb_CompaniesCities" (
    id BIGSERIAL PRIMARY KEY,
    id_company INT NOT NULL,
    id_city INT NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY (id_company) REFERENCES "Tb_Companies" (id),
    CONSTRAINT fk_city FOREIGN KEY (id_city) REFERENCES "Tb_Cities" (id)
);

