CREATE TABLE sku_lookup
(
    sku text COLLATE pg_catalog."default" NOT NULL,
    sku_id bigint NOT NULL,
    created_at text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT sku_lookup_pkey PRIMARY KEY (sku_id)
)