CREATE TABLE Tb_OtherRoutes (
    id BIGSERIAL PRIMARY KEY,
    id_b_agency_board BIGINT NOT NULL,
    company VARCHAR(255),
    city VARCHAR(255),
    type VARCHAR(255),
    CONSTRAINT fk_b_agency_board FOREIGN KEY (id_b_agency_board) REFERENCES Tb_BAgencyBoards (id)
);
