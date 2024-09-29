CREATE TABLE "Tb_BInternalCampaigns" (
    id BIGSERIAL PRIMARY KEY,
    id_stationery_type BIGINT NOT NULL,
    id_other_items BIGINT NOT NULL,
    id_briefing BIGINT NOT NULL,
    campaign_motto TEXT NOT NULL,
    CONSTRAINT fk_stationery_type FOREIGN KEY (id_stationery_type) REFERENCES "Tb_StationeryTypes" (id),
    CONSTRAINT fk_other_items FOREIGN KEY (id_other_items) REFERENCES "Tb_OtherItems" (id),
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id)
);