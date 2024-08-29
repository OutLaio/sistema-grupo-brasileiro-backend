CREATE TABLE routes (
    id BIGSERIAL PRIMARY KEY,
    id_bAgencyBoard INT NOT NULL,
    id_citycompany INT NOT NULL,
    type VARCHAR(255),
    CONSTRAINT fk_bAgencyBoard FOREIGN KEY (id_bAgencyBoard) REFERENCES bAgencyBoards (id),
    CONSTRAINT fk_citycompany FOREIGN KEY (id_citycompany) REFERENCES companiesCities (id)
);

