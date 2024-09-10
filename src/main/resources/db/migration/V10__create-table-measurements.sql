CREATE TABLE Tb_measurements (
    id BIGSERIAL PRIMARY KEY,
    id_briefing BIGINT NOT NULL,
    height DECIMAL(10, 2),
    length DECIMAL(10, 2),
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES Tb_Briefings (id)
);
