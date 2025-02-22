package ra.business;

import ra.entity.Employee;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EmployeeBusiness {
    public static List<Employee> findAll() {
        List<Employee> list = new ArrayList<>();
        String sql = "{call Proc_Get_All_Employees()}"; // Đổi tên cho đúng với file SQL

        try (Connection conn = ConnectionDB.getConnection();
             CallableStatement callSt = conn.prepareCall(sql);
             ResultSet rs = callSt.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("salary"),
                        rs.getDate("created_at"),
                        rs.getDate("update_at"),
                        rs.getBoolean("is_deleted"),
                        rs.getString("department_id")
                );
                list.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }
        return list;
    }

    public static boolean addEmployee(Employee employee) {
        String sql = "{call Proc_Add_Employee(?,?,?,?,?,?,?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, employee.getFirstName());
            cs.setString(2, employee.getLastName());
            cs.setDate(3, employee.getDateOfBirth());
            cs.setString(4, employee.getPhone());
            cs.setString(5, employee.getAddress());
            cs.setDouble(6, employee.getSalary());
            cs.setString(7, employee.getDepartmentId());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhân viên: " + e.getMessage());
            return false;
        }
    }

    public static boolean updateEmployee(Employee employee) {
        String sql = "{call Proc_Update_Employee(?,?,?,?,?,?,?,?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, employee.getEmployeeId());
            cs.setString(2, employee.getFirstName());
            cs.setString(3, employee.getLastName());
            cs.setDate(4, employee.getDateOfBirth());
            cs.setString(5, employee.getPhone());
            cs.setString(6, employee.getAddress());
            cs.setDouble(7, employee.getSalary());
            cs.setString(8, employee.getDepartmentId());

            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật nhân viên: " + e.getMessage());
            return false;
        }
    }

    public static boolean deleteEmployee(int id) {
        String sql = "{call Proc_Delete_Employee(?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, id);
            return cs.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            return false;
        }
    }

    public static List<Employee> getEmployeesByAgeDesc() {
        List<Employee> employees = new ArrayList<>();
        String sql = "{call Proc_Get_Employees_By_Age_Desc()}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("employee_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("salary"),
                        rs.getDate("created_at"),
                        rs.getDate("update_at"),
                        rs.getBoolean("is_deleted"),
                        rs.getString("department_id")
                );
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên theo tuổi giảm dần: " + e.getMessage());
        }
        return employees;
    }

    public static List<Employee> searchEmployeesByName(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String sql = "{call Proc_Search_Employee_By_Name(?)}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {
            cs.setString(1, keyword);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    Employee employee = new Employee(
                            rs.getInt("employee_id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getDate("date_of_birth"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getDouble("salary"),
                            rs.getDate("created_at"),
                            rs.getDate("update_at"),
                            rs.getBoolean("is_deleted"),
                            rs.getString("department_id")
                    );
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
        return employees;
    }

    public Map<String, Integer> statisticEmployee() {
        Map<String, Integer> statistics = new HashMap<>();
        String sql = "{call Proc_Statistic_Employee_By_Age_Group()}";

        try (Connection con = ConnectionDB.getConnection();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            if (rs.next()) {
                statistics.put("Từ 18 đến 29 tuổi", rs.getInt("age_18_29"));
                statistics.put("Từ 30 đến 49 tuổi", rs.getInt("age_30_49"));
                statistics.put("Từ 50 tuổi trở lên", rs.getInt("age_50_plus"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê nhân viên theo độ tuổi: " + e.getMessage());
        }
        return statistics;
    }



}
