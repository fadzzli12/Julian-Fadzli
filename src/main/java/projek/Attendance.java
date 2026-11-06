/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projek;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int attendanceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time_in", nullable = false)
    private LocalTime timeIn;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    public Attendance() {}

    public Attendance(Student student, LocalDate date, LocalTime timeIn, String status) {
        this.student = student;
        this.date = date;
        this.timeIn = timeIn;
        this.status = status;
    }

    public Attendance(int attendanceId, Student student, LocalDate date,
                      LocalTime timeIn, String status) {
        this.attendanceId = attendanceId;
        this.student = student;
        this.date = date;
        this.timeIn = timeIn;
        this.status = status;
    }

    public int getAttendanceId() { return attendanceId; }
    public void setAttendanceId(int attendanceId) { this.attendanceId = attendanceId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public int getStudentId() {
        return student != null ? student.getStudentId() : 0;
    }

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
                ", studentId=" + getStudentId() +
                ", date=" + date +
                ", timeIn=" + timeIn +
                ", status='" + status + '\'' +
                '}';
    }
}
