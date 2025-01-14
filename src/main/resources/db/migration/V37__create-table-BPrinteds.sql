CREATE TABLE "Tb_BPrinteds" (
    id BIGSERIAL PRIMARY KEY,
    id_briefing BIGINT NOT NULL,
    id_printed_type BIGINT NOT NULL,
    id_printing_type BIGINT,
    paper_type VARCHAR(80),
    folds INT,
    pages INT,
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id),
    CONSTRAINT fk_printed_types FOREIGN KEY (id_printed_type) REFERENCES "Tb_PrintedTypes" (id),
    CONSTRAINT fk_printing_types FOREIGN KEY (id_printing_type) REFERENCES "Tb_PrintingTypes" (id)
);