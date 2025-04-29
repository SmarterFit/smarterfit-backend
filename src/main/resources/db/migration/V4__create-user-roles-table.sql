CREATE TABLE SF_USER_ROLE (
    user_id UUID NOT NULL,
    role_type role_type NOT NULL,
    PRIMARY KEY (user_id, role_type),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES SF_USER(id)
);
