-- Insert default roles
INSERT INTO role (id, name, code, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
(1, 'ROLE_ADMIN', 'ADMIN', CURRENT_DATE, 'system', CURRENT_DATE, 'system', 0),
(2, 'ROLE_USER', 'USER', CURRENT_DATE, 'system', CURRENT_DATE, 'system', 0);

-- Insert default permissions
INSERT INTO permission (id, name, code, created_at, created_by, updated_at, updated_by, is_deleted)
VALUES
(1, 'Manage Users', 'PERM_MANAGE_USERS', CURRENT_DATE, 'system', CURRENT_DATE, 'system', 0),
(2, 'View Dashboard', 'PERM_VIEW_DASHBOARD', CURRENT_DATE, 'system', CURRENT_DATE, 'system', 0);

-- Insert default users
-- adminpassword
-- userpassword
INSERT INTO user (id, name, email, password, is_deleted, created_at, created_by, updated_at, updated_by, avatar)
VALUES
(1, 'Admin User', 'admin@example.com', '$2a$10$9rGMuQMvMlOUkji1DVPX4OqJ6tiGlc0hHYyiHMqK7gArUkrkQtWsC
', 0, CURRENT_DATE, 'system', CURRENT_DATE, 'system', 'default_avatar.png'),
(2, 'Regular User', 'user@example.com', '$2a$10$9rGMuQMvMlOUkji1DVPX4OqJ6tiGlc0hHYyiHMqK7gArUkrkQtWsC
', 0, CURRENT_DATE, 'system', CURRENT_DATE, 'system', 'default_avatar.png');

INSERT INTO role_permission (role_id, permission_id)
VALUES
(1, 1),
(1, 2),
(2, 2);

-- Map users to roles
INSERT INTO user_role (user_id, role_id)
VALUES
(1, 1), -- Admin User -> ROLE_ADMIN
(2, 2); -- Regular User -> ROLE_USER
