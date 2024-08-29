CREATE TABLE versions (
    id BIGSERIAL PRIMARY KEY,
    id_collaborator INT NULL,
    id_project INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    feedback TEXT,
    'begin' DATE,
    'end' DATE,
    num_version INT,
    product_link TEXT,
    client_approve BOOLEAN NOT NULL DEFAULT FALSE,
    supervisor_approve BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_collaborator FOREIGN KEY (id_collaborator) REFERENCES employees (id),
    CONSTRAINT fk_project FOREIGN KEY (id_project) REFERENCES projects (id)
);

