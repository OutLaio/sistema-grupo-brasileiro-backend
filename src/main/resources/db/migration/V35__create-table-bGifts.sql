CREATE TABLE "Tb_BGifts" (
    id BIGSERIAL PRIMARY KEY,
    id_briefing BIGINT NOT NULL,
    id_gift_type BIGINT NOT NULL,
    id_printing_type BIGINT,
    id_printing_shirt_type BIGINT,
    id_stamp BIGINT,
    id_calendar_type BIGINT,
    gift_model TEXT,
    link_model TEXT,
    CONSTRAINT fk_briefing FOREIGN KEY (id_briefing) REFERENCES "Tb_Briefings" (id),
    CONSTRAINT fk_gift_type FOREIGN KEY (id_gift_type) REFERENCES "Tb_GiftTypes" (id),
    CONSTRAINT fk_printing_type FOREIGN KEY (id_printing_type) REFERENCES "Tb_PrintingTypes" (id),
    CONSTRAINT fk_printing_shirt_type FOREIGN KEY (id_printing_shirt_type) REFERENCES "Tb_PrintingShirtTypes" (id),
    CONSTRAINT fk_stamp FOREIGN KEY (id_stamp) REFERENCES "Tb_Stamps" (id),
    CONSTRAINT fk_calendar_type FOREIGN KEY (id_calendar_type) REFERENCES "Tb_CalendarTypes" (id)
);