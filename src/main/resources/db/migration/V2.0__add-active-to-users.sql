-- Adiciona a coluna active à tabela users
ALTER TABLE public.users
ADD COLUMN active boolean NOT NULL DEFAULT true;
