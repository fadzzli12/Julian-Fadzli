/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projek;

// 5. CLASS SERVICE - AttendanceService (Business Logic)
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceService {
    private StudentDAO studentDAO;
    private AttendanceDAO attendanceDAO;
    
    public AttendanceService(StudentDAO studentDAO, AttendanceDAO attendanceDAO) {
        this.studentDAO = studentDAO;
        this.attendanceDAO = attendanceDAO;
    }
    
    // Add student
    public void addStudent(String name, String className) {
        studentDAO.addStudent(name, className);
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
    
    // Record attendance for student
    public void recordAttendance(int studentId, String status) {
        if (studentDAO.getStudentById(studentId) != null) {
            attendanceDAO.recordAttendance(studentId, LocalDate.now(), 
                                          LocalTime.now(), status);
        }
    }
    
    // Get attendance report for specific student
    public List<Attendance> getStudentAttendanceReport(int studentId) {
        return attendanceDAO.getAttendanceByStudentId(studentId);
    }
    
    // Get attendance report for specific date
    public List<Attendance> getDailyAttendanceReport(LocalDate date) {
        return attendanceDAO.getAttendanceByDate(date);
    }
    
    // Get summary: jumlah hadir, izin, sakit, alpa per student
    public Map<String, Integer> getAttendanceSummary(int studentId) {
        List<Attendance> attendances = attendanceDAO.getAttendanceByStudentId(studentId);
        return attendances.stream()
                .collect(Collectors.groupingBy(
                    Attendance::getStatus,
                    Collectors.summingInt(e -> 1)
                ));
    }
}