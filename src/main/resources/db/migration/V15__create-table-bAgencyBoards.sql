CREATE TABLE "Tb_BAgencyBoards" (
    id BIGSERIAL PRIMARY KEY,
    id_agency_board_type BIGINT NOT NULL,
    id_board_type BIGINT,
    id_briefing BIGINT NOT NULL,
    board_location VARCHAR(255) NOT NULL,
    observations TEXT NOT NULL,
    CONSTRAINT fk_agency_board_type FOREIGN KEY (id_agency_board_type) REFERENCES "Tb_AgencyBoardTypes"(id),
    CONSTRAINT fk_board_type FOREIGN KEY (id_board_type) REFERENCES "Tb_BoardTypes"(id),
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings"(id)
);
