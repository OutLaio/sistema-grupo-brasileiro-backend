CREATE TABLE "Tb_Projects" (
    id BIGSERIAL PRIMARY KEY,
    id_collaborator BIGINT,
    id_client BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    disabled BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_collaborator FOREIGN KEY (id_collaborator) REFERENCES "Tb_Employees" (id),
    CONSTRAINT fk_client FOREIGN KEY (id_client) REFERENCES "Tb_Employees" (id)
);