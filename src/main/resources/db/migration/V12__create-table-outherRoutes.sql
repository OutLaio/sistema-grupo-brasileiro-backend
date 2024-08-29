CREATE TABLE outherToutes (
    id BIGSERIAL PRIMARY KEY,
    id_bAgencyBoard INT NOT NULL,
    company VARCHAR(255),
    city VARCHAR(255),
    type VARCHAR(255),
    CONSTRAINT fk_bAgencyBoard FOREIGN KEY (id_bAgencyBoard) REFERENCES bAgencyBoards (id)
);

