/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projek;

// 2. CLASS MODEL - Attendance
import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private int attendanceId;
    private int studentId;
    private LocalDate date;
    private LocalTime timeIn;
    private String status; // HADIR, IZIN, SAKIT, ALPA
    
    public Attendance(int attendanceId, int studentId, LocalDate date, 
                      LocalTime timeIn, String status) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.date = date;
        this.timeIn = timeIn;
        this.status = status;
    }
    
    // Getters & Setters
    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }
    
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public LocalTime getTimeIn() { return timeIn; }
    public void setTimeIn(LocalTime timeIn) { this.timeIn = timeIn; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceId=" + attendanceId +
                ", studentId=" + studentId +
                ", date=" + date +
                ", timeIn=" + timeIn +
                ", status='" + status + '\'' +
                '}';
    }
}
