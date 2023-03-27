CREATE TABLE IF NOT EXISTS phase
(
    id    SERIAL,
    phase VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS duty
(
    id       SERIAL,
    position VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS project
(
    id                SERIAL,
    title             VARCHAR(128) NOT NULL,
    parent_project_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS task
(
    id            SERIAL,
    title         VARCHAR(128) NOT NULL,
    description   VARCHAR(1024),
    project_id    BIGINT       NOT NULL,
    owner_id      BIGINT       NOT NULL,
    duty_id       BIGINT       NOT NULL,
    phase_id      BIGINT       NOT NULL,
    created_date  BIGINT       NOT NULL,
    modified_date BIGINT
);

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    role     VARCHAR(32) NOT NULL
);
