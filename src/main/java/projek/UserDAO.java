package projek;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO user (untuk login). Bean Spring (@Repository).
 */
@Repository
public class UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Transactional(readOnly = true)
    public User login(String username, String password) {
        List<User> hasil = session()
                .createQuery("from User where username = :u and password = :p", User.class)
                .setParameter("u", username)
                .setParameter("p", password)
                .setMaxResults(1)
                .list();
        return hasil.isEmpty() ? null : hasil.get(0);
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return session().createQuery("from User order by userId", User.class).list();
    }

    @Transactional
    public void addUser(String username, String password, String role) {
        session().persist(new User(username, password, role));
    }

    @Transactional
    public void deleteUser(int userId) {
        User user = session().get(User.class, userId);
        if (user != null) {
            session().remove(user);
        }
    }
}
