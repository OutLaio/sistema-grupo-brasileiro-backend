CREATE TABLE "Tb_DialogBoxes" (
    id BIGSERIAL PRIMARY KEY,
    id_employee BIGINT NOT NULL,
    id_briefing BIGINT NOT NULL,
    "time" TIMESTAMP NOT NULL,
    dialog TEXT NOT NULL,
    CONSTRAINT fk_employee FOREIGN KEY (id_employee) REFERENCES "Tb_Employees" (id),
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id)
);