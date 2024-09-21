CREATE TABLE "Tb_BHandouts" (
    id BIGSERIAL PRIMARY KEY,
    id_handout_type BIGINT NOT NULL,
    id_briefing BIGINT NOT NULL,
    CONSTRAINT fk_handout_type FOREIGN KEY (id_handout_type) REFERENCES "Tb_HandoutTypes" (id),
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id)
);