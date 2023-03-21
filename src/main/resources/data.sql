CREATE TABLE IF NOT EXISTS phase
(
    id    IDENTITY,
    phase VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS duty
(
    id       IDENTITY,
    position VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS project
(
    id                IDENTITY,
    title             VARCHAR(128) NOT NULL,
    parent_project_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS task
(
    id            IDENTITY,
    title         VARCHAR(255) NOT NULL,
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
    id       IDENTITY,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    role     VARCHAR(32) NOT NULL
);

INSERT INTO phase (id, phase)
VALUES (1, 'New');
INSERT INTO phase (id, phase)
VALUES (2, 'Progress');
INSERT INTO phase (id, phase)
VALUES (3, 'Done');

INSERT INTO duty (id, position)
VALUES (1, 'Manager');
INSERT INTO duty (id, position)
VALUES (2, 'Technical Specialist');

INSERT INTO users (id, username, password, role)
VALUES (1, 'admin', 'password', 'ROLE_ADMIN');
INSERT INTO users (id, username, password, role)
VALUES (2, 'user', 'password', 'ROLE_USER');

INSERT INTO project (id, title, parent_project_id)
VALUES (1, 'Первый тестовый проект', null);
INSERT INTO project (id, title, parent_project_id)
VALUES (2, 'Второй тестовый проект', 1);

INSERT INTO task (id, title, description, project_id, owner_id, duty_id, phase_id, created_date, modified_date)
VALUES (1, 'Первая тестовая задача', 'Описание', 1, 1, 1, 1, 1679438057797, null);