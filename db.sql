-- Create Database
CREATE DATABASE smart_attendance;
USE smart_attendance;

-- ==============================
-- Student Table
-- ==============================
CREATE TABLE student (
    student_id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- ==============================
-- Attendance Table
-- ==============================
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    date DATE DEFAULT CURRENT_DATE,
    status VARCHAR(10) CHECK (status IN ('Present', 'Absent')),
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);

-- ==============================
-- Sample Students (Optional)
-- ==============================
INSERT INTO student (student_id, name) VALUES
(101, 'Rahul Patil'),
(102, 'Sneha Kulkarni'),
(103, 'Amit Deshmukh');

-- ==============================
-- Sample Attendance (Optional)
-- ==============================
INSERT INTO attendance (student_id, status) VALUES
(101, 'Present'),
(101, 'Absent'),
(102, 'Present'),
(103, 'Present');
