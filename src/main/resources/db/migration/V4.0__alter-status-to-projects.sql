ALTER TABLE public.projects
ALTER COLUMN status TYPE character varying(50) USING status::character varying,
ALTER COLUMN status SET CHECK (status IN ('AF', 'EA', 'AA', 'AP', 'EC', 'CO'));
