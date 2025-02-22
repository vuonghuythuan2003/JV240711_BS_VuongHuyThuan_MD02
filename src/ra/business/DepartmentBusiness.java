package ra.business;

import ra.entity.Department;
import ra.util.ConnectionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepartmentBusiness {

    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String query = "CALL Proc_Get_All_Departments()";

        try (Connection conn = ConnectionDB.getConnection();
             CallableStatement callSt = conn.prepareCall(query);
             ResultSet rs = callSt.executeQuery()) {

            while (rs.next()) {
                Department department = new Department(
                        rs.getString("department_id"),
                        rs.getString("department_name"),
                        rs.getString("description"),
                        rs.getBoolean("is_deleted")
                );
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    public boolean addDepartment(Department department) {
        String query = "CALL Proc_Add_Department(?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             CallableStatement callSt = conn.prepareCall(query)) {
            callSt.setString(1, department.getDepartmentId());
            callSt.setString(2, department.getDepartmentName());
            callSt.setString(3, department.getDescription());
            return callSt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateDepartment(Department department) {
        String query = "CALL Proc_Update_Department(?, ?, ?)";
        try (Connection conn = ConnectionDB.getConnection();
             CallableStatement callSt = conn.prepareCall(query)) {
            callSt.setString(1, department.getDepartmentId());
            callSt.setString(2, department.getDepartmentName());
            callSt.setString(3, department.getDescription());
            return callSt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(String departmentId) {
        String query = "CALL Proc_Delete_Department(?)";
        try (Connection conn = ConnectionDB.getConnection();
             CallableStatement callSt = conn.prepareCall(query)) {
            callSt.setString(1, departmentId);
            return callSt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int countEmployeesByDepartment(String departmentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        int employeeCount = 0;
        try {
            conn = ConnectionDB.getConnection();
            callSt = conn.prepareCall("{call Proc_Statistic_Employees_By_Department(?, ?)}");
            callSt.setString(1, departmentId);
            callSt.registerOutParameter(2, Types.INTEGER);
            callSt.execute();
            employeeCount = callSt.getInt(2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return employeeCount;
    }

}
