CREATE TABLE "Tb_Routes" (
    id BIGSERIAL PRIMARY KEY,
    id_b_agency_board BIGINT NOT NULL,
    id_company BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    CONSTRAINT fk_b_agency_board FOREIGN KEY (id_b_agency_board) REFERENCES "Tb_BAgencyBoards" (id),
    CONSTRAINT fk_company_route FOREIGN KEY (id_company) REFERENCES "Tb_Companies" (id)
);