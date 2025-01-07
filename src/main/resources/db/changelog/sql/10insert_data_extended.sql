-- Insert default projects
INSERT INTO project (id, name, code, is_deleted, created_at, created_by, updated_at, updated_by)
VALUES
(1, 'Project Alpha', 'PROJ_ALPHA', 0, CURRENT_DATE, 'system', CURRENT_DATE, 'system'),
(2, 'Project Beta', 'PROJ_BETA', 0, CURRENT_DATE, 'system', CURRENT_DATE, 'system');

-- Insert default tasks
INSERT INTO task (id, name, code, is_deleted, created_at, created_by, updated_at, updated_by, project_id, user_id)
VALUES
(1, 'Task 1', 'TASK_1', 0, CURRENT_DATE, 'system', CURRENT_DATE, 'system', 1, 1), -- Task 1 -> Project Alpha, User: Admin
(2, 'Task 2', 'TASK_2', 0, CURRENT_DATE, 'system', CURRENT_DATE, 'system', 1, NULL), -- Task 2 -> Project Alpha, No User Assigned
(3, 'Task 3', 'TASK_3', 0, CURRENT_DATE, 'system', CURRENT_DATE, 'system', 2, 2); -- Task 3 -> Project Beta, User: Regular User

-- Map users to projects
INSERT INTO user_project (user_id, project_id)
VALUES
(1, 1), -- Admin User -> Project Alpha
(2, 2); -- Regular User -> Project Beta
