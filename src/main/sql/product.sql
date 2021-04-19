CREATE TABLE product
(
    sku_id bigint,
    name text COLLATE ,
    description text,
    created_at NOT NULL,
    CONSTRAINT sku_id_fk FOREIGN KEY (sku_id)
        REFERENCES public.sku_lookup (sku_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)