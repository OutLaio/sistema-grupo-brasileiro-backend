CREATE TABLE "Tb_RoutesCities" (
    id BIGSERIAL PRIMARY KEY,
    id_route BIGINT NOT NULL,
    id_city BIGINT NOT NULL,
    CONSTRAINT fk_route FOREIGN KEY (id_route) REFERENCES "Tb_Routes" (id),
    CONSTRAINT fk_city FOREIGN KEY (id_city) REFERENCES "Tb_Cities" (id)
);

