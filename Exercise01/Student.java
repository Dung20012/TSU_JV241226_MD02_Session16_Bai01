package BTVN.Exercise01;

import Lession16.RersultSet;

import java.sql.*;

public class Student {
    private static final String URL = "jdbc:mysql://localhost:3306/Students";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Gọi thử từng Stored Procedure
        getAllStudents();
        addStudent("Nguyen Van A", Date.valueOf("2001-01-01"), "nva@example.com");
        updateStudent(1, "Nguyen Van B", Date.valueOf("2000-12-12"), "nvb@example.com");
        findStudentById(1);
        deleteStudent(1);
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void getAllStudents() {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL get_all_students()}")) {

            ResultSet rs = stmt.executeQuery();
            System.out.println("Danh sách sinh viên:");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | DOB: %s | Email: %s%n",
                        rs.getInt("student_id"),
                        rs.getString("full_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent(String fullName, Date dob, String email) {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL add_student(?, ?, ?)}")) {

            stmt.setString(1, fullName);
            stmt.setDate(2, dob);
            stmt.setString(3, email);
            stmt.execute();

            System.out.println("Đã thêm sinh viên thành công.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(int id, String fullName, Date dob, String email) {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL update_student(?, ?, ?, ?)}")) {

            stmt.setInt(1, id);
            stmt.setString(2, fullName);
            stmt.setDate(3, dob);
            stmt.setString(4, email);
            stmt.execute();

            System.out.println("Đã cập nhật sinh viên thành công.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void findStudentById(int id) {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL find_student_by_id(?)}")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.printf("Tìm thấy sinh viên: ID: %d | Name: %s | DOB: %s | Email: %s%n",
                        rs.getInt("student_id"),
                        rs.getString("full_name"),
                        rs.getDate("date_of_birth"),
                        rs.getString("email"));
            } else {
                System.out.println("Không tìm thấy sinh viên với ID: " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStudent(int id) {
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL delete_student(?)}")) {

            stmt.setInt(1, id);
            stmt.execute();

            System.out.println("Đã xóa sinh viên thành công.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
