CREATE TABLE task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    code VARCHAR(255),
    is_deleted TINYINT DEFAULT 0,
    created_at DATE,
    created_by VARCHAR(255),
    updated_at DATE,
    updated_by VARCHAR(255),
    project_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES project (id),
    FOREIGN KEY (user_id) REFERENCES user (id)
);