/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projek;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public void recordAttendance(int studentId, LocalDate date,
                                 LocalTime timeIn, String status) {
        String sql = "INSERT INTO attendances (student_id, date, time_in, status) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setDate(2, Date.valueOf(date));
            ps.setTime(3, Time.valueOf(timeIn));
            ps.setString(4, status);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error record attendance: " + e.getMessage());
        }
    }

    public List<Attendance> getAllAttendances() {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT * FROM attendances ORDER BY id";
        try {
            Connection conn = Koneksi.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                attendances.add(new Attendance(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time_in").toLocalTime(),
                    rs.getString("status")
                ));
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error get all attendances: " + e.getMessage());
        }
        return attendances;
    }

    public List<Attendance> getAttendanceByStudentId(int studentId) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT * FROM attendances WHERE student_id = ? ORDER BY date DESC";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                attendances.add(new Attendance(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time_in").toLocalTime(),
                    rs.getString("status")
                ));
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error get attendance by student: " + e.getMessage());
        }
        return attendances;
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        List<Attendance> attendances = new ArrayList<>();
        String sql = "SELECT * FROM attendances WHERE date = ?";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                attendances.add(new Attendance(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getTime("time_in").toLocalTime(),
                    rs.getString("status")
                ));
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error get attendance by date: " + e.getMessage());
        }
        return attendances;
    }

    public void updateAttendance(int attendanceId, String status) {
        String sql = "UPDATE attendances SET status = ? WHERE id = ?";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, attendanceId);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error update attendance: " + e.getMessage());
        }
    }

    public void deleteByStudentId(int studentId) {
        String sql = "DELETE FROM attendances WHERE student_id = ?";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error delete attendance by student: " + e.getMessage());
        }
    }

    public void deleteAttendance(int attendanceId) {
        String sql = "DELETE FROM attendances WHERE id = ?";
        try {
            Connection conn = Koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, attendanceId);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error delete attendance: " + e.getMessage());
        }
    }
}