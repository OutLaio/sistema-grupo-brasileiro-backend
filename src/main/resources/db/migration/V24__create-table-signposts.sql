CREATE TABLE "Tb_BSignposts" (
    id BIGSERIAL PRIMARY KEY,
    id_material BIGINT NOT NULL,
    id_briefing BIGINT NOT NULL,
    board_location VARCHAR(255),
    sector VARCHAR(255),
    CONSTRAINT fk_material FOREIGN KEY (id_material) REFERENCES "Tb_Materials" (id),
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id)
);