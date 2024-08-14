ALTER TABLE public.project_users
DROP COLUMN user_id;

ALTER TABLE public.project_users
ADD COLUMN collaborator_id bigint;

ALTER TABLE public.project_users
ADD COLUMN client_id bigint;
