CREATE DATABASE Students;
USE Students;

-- Tạo bảng Students
CREATE TABLE Students(
	student_id INT auto_increment primary key,
    full_name varchar(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    email varchar(100) NOT NULL UNIQUE
);

-- Lấy tất cả sinh viên
DELIMITER //
CREATE PROCEDURE get_all_students()
BEGIN
    SELECT * FROM Students;
END //
DELIMITER ;

-- Thêm sinh viên
DELIMITER //
CREATE PROCEDURE add_student(
    IN in_full_name VARCHAR(100),
    IN in_date_of_birth DATE,
    IN in_email VARCHAR(100)
)
BEGIN
    INSERT INTO Students(full_name, date_of_birth, email)
    VALUES (in_full_name, in_date_of_birth, in_email);
END //
DELIMITER ;

-- Cập nhật sinh viên
DELIMITER //
CREATE PROCEDURE update_student(
    IN in_id INT,
    IN in_full_name VARCHAR(100),
    IN in_date_of_birth DATE,
    IN in_email VARCHAR(100)
)
BEGIN
    UPDATE Students
    SET full_name = in_full_name,
        date_of_birth = in_date_of_birth,
        email = in_email
    WHERE student_id = in_id;
END //
DELIMITER ;

-- Tìm sinh viên theo ID
DELIMITER //
CREATE PROCEDURE find_student_by_id(
    IN in_id INT
)
BEGIN
    SELECT * FROM Students
    WHERE student_id = in_id;
END //
DELIMITER ;

-- Xóa sinh viên theo ID
DELIMITER //
CREATE PROCEDURE delete_student(
    IN in_id INT
)
BEGIN
    DELETE FROM Students
    WHERE student_id = in_id;
END //
DELIMITER ;
