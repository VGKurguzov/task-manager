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