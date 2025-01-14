CREATE TABLE "Tb_Version" (
    id BIGSERIAL PRIMARY KEY,
    id_briefing BIGINT NOT NULL,
    num_version INT NOT NULL,
    product_link VARCHAR(255) NOT NULL,
    client_approve BOOLEAN,
    supervisor_approve BOOLEAN,
    feedback TEXT,
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id)
);