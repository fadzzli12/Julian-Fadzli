/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projek;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void addStudent(String name, String className) {
        String sql = "INSERT INTO students (name, className) VALUES (?, ?)";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, className);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error add student: " + e.getMessage());
        }
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id";
        try {
            Connection conn = Koneksi.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("className")
                ));
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error get all students: " + e.getMessage());
        }
        return students;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Student s = new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("className")
                );
                rs.close();
                ps.close();
                conn.close();
                return s;
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error get student by ID: " + e.getMessage());
        }
        return null;
    }

    public void updateStudent(int id, String name, String className) {
        String sql = "UPDATE students SET name = ?, className = ? WHERE id = ?";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, className);
            ps.setInt(3, id);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error update student: " + e.getMessage());
        }
    }

    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error delete student: " + e.getMessage());
        }
    }
}

