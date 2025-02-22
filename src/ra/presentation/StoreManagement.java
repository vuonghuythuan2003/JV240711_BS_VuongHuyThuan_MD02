package ra.presentation;

import java.util.Scanner;

public class StoreManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("****************** STORE MANAGEMENT ******************\n" +
                    "1. Quản lý phòng ban\n" +
                    "2. Quản lý nhân viên\n" +
                    "3. Thoát\n");
            System.out.print("Lựa chọn của bạn: ");

            int choice = getUserChoice(sc);
            switch (choice) {
                case 1:
                    departmentManagement(sc);
                    break;
                case 2:
                    employeeManagement(sc);
                    break;
                case 3:
                    System.out.println("Thoát chương trình...");
                    System.exit(0);
                default:
                    System.err.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    private static void departmentManagement(Scanner sc) {
        while (true) {
            System.out.println("********************** DEPARTMENT MENU ********************\n" +
                    "1. Danh sách phòng ban\n" +
                    "2. Tạo mới phòng ban\n" +
                    "3. Cập nhật phòng ban\n" +
                    "4. Xóa phòng ban\n" +
                    "5. Thống kê số lượng nhân viên theo mã phòng ban\n" +
                    "6. Quay lại\n");
            System.out.print("Lựa chọn của bạn: ");

            int choice = getUserChoice(sc);
            switch (choice) {
                case 1:
                    DepartmentManagement.getList();
                    break;
                case 2:
                    DepartmentManagement.addDepartment(sc);
                    break;
                case 3:
                    DepartmentManagement.editDepartment(sc);
                    break;
                case 4:
                    DepartmentManagement.deleteDepartment(sc);
                    break;
                case 5:
                    DepartmentManagement.statisticEmployeesByDepartment(sc);
                    break;
                case 6:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    private static void employeeManagement(Scanner sc) {
        while (true) {
            System.out.println("********************** EMPLOYEE MENU ********************\n" +
                    "1. Danh sách nhân viên\n" +
                    "2. Tạo mới nhân viên\n" +
                    "3. Cập nhật nhân viên\n" +
                    "4. Xóa nhân viên\n" +
                    "5. Hiển thị danh sách nhân viên theo tuổi giảm dần\n" +
                    "6. Tìm kiếm nhân viên theo họ hoặc tên\n" +
                    "7. Thống kê số lượng nhân viên theo độ tuổi\n" +
                    "8. Quay lại\n");
            System.out.print("Lựa chọn của bạn: ");

            int choice = getUserChoice(sc);
            switch (choice) {
                case 1:
                    EmployeeManagement.getList();
                    break;
                case 2:
                    EmployeeManagement.addEmployee(sc);
                    break;
                case 3:
                    EmployeeManagement.editEmployee(sc);
                    break;
                case 4:
                    EmployeeManagement.deleteEmployee(sc);
                    break;
                case 5:
                    EmployeeManagement.getEmployeesByAgeDesc();
                    break;
                case 6:
                    System.out.print("Nhập từ khóa tìm kiếm: ");
                    String keyword = sc.nextLine();
                    EmployeeManagement.searchEmployeesByName(sc);
                    break;
                case 7:
                    EmployeeManagement.statisticEmployeeByAgeGroup();
                    break;
                case 8:
                    return;
                default:
                    System.err.println("Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }
        }
    }

    private static int getUserChoice(Scanner sc) {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Lựa chọn phải là số! Vui lòng nhập lại.");
            return -1;
        }
    }
}
