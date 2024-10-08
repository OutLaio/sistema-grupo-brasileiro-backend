-- Primeiro, criar a tabela se não existir
CREATE TABLE IF NOT EXISTS tb_profiles (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- Em seguida, inserir os dados
INSERT INTO tb_profiles (description) VALUES ('ROLE_SUPERVISOR');
INSERT INTO tb_profiles (description) VALUES ('ROLE_COLLABORATOR');
INSERT INTO tb_profiles (description) VALUES ('ROLE_CLIENT');
