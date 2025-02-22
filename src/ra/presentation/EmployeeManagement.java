package ra.presentation;

import ra.business.DepartmentBusiness;
import ra.business.EmployeeBusiness;
import ra.entity.Department;
import ra.entity.Employee;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeeManagement {

    public static void getList() {
        EmployeeBusiness business = new EmployeeBusiness();
        List<Employee> list = business.findAll();
        list.forEach(Employee::displayData);

    }

    public static void addEmployee(Scanner sc) {
        Employee employee = new Employee();

        System.out.print("Nhập họ: ");
        employee.setFirstName(sc.nextLine());
        System.out.print("Nhập tên: ");
        employee.setLastName(sc.nextLine());
        System.out.print("Nhập ngày sinh (YYYY-MM-DD): ");
        employee.setDateOfBirth(java.sql.Date.valueOf(sc.nextLine()));
        System.out.print("Nhập số điện thoại: ");
        employee.setPhone(sc.nextLine());
        System.out.print("Nhập địa chỉ: ");
        employee.setAddress(sc.nextLine());
        System.out.print("Nhập lương: ");
        employee.setSalary(Double.parseDouble(sc.nextLine()));
        System.out.print("Nhập mã phòng ban: ");
        employee.setDepartmentId(sc.nextLine());

        EmployeeBusiness business = new EmployeeBusiness();
        if (business.addEmployee(employee)) {
            System.out.println("Thêm nhân viên thành công!");
        } else {
            System.err.println("Thêm nhân viên thất bại!");
        }
    }

    public static void editEmployee(Scanner sc) {
        EmployeeBusiness business = new EmployeeBusiness();
        List<Employee> list = business.findAll();
        if (list.isEmpty()) {
            System.err.println("Không có nhân viên nào để cập nhật!");
            return;
        }

        int choice = chooseEmployee(sc, list, "Chọn nhân viên cần cập nhật");
        if (choice == -1) return;

        Employee employee = list.get(choice);
        while (true) {
            System.out.println("Chọn thông tin cần cập nhật:\n1. Họ\n2. Tên\n3. Ngày sinh\n4. Số điện thoại\n5. Địa chỉ\n6. Lương\n7. Phòng ban\n8. Thoát");
            System.out.print("Lựa chọn: ");

            try {
                int option = Integer.parseInt(sc.nextLine());
                switch (option) {
                    case 1 -> {
                        System.out.print("Nhập họ mới: ");
                        employee.setFirstName(sc.nextLine());
                    }
                    case 2 -> {
                        System.out.print("Nhập tên mới: ");
                        employee.setLastName(sc.nextLine());
                    }
                    case 3 -> {
                        System.out.print("Nhập ngày sinh mới (YYYY-MM-DD): ");
                        employee.setDateOfBirth(java.sql.Date.valueOf(sc.nextLine()));
                    }
                    case 4 -> {
                        System.out.print("Nhập số điện thoại mới: ");
                        employee.setPhone(sc.nextLine());
                    }
                    case 5 -> {
                        System.out.print("Nhập địa chỉ mới: ");
                        employee.setAddress(sc.nextLine());
                    }
                    case 6 -> {
                        System.out.print("Nhập lương mới: ");
                        employee.setSalary(Double.parseDouble(sc.nextLine()));
                    }
                    case 7 -> {
                        System.out.print("Nhập mã phòng ban mới: ");
                        employee.setDepartmentId(sc.nextLine());
                    }
                    case 8 -> {
                        if (business.updateEmployee(employee)) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.err.println("Cập nhật thất bại!");
                        }
                        return;
                    }
                    default -> System.err.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    public static void deleteEmployee(Scanner sc) {
        EmployeeBusiness business = new EmployeeBusiness();
        List<Employee> list = business.findAll();
        if (list.isEmpty()) {
            System.err.println("Không có nhân viên nào để xoá!");
            return;
        }

        int choice = chooseEmployee(sc, list, "Chọn nhân viên cần xoá");
        if (choice == -1) return;

        Employee employee = list.get(choice);
        System.out.printf("Bạn có chắc chắn muốn xoá nhân viên '%s %s'? (1. Đồng ý, 2. Không): ", employee.getFirstName(), employee.getLastName());

        try {
            int confirm = Integer.parseInt(sc.nextLine());
            if (confirm == 1) {
                if (business.deleteEmployee(employee.getEmployeeId())) {
                    System.out.println("Xoá thành công!");
                } else {
                    System.err.println("Xoá thất bại!");
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Vui lòng nhập số hợp lệ!");
        }
    }

    public static void getEmployeesByAgeDesc() {
        EmployeeBusiness business = new EmployeeBusiness();
        List<Employee> employees = business.getEmployeesByAgeDesc();
        employees.forEach(Employee::displayData);
    }

    public static void searchEmployeesByName(Scanner sc) {
        System.out.print("Nhập từ khoá tìm kiếm: ");
        String keyword = sc.nextLine();
        EmployeeBusiness business = new EmployeeBusiness();
        List<Employee> employees = business.searchEmployeesByName(keyword);
        employees.forEach(Employee::displayData);
    }

    public static void statisticEmployeeByAgeGroup() {
        EmployeeBusiness business = new EmployeeBusiness();
        Map<String, Integer> statistic = business.statisticEmployee();

        if (statistic.isEmpty()) {
            System.out.println("Không có dữ liệu nhân viên.");
        } else {
            System.out.println("Thống kê số lượng nhân viên theo độ tuổi:");
            statistic.forEach((group, count) -> System.out.printf("%s: %d nhân viên\n", group, count));
        }
    }


    private static int chooseEmployee(Scanner sc, List<Employee> list, String message) {
        System.out.println(message);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d. %s %s\n", i + 1, list.get(i).getFirstName(), list.get(i).getLastName());
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
