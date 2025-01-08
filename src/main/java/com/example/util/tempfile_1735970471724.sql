CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    class_name VARCHAR(50) NOT NULL,
    called_count INT DEFAULT 0,
    correct_count INT DEFAULT 0,
    correct_rate DOUBLE DEFAULT 0.0
);
