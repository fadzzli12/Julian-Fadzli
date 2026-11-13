package projek;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO siswa. Sekarang bean Spring (@Repository) dan transaksinya
 * diurus @Transactional, bukan beginTransaction() manual.
 */
@Repository
public class StudentDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public StudentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    public void addStudent(String name, String className) {
        session().persist(new Student(name, className));
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return session().createQuery("from Student order by studentId", Student.class).list();
    }

    @Transactional(readOnly = true)
    public Student getStudentById(int id) {
        return session().get(Student.class, id);
    }

    @Transactional
    public void updateStudent(int id, String name, String className) {
        Student student = session().get(Student.class, id);
        if (student != null) {
            student.setName(name);
            student.setClassName(className);
            session().merge(student);
        }
    }

    @Transactional
    public void deleteStudent(int id) {
        Student student = session().get(Student.class, id);
        if (student != null) {
            session().remove(student);
        }
    }
}
