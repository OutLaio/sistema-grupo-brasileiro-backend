CREATE SEQUENCE itineraries_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.itineraries
(
    id bigint NOT NULL DEFAULT nextval('itineraries_id_seq'::regclass),
    main character varying(255) NOT NULL,
    connections character varying(255) NOT NULL,
    b_agency_board_id bigint NOT NULL,
    company_id bigint NOT NULL,
    CONSTRAINT fk_b_agency_board FOREIGN KEY (b_agency_board_id) REFERENCES public.b_agency_boards (id),
    CONSTRAINT itineraries_pkey PRIMARY KEY (id)
);
