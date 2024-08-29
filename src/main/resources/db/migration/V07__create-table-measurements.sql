CREATE TABLE measurements (
    id BIGSERIAL PRIMARY KEY,
    height DECIMAL(10, 2),
    length DECIMAL(10, 2),
    id_bAgencyBoard INT NOT NULL,
    CONSTRAINT fk_bAgencyBoard FOREIGN KEY (id_bAgencyBoard) REFERENCES bAgencyBoards (id)
);

