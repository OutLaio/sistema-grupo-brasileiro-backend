-- 1. Apagar a tabela existente, se ela existir
DROP TABLE IF EXISTS public.project_users;

CREATE SEQUENCE projects_users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- 2. Criar a nova tabela com os campos desejados
CREATE TABLE public.project_users
(
    id bigint NOT NULL DEFAULT nextval('projects_users_id_seq'::regclass),
    project_id BIGINT NOT NULL,
    client_id BIGINT,
    collaborator_id BIGINT,
    
    -- Adiciona as constraints de chaves estrangeiras
    CONSTRAINT fk_project FOREIGN KEY (project_id)
        REFERENCES public.projects (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE,
        
    CONSTRAINT fk_client FOREIGN KEY (client_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL,

    CONSTRAINT fk_collaborator FOREIGN KEY (collaborator_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
);
