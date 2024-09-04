CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    id_client INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50),
    progress DECIMAL(5, 2) CHECK (progress >= 0 AND progress <= 100),
    briefling_type INT,
    disabled BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_client FOREIGN KEY (id_client) REFERENCES employees (id)
);
