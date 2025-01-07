CREATE TABLE user_project (
    user_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, project_id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (project_id) REFERENCES project (id)
);