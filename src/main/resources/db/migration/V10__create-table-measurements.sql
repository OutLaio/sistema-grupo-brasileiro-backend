CREATE TABLE "Tb_Measurements" (
    id BIGSERIAL PRIMARY KEY,
    id_briefing BIGINT NOT NULL,
    height DECIMAL(10, 2) NOT NULL,
    length DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id)
);
