CREATE TABLE "Tb_Briefings" (
    id BIGSERIAL PRIMARY KEY,
    id_project BIGINT NOT NULL,
    id_briefing_type BIGINT NOT NULL,
    start_time DATE NOT NULL,
    expected_time DATE NOT NULL,
    finish_time DATE,
    detailed_description TEXT NOT NULL,
    other_company VARCHAR(255),
    CONSTRAINT fk_briefing_type FOREIGN KEY (id_briefing_type) REFERENCES "Tb_BriefingTypes"(id),
    CONSTRAINT fk_project FOREIGN KEY (id_project) REFERENCES "Tb_Projects"(id)
);