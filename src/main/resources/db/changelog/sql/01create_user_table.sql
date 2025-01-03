CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    is_deleted TINYINT DEFAULT 0,
    created_at DATE,
    created_by VARCHAR(255),
    updated_at DATE,
    updated_by VARCHAR(255),
    avatar VARCHAR(255)
);
