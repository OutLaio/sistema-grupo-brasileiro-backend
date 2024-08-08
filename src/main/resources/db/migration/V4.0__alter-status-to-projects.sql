ALTER TABLE public.projects
DROP COLUMN status;

ALTER TABLE public.projects
ADD COLUMN status character varying(50) NOT NULL;

ALTER TABLE public.projects
ADD CONSTRAINT status_check CHECK (status IN ('AF', 'EA', 'AA', 'AP', 'EC', 'CO'));
