create database quanlynhanvien;
use quanlynhanvien;

CREATE TABLE Department (
        department_id VARCHAR(50) PRIMARY KEY,
        department_name VARCHAR(100) NOT NULL UNIQUE,
        description TEXT,
        is_deleted BIT DEFAULT 0
);

CREATE TABLE Employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    salary DOUBLE NOT NULL CHECK (salary > 0),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_deleted BIT DEFAULT 0,
    department_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (department_id) REFERENCES Department(department_id) ON DELETE CASCADE
);


DELIMITER //
CREATE PROCEDURE Proc_Get_All_Departments()
BEGIN
    SELECT * FROM Department WHERE is_deleted = 0;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE Proc_Add_Department(
    IN p_departmentId VARCHAR(50),
    IN p_departmentName VARCHAR(255),
    IN p_description TEXT
)
BEGIN
    INSERT INTO Department (department_id, department_name, description, is_deleted)
    VALUES (p_departmentId, p_departmentName, p_description, FALSE);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE Proc_Update_Department(
    IN p_departmentId VARCHAR(50),
    IN p_departmentName VARCHAR(100),
    IN p_description TEXT
)
BEGIN
    UPDATE Department
        SET department_name = p_departmentName,
            description = p_description
            WHERE department_id = p_departmentId AND is_deleted = 0;
end //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE Proc_Delete_Department(
    IN p_department_id VARCHAR(50)
)
BEGIN
    UPDATE Department
    SET is_deleted = 1
    WHERE department_id = p_department_id;
END //
DELIMITER ;



DELIMITER //
CREATE PROCEDURE Proc_Statistic_Employees_By_Department(
    IN dep_id VARCHAR(5),
    OUT employee_count INT
)
BEGIN
    SELECT COUNT(*) INTO employee_count FROM Employee WHERE department_id = dep_id AND is_deleted = 0;
END //
DELIMITER ;


DELIMITER //

-- 1. Lấy danh sách nhân viên
CREATE PROCEDURE Proc_Get_All_Employees()
BEGIN
    SELECT * FROM Employee WHERE is_deleted = 0;
END //

-- Thêm mới nhân viên
CREATE PROCEDURE Proc_Add_Employee(
    IN p_first_name VARCHAR(50),
    IN p_last_name VARCHAR(50),
    IN p_date_of_birth DATE,
    IN p_phone VARCHAR(20),
    IN p_address VARCHAR(255),
    IN p_salary DOUBLE,
    IN p_department_id VARCHAR(50)
)
BEGIN
    INSERT INTO Employee (first_name, last_name, date_of_birth, phone, address, salary, department_id, created_at, is_deleted)
    VALUES (p_first_name, p_last_name, p_date_of_birth, p_phone, p_address, p_salary, p_department_id, NOW(), FALSE);
END //

-- Cập nhật nhân viên
CREATE PROCEDURE Proc_Update_Employee(
    IN p_employee_id INT,
    IN p_first_name VARCHAR(50),
    IN p_last_name VARCHAR(50),
    IN p_date_of_birth DATE,
    IN p_phone VARCHAR(20),
    IN p_address VARCHAR(255),
    IN p_salary DOUBLE,
    IN p_department_id VARCHAR(50)
)
BEGIN
    UPDATE Employee
    SET first_name = p_first_name,
        last_name = p_last_name,
        date_of_birth = p_date_of_birth,
        phone = p_phone,
        address = p_address,
        salary = p_salary,
        department_id = p_department_id,
        update_at = NOW()
    WHERE employee_id = p_employee_id AND is_deleted = 0;
END //

-- Xóa nhân viên
CREATE PROCEDURE Proc_Delete_Employee(
    IN p_employee_id INT
)
BEGIN
    UPDATE Employee
    SET is_deleted = 1
    WHERE employee_id = p_employee_id;
END //

-- 5. Lấy danh sách nhân viên theo tuổi giảm dần
CREATE PROCEDURE Proc_Get_Employees_By_Age_Desc()
BEGIN
    SELECT *, TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) AS age
    FROM Employee
    WHERE is_deleted = 0
    ORDER BY age DESC;
END //

-- 6. Tìm kiếm nhân viên theo họ hoặc tên
CREATE PROCEDURE Proc_Search_Employee_By_Name(
    IN p_keyword VARCHAR(100)
)
BEGIN
    SELECT * FROM Employee
    WHERE (first_name LIKE CONCAT('%', p_keyword, '%')
        OR last_name LIKE CONCAT('%', p_keyword, '%'))
      AND is_deleted = 0;
END //

-- 7. Thống kê số lượng nhân viên theo độ tuổi
CREATE PROCEDURE Proc_Statistic_Employee_By_Age_Group()
BEGIN
    SELECT
        SUM(CASE WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) BETWEEN 18 AND 29 THEN 1 ELSE 0 END) AS age_18_29,
        SUM(CASE WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) BETWEEN 30 AND 49 THEN 1 ELSE 0 END) AS age_30_49,
        SUM(CASE WHEN TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) >= 50 THEN 1 ELSE 0 END) AS age_50_plus
    FROM Employee
    WHERE is_deleted = 0;
END //

DELIMITER ;




