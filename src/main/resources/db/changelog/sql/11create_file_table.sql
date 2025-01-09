-- Tạo bảng file
CREATE TABLE file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255),
    file_name VARCHAR(255) NOT NULL,
    uploaded_at DATE,
    uploaded_by VARCHAR(255),
    is_deleted INT DEFAULT 0
);

-- Thêm dữ liệu mẫu vào bảng file
INSERT INTO file (type, file_name, uploaded_at, uploaded_by, is_deleted)
VALUES
    ('image', 'logo.png', '2025-01-01', 'admin', 0),
    ('document', 'report.pdf', '2025-01-02', 'user1', 0),
    ('video', 'tutorial.mp4', '2025-01-03', 'user2', 0),
    ('image', 'banner.jpg', '2025-01-04', 'admin', 0);
