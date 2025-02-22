package ra.entity;

import java.sql.Date;
import java.util.Scanner;

public class Employee implements IEmployeeManagement {
    private int employeeId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phone;
    private String address;
    private double salary;
    private Date createdAt;
    private Date updateAt;
    private boolean isDeleted;
    private String departmentId;

    public Employee() {}

    public Employee(int employeeId, String firstName, String lastName, Date dateOfBirth, String phone, String address,
                    double salary, Date createdAt, Date updateAt, boolean isDeleted, String departmentId) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.isDeleted = isDeleted;
        this.departmentId = departmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public void inputData(Scanner scanner) {
        System.out.print("Nhập họ: ");
        this.lastName = scanner.nextLine();
        System.out.print("Nhập tên: ");
        this.firstName = scanner.nextLine();
        System.out.print("Nhập ngày sinh (yyyy-MM-dd): ");
        this.dateOfBirth = Date.valueOf(scanner.nextLine());
        System.out.print("Nhập số điện thoại: ");
        this.phone = scanner.nextLine();
        System.out.print("Nhập địa chỉ: ");
        this.address = scanner.nextLine();
        System.out.print("Nhập lương: ");
        this.salary = Double.parseDouble(scanner.nextLine());
        System.out.print("Nhập mã phòng ban: ");
        this.departmentId = scanner.nextLine();
        this.createdAt = new Date(System.currentTimeMillis());
        this.updateAt = null;
        this.isDeleted = false;
    }

    @Override
    public void displayData() {
        System.out.printf(
                "ID: %d | Họ: %s | Tên: %s | Ngày sinh: %s | SĐT: %s | Địa chỉ: %s | Lương: %.2f | Phòng ban: %s | Trạng thái: %s | Ngày tạo: %s | Ngày cập nhật: %s\n",
                employeeId, lastName, firstName, dateOfBirth, phone, address, salary, departmentId,
                isDeleted ? "Đã xóa" : "Hoạt động",
                createdAt,
                updateAt != null ? updateAt.toString() : "Chưa cập nhật"
        );
    }

}