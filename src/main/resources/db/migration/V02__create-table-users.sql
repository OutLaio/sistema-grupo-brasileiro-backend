CREATE TABLE "Tb_Users" (
    id BIGSERIAL PRIMARY KEY,
    id_profile BIGINT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    disabled BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_profile FOREIGN KEY (id_profile) REFERENCES "Tb_Profiles" (id)
);

