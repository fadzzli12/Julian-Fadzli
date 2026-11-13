package projek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lapisan service. Bean Spring (@Service), DAO disuntik lewat constructor.
 */
@Service
public class AttendanceService {

    private final StudentDAO studentDAO;
    private final AttendanceDAO attendanceDAO;

    @Autowired
    public AttendanceService(StudentDAO studentDAO, AttendanceDAO attendanceDAO) {
        this.studentDAO = studentDAO;
        this.attendanceDAO = attendanceDAO;
    }

    public void addStudent(String name, String className) {
        studentDAO.addStudent(name, className);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Transactional
    public void recordAttendance(int studentId, String status) {
        if (studentDAO.getStudentById(studentId) != null) {
            attendanceDAO.recordAttendance(studentId, LocalDate.now(), LocalTime.now(), status);
        }
    }

    public List<Attendance> getStudentAttendanceReport(int studentId) {
        return attendanceDAO.getAttendanceByStudentId(studentId);
    }

    public List<Attendance> getDailyAttendanceReport(LocalDate date) {
        return attendanceDAO.getAttendanceByDate(date);
    }

    public Map<String, Integer> getAttendanceSummary(int studentId) {
        List<Attendance> attendances = attendanceDAO.getAttendanceByStudentId(studentId);
        return attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getStatus, Collectors.summingInt(e -> 1)));
    }
}
