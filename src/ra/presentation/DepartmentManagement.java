package ra.presentation;

import ra.business.DepartmentBusiness;
import ra.entity.Department;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DepartmentManagement {

    public static void getList() {
        DepartmentBusiness business = new DepartmentBusiness();
        List<Department> list = business.getAllDepartments();
        list.forEach(Department::displayData);
    }

    public static void addDepartment(Scanner sc) {
        System.out.print("Nhập ID phòng ban: ");
        Department department = new Department();
        department.setDepartmentId(sc.nextLine());
        System.out.print("Nhập tên phòng ban: ");
        department.setDepartmentName(sc.nextLine());

        System.out.print("Nhập mô tả: ");
        department.setDescription(sc.nextLine());

        department.setDeleted(false);

        DepartmentBusiness business = new DepartmentBusiness();
        if (business.addDepartment(department)) {
            System.out.println("Thêm phòng ban thành công!");
        } else {
            System.err.println("Thêm phòng ban thất bại!");
        }
    }

    public static void editDepartment(Scanner sc) {
        DepartmentBusiness business = new DepartmentBusiness();
        List<Department> list = business.getAllDepartments();
        if (list.isEmpty()) {
            System.err.println("Không có phòng ban nào để cập nhật!");
            return;
        }

        int choice = chooseDepartment(sc, list, "Chọn phòng ban cần cập nhật");
        if (choice == -1) return;

        Department department = list.get(choice);
        while (true) {
            System.out.println("Chọn thông tin cần cập nhật:\n1. Tên phòng ban\n2. Mô tả\n3. Trạng thái\n4. Thoát");
            System.out.print("Lựa chọn: ");

            try {
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1:
                        System.out.print("Nhập tên mới: ");
                        department.setDepartmentName(sc.nextLine());
                        break;
                    case 2:
                        System.out.print("Nhập mô tả mới: ");
                        department.setDescription(sc.nextLine());
                        break;
                    case 3:
                        department.setDeleted(!department.isDeleted());
                        System.out.println("Trạng thái cập nhật: " + (department.isDeleted() ? "Đã xoá" : "Hoạt động"));
                        break;
                    case 4:
                        if (business.updateDepartment(department)) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        return;
                    default:
                        System.err.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    public static void deleteDepartment(Scanner sc) {
        DepartmentBusiness business = new DepartmentBusiness();
        List<Department> list = business.getAllDepartments();
        if (list.isEmpty()) {
            System.err.println("Không có phòng ban nào để xoá!");
            return;
        }

        int choice = chooseDepartment(sc, list, "Chọn phòng ban cần xoá");
        if (choice == -1) return;

        Department department = list.get(choice);
        System.out.printf("Bạn có chắc chắn muốn xoá phòng ban '%s'? (1. Đồng ý, 2. Không): ", department.getDepartmentName());

        try {
            int confirm = Integer.parseInt(sc.nextLine());
            if (confirm == 1) {
                if (business.deleteDepartment(department.getDepartmentId())) {
                    System.out.println("Xoá thành công!");
                } else {
                    System.err.println("Xoá thất bại!");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số hợp lệ!");
        }
    }

    public static void statisticEmployeesByDepartment(Scanner scanner) {
        System.out.print("Nhập mã phòng ban cần thống kê: ");
        String departmentId = scanner.nextLine();
        int employeeCount = DepartmentBusiness.countEmployeesByDepartment(departmentId);
        System.out.printf("Phòng ban %s có %d nhân viên.\n", departmentId, employeeCount);
    }


    private static int chooseDepartment(Scanner sc, List<Department> list, String message) {
        System.out.println(message);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, list.get(i).getDepartmentName());
        }

        while (true) {
            try {
                System.out.print("Lựa chọn: ");
                int choice = Integer.parseInt(sc.nextLine()) - 1;
                if (choice >= 0 && choice < list.size()) {
                    return choice;
                }
                System.err.println("Lựa chọn không hợp lệ!");
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }
}
