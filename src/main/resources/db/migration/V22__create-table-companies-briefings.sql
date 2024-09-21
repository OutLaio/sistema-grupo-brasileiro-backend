CREATE TABLE "Tb_CompaniesBriefings" (
    id BIGSERIAL PRIMARY KEY,
    id_company BIGINT NOT NULL,
    id_briefing BIGINT NOT NULL,
    CONSTRAINT fk_company FOREIGN KEY (id_company) REFERENCES "Tb_Companies" (id),
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id)
);