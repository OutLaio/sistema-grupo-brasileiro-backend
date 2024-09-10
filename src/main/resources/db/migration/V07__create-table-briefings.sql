CREATE TABLE Tb_Briefings (
    id BIGSERIAL PRIMARY KEY,
    id_project BIGINT NOT NULL,
    id_briefing_types BIGINT NOT NULL,
    start_time DATE,
    expected_time DATE,
    finish_time DATE,
    detailed_description TEXT,
    other_company VARCHAR(255),
    CONSTRAINT fk_briefing_types FOREIGN KEY (id_briefing_types) REFERENCES Tb_BriefingTypes(id),
    CONSTRAINT fk_project FOREIGN KEY (id_project) REFERENCES Tb_Projects(id)
);