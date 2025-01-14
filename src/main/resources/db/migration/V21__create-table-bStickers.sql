CREATE TABLE "Tb_BStickers" (
    id BIGSERIAL PRIMARY KEY,
    id_briefing BIGINT NOT NULL,
    id_sticker_type BIGINT NOT NULL,
    id_sticker_information_type BIGINT,
    sector VARCHAR(255) NOT NULL,
    observations TEXT NOT NULL,
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id),
    CONSTRAINT fk_sticker_type FOREIGN KEY (id_sticker_type) REFERENCES "Tb_StickerTypes" (id),
    CONSTRAINT fk_sticker_information_type FOREIGN KEY (id_sticker_information_type) REFERENCES "Tb_StickerInformationTypes" (id)
);
