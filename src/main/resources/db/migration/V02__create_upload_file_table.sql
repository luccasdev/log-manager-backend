CREATE sequence sq_upload_file;

CREATE TABLE tb_upload_file_upfi (
    id_upload_file bigint NOT NULL,
    upload_at timestamp NOT NULL,
    import_process_status smallint NOT NULL,
    CONSTRAINT pk_upload_file_upfi PRIMARY KEY (id_upload_file)
)