-- Tabela para Project
CREATE TABLE IF NOT EXISTS public."Tb_Projects" (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(255),
    disabled BOOLEAN NOT NULL,
    id_collaborator INTEGER,
    id_client INTEGER NOT NULL,
    FOREIGN KEY (id_collaborator) REFERENCES public."Tb_Employees" (id),
    FOREIGN KEY (id_client) REFERENCES public."Tb_Employees" (id)
);

-- Tabela para BriefingType
CREATE TABLE IF NOT EXISTS public."Tb_BriefingTypes" (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- Tabela para Briefing
CREATE TABLE IF NOT EXISTS public."Tb_Briefings" (
    id SERIAL PRIMARY KEY,
    detailed_description TEXT,
    start_time DATE,
    expected_time DATE,
    other_company VARCHAR(255),
    id_briefing_type INTEGER NOT NULL,
    id_project INTEGER NOT NULL,
    FOREIGN KEY (id_briefing_type) REFERENCES public."Tb_BriefingTypes" (id),
    FOREIGN KEY (id_project) REFERENCES public."Tb_Projects" (id)
);

-- Tabela para GiftType
CREATE TABLE IF NOT EXISTS public."Tb_GiftTypes" (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- Tabela para PrintingType
CREATE TABLE IF NOT EXISTS public."Tb_PrintingTypes" (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- Tabela para PrintingShirtType
CREATE TABLE IF NOT EXISTS public."Tb_PrintingShirtTypes" (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- Tabela para Stamp
CREATE TABLE IF NOT EXISTS public."Tb_Stamps" (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- Tabela para CalendarType
CREATE TABLE IF NOT EXISTS public."Tb_CalendarTypes" (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

-- Tabela para BGift
CREATE TABLE IF NOT EXISTS public."Tb_BGifts" (
    id SERIAL PRIMARY KEY,
    gift_model VARCHAR(255),
    link_model VARCHAR(255),
    id_briefing INTEGER NOT NULL,
    id_gift_type INTEGER,
    id_printing_type INTEGER,
    id_printing_shirt_type INTEGER,
    id_stamp INTEGER,
    id_calendar_type INTEGER,
    FOREIGN KEY (id_briefing) REFERENCES public."Tb_Briefings" (id),
    FOREIGN KEY (id_gift_type) REFERENCES public."Tb_GiftTypes" (id),
    FOREIGN KEY (id_printing_type) REFERENCES public."Tb_PrintingTypes" (id),
    FOREIGN KEY (id_printing_shirt_type) REFERENCES public."Tb_PrintingShirtTypes" (id),
    FOREIGN KEY (id_stamp) REFERENCES public."Tb_Stamps" (id),
    FOREIGN KEY (id_calendar_type) REFERENCES public."Tb_CalendarTypes" (id)
);

ALTER SEQUENCE public."Tb_Projects_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_BriefingTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_Briefings_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_GiftTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_PrintingTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_PrintingShirtTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_Stamps_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_CalendarTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_BGifts_id_seq" RESTART WITH 1;

TRUNCATE TABLE public."Tb_BGifts" CASCADE;
TRUNCATE TABLE public."Tb_Briefings" CASCADE;
TRUNCATE TABLE public."Tb_Projects" CASCADE;
TRUNCATE TABLE public."Tb_BriefingTypes" CASCADE;
TRUNCATE TABLE public."Tb_GiftTypes" CASCADE;
TRUNCATE TABLE public."Tb_PrintingTypes" CASCADE;
TRUNCATE TABLE public."Tb_PrintingShirtTypes" CASCADE;
TRUNCATE TABLE public."Tb_Stamps" CASCADE;
TRUNCATE TABLE public."Tb_CalendarTypes" CASCADE;

DROP TABLE IF EXISTS public."Tb_BGifts" CASCADE;
DROP TABLE IF EXISTS public."Tb_Briefings" CASCADE;
DROP TABLE IF EXISTS public."Tb_Projects" CASCADE;
DROP TABLE IF EXISTS public."Tb_BriefingTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_GiftTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_PrintingTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_PrintingShirtTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_Stamps" CASCADE;
DROP TABLE IF EXISTS public."Tb_CalendarTypes" CASCADE;

-- Dropar tabelas existentes
DROP TABLE IF EXISTS public."Tb_BGifts" CASCADE;
DROP TABLE IF EXISTS public."Tb_Briefings" CASCADE;
DROP TABLE IF EXISTS public."Tb_Projects" CASCADE;
DROP TABLE IF EXISTS public."Tb_BriefingTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_GiftTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_PrintingTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_PrintingShirtTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_Stamps" CASCADE;
DROP TABLE IF EXISTS public."Tb_CalendarTypes" CASCADE;
DROP TABLE IF EXISTS public."Tb_Employees" CASCADE;
DROP TABLE IF EXISTS public."Tb_Profiles" CASCADE;

-- Criar tabelas
-- (seus comandos CREATE TABLE aqui)

-- Truncar tabelas (caso já existam dados)
TRUNCATE TABLE public."Tb_BGifts" CASCADE;
TRUNCATE TABLE public."Tb_Briefings" CASCADE;
TRUNCATE TABLE public."Tb_Projects" CASCADE;
TRUNCATE TABLE public."Tb_BriefingTypes" CASCADE;
TRUNCATE TABLE public."Tb_GiftTypes" CASCADE;
TRUNCATE TABLE public."Tb_PrintingTypes" CASCADE;
TRUNCATE TABLE public."Tb_PrintingShirtTypes" CASCADE;
TRUNCATE TABLE public."Tb_Stamps" CASCADE;
TRUNCATE TABLE public."Tb_CalendarTypes" CASCADE;
TRUNCATE TABLE public."Tb_Employees" CASCADE;
TRUNCATE TABLE public."Tb_Profiles" CASCADE;

-- Reiniciar sequências
ALTER SEQUENCE public."Tb_Projects_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_BriefingTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_Briefings_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_GiftTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_PrintingTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_PrintingShirtTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_Stamps_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_CalendarTypes_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_BGifts_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_Employees_id_seq" RESTART WITH 1;
ALTER SEQUENCE public."Tb_Profiles_id_seq" RESTART WITH 1;

-- Dropar tabelas existentes
DROP TABLE IF EXISTS "Tb_BGifts" CASCADE;
DROP TABLE IF EXISTS "Tb_Briefings" CASCADE;
DROP TABLE IF EXISTS "Tb_Projects" CASCADE;
DROP TABLE IF EXISTS "Tb_BriefingTypes" CASCADE;
DROP TABLE IF EXISTS "Tb_GiftTypes" CASCADE;
DROP TABLE IF EXISTS "Tb_PrintingTypes" CASCADE;
DROP TABLE IF EXISTS "Tb_PrintingShirtTypes" CASCADE;
DROP TABLE IF EXISTS "Tb_Stamps" CASCADE;
DROP TABLE IF EXISTS "Tb_CalendarTypes" CASCADE;
DROP TABLE IF EXISTS "Tb_Employees" CASCADE;
DROP TABLE IF EXISTS "Tb_Users" CASCADE;
DROP TABLE IF EXISTS "Tb_Profiles" CASCADE;

-- Criar tabelas
CREATE TABLE "Tb_Profiles" (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE "Tb_Users" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    disabled BOOLEAN NOT NULL,
    id_profile INTEGER NOT NULL,
    FOREIGN KEY (id_profile) REFERENCES "Tb_Profiles" (id)
);

CREATE TABLE "Tb_Employees" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    avatar INTEGER NOT NULL,
    agency VARCHAR(255) NOT NULL,
    occupation VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    sector VARCHAR(255) NOT NULL,
    id_user INTEGER NOT NULL,
    FOREIGN KEY (id_user) REFERENCES "Tb_Users" (id)
);

CREATE TABLE "Tb_Projects" (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(255),
    disabled BOOLEAN NOT NULL,
    id_collaborator INTEGER,
    id_client INTEGER NOT NULL,
    FOREIGN KEY (id_collaborator) REFERENCES "Tb_Employees" (id),
    FOREIGN KEY (id_client) REFERENCES "Tb_Employees" (id)
);

CREATE TABLE "Tb_BriefingTypes" (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE "Tb_Briefings" (
    id SERIAL PRIMARY KEY,
    detailed_description TEXT,
    start_time DATE,
    expected_time DATE,
    other_company VARCHAR(255),
    id_briefing_type INTEGER NOT NULL,
    id_project INTEGER NOT NULL,
    FOREIGN KEY (id_briefing_type) REFERENCES "Tb_BriefingTypes" (id),
    FOREIGN KEY (id_project) REFERENCES "Tb_Projects" (id)
);

   @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

   
   