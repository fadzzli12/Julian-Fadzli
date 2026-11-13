package projek;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import projek.Student;

@Entity
@Table(name="attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int attendanceId;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="student_id", nullable=false)
    private Student student;
    @Column(name="date", nullable=false)
    private LocalDate date;
    @Column(name="time_in", nullable=false)
    private LocalTime timeIn;
    @Column(name="status", nullable=false, length=20)
    private String status;

    public Attendance() {
    }

    public Attendance(Student student, LocalDate date, LocalTime timeIn, String status) {
        this.student = student;
        this.date = date;
        this.timeIn = timeIn;
        this.status = status;
    }

    public Attendance(int attendanceId, Student student, LocalDate date, LocalTime timeIn, String status) {
        this.attendanceId = attendanceId;
        this.student = student;
        this.date = date;
        this.timeIn = timeIn;
        this.status = status;
    }

    public int getAttendanceId() {
        return this.attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getStudentId() {
        return this.student != null ? this.student.getStudentId() : 0;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeIn() {
        return this.timeIn;
    }

    public void setTimeIn(LocalTime timeIn) {
        this.timeIn = timeIn;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "Attendance{attendanceId=" + this.attendanceId + ", studentId=" + this.getStudentId() + ", date=" + String.valueOf(this.date) + ", timeIn=" + String.valueOf(this.timeIn) + ", status='" + this.status + "'}";
    }
}

