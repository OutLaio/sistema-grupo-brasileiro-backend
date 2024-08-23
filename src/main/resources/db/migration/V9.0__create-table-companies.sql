
-- Criar a sequência, se ela não existir
CREATE SEQUENCE companies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar a tabela, se ela não existir
CREATE TABLE IF NOT EXISTS public.companies
(
    id bigint NOT NULL DEFAULT nextval('companies_id_seq'::regclass),
    name character varying(255) NOT NULL,
    CONSTRAINT companies_pkey PRIMARY KEY (id)
);


-- Inserção dos dados na tabela
INSERT INTO public.companies (name) VALUES ('ROTA');
INSERT INTO public.companies (name) VALUES ('CIDADE SOL');
INSERT INTO public.companies (name) VALUES ('BRASILEIRO');

COMMIT;
