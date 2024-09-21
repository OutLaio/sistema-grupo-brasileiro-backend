CREATE TABLE "Tb_Routes" (
    id BIGSERIAL PRIMARY KEY,
    id_b_agency_board BIGINT NOT NULL,
    id_company_city BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    CONSTRAINT fk_b_agency_board FOREIGN KEY (id_b_agency_board) REFERENCES "Tb_BAgencyBoards" (id),
    CONSTRAINT fk_company_city FOREIGN KEY (id_company_city) REFERENCES "Tb_CompaniesCities" (id)
);