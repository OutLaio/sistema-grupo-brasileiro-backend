CREATE SEQUENCE measurements_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.measurements
(
    id bigint NOT NULL DEFAULT nextval('measurements_id_seq'::regclass),
    height float NOT NULL,
    length float NOT NULL,
    b_agency_board_id bigint NOT NULL,
    CONSTRAINT fk_b_agency_board FOREIGN KEY (b_agency_board_id) REFERENCES public.b_agency_boards (id),
    CONSTRAINT measurement_pkey PRIMARY KEY (id)
);
