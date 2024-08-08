CREATE SEQUENCE projects_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.projects
(
    id bigint NOT NULL DEFAULT nextval('projects_id_seq'::regclass),
    title character varying(255) NOT NULL,
    description text NOT NULL,
    details text,
    progress integer CHECK (progress >= 0 AND progress <= 100),
    status character varying(50) NOT NULL CHECK (status IN ('A Fazer', 'Em Andamento', 'Aguardando Aprovação', 'Aprovado', 'Em Confecção', 'Concluído')),
    CONSTRAINT projects_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.project_users
(
    project_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES public.projects (id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public.users (id) ON DELETE CASCADE,
    PRIMARY KEY (project_id, user_id)
);
