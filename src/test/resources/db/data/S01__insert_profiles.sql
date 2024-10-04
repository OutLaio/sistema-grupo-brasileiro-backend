INSERT INTO tb_profiles (description) VALUES ('ROLE_SUPERVISOR');
INSERT INTO tb_profiles (description) VALUES ('ROLE_COLLABORATOR');
INSERT INTO tb_profiles (description) VALUES ('ROLE_CLIENT');

CREATE TABLE IF NOT EXISTS tb_profiles (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

INSERT INTO tb_profiles (description) VALUES ('ROLE_SUPERVISOR');