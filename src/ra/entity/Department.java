package ra.entity;

import java.util.Scanner;

public class Department implements IEmployeeManagement{
    private String departmentId;
    private String departmentName;
    private String description;
    private boolean isDeleted;

    public Department() {
    }

    public Department(String departmentId, String departmentName, String description, boolean isDeleted) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public void inputData(Scanner scanner) {
        System.out.println("Nhập ID phòng ban");
        this.departmentId = scanner.nextLine();
        System.out.println("Nhập tên phòng ban");
        this.departmentName = scanner.nextLine();
        System.out.println("Nhập mô tả phòng ban:");
        this.description = scanner.nextLine();
        this.isDeleted = false;
    }

    @Override
    public void displayData() {
            System.out.println("Mã phòng ban: " + departmentId);
            System.out.println("Tên phòng ban: " + departmentName);
            System.out.println("Mô tả: " + description);
            System.out.println("Trạng thái: " + (isDeleted ? "Đã xoá" : "Hoạt động"));
            System.out.println("--------------------------------------");
        }

}
