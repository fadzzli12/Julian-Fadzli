package projek;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO absensi. Bean Spring (@Repository), transaksi diurus @Transactional.
 */
@Repository
public class AttendanceDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public AttendanceDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public void recordAttendance(int studentId, LocalDate date, LocalTime timeIn, String status) {
        Student student = session().get(Student.class, studentId);
        if (student != null) {
            session().persist(new Attendance(student, date, timeIn, status));
        }
    }

    @Transactional(readOnly = true)
    public List<Attendance> getAllAttendances() {
        return session().createQuery("from Attendance order by attendanceId", Attendance.class).list();
    }

    @Transactional(readOnly = true)
    public List<Attendance> getAttendanceByStudentId(int studentId) {
        return session().createQuery("from Attendance where student.studentId = :sid order by date desc", Attendance.class)
                .setParameter("sid", studentId)
                .list();
    }

    @Transactional(readOnly = true)
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return session().createQuery("from Attendance where date = :date", Attendance.class)
                .setParameter("date", date)
                .list();
    }

    @Transactional
    public void updateAttendance(int attendanceId, String status) {
        Attendance attendance = session().get(Attendance.class, attendanceId);
        if (attendance != null) {
            attendance.setStatus(status);
            session().merge(attendance);
        }
    }

    @Transactional
    public void deleteByStudentId(int studentId) {
        session().createMutationQuery("delete from Attendance where student.studentId = :sid")
                .setParameter("sid", studentId)
                .executeUpdate();
    }

    @Transactional
    public void deleteAttendance(int attendanceId) {
        Attendance attendance = session().get(Attendance.class, attendanceId);
        if (attendance != null) {
            session().remove(attendance);
        }
    }
}
