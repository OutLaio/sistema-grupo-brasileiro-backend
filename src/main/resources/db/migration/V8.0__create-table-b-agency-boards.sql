CREATE SEQUENCE b_agency_boards_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.b_agency_boards
(
    id bigint NOT NULL DEFAULT nextval('b_agency_boards_id_seq'::regclass),
    board_location character varying(255) NOT NULL,
    company_sharing boolean NOT NULL,
    board_type character varying(50) NOT NULL,
    material character varying(255) NOT NULL,
    observations text,
    project_id bigint NOT NULL,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES public.projects (id),
    CONSTRAINT b_agency_board_pkey PRIMARY KEY (id)
);
