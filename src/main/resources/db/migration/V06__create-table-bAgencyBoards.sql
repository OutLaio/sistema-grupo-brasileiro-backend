CREATE TABLE bAgencyBoards (
    id BIGSERIAL PRIMARY KEY,
    id_version INT NOT NULL,
    board_location VARCHAR(255),
    company_sharing BOOLEAN NOT NULL DEFAULT FALSE,
    board_type VARCHAR(255),
    material VARCHAR(255),
    observations TEXT,
    CONSTRAINT fk_version FOREIGN KEY (id_version) REFERENCES versions (id)
);

