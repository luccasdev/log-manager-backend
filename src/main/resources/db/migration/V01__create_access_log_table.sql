CREATE sequence sq_access_log;

CREATE TABLE tb_access_log_aclo (
    id_access_log bigint NOT NULL,
    created_at timestamp NOT NULL,
    ip_address varchar(20) NOT NULL,
    request_line varchar(45) NOT NULL,
    response_status smallint NOT NULL,
    user_agent varchar(250) NOT NULL,
    CONSTRAINT pk_access_log_aclo PRIMARY KEY (id_access_log)
)