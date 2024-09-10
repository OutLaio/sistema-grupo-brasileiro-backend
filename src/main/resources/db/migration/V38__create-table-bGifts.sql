CREATE TABLE Tb_BPrinteds (
    id BIGSERIAL PRIMARY KEY,
    id_briefing BIGINT NOT NULL,
    id_printed_types BIGINT NOT NULL,
    id_printing_types BIGINT NOT NULL,
    paper_type VARCHAR(255),
    folds INT,
    pages INT,
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES Tb_Briefings (id),
    CONSTRAINT fk_printed_types FOREIGN KEY (id_printed_types) REFERENCES Tb_PrintedTypes (id),
    CONSTRAINT fk_printing_types FOREIGN KEY (id_printing_types) REFERENCES Tb_PrintingTypes (id)
);