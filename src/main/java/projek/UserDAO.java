package projek;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {

    /**
     * Cek login. Return User kalau username + password cocok, null kalau gagal.
     */
    public User login(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<User> hasil = session.createQuery(
                    "from User where username = :u and password = :p", User.class)
                    .setParameter("u", username)
                    .setParameter("p", password)
                    .setMaxResults(1)
                    .list();
            return hasil.isEmpty() ? null : hasil.get(0);
        } catch (Exception e) {
            System.out.println("Error login: " + e.getMessage());
            return null;
        }
    }

    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from User order by userId", User.class).list();
        } catch (Exception e) {
            System.out.println("Error get users: " + e.getMessage());
            return List.of();
        }
    }

    public void addUser(String username, String password, String role) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(new User(username, password, role));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error add user: " + e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) session.remove(user);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error delete user: " + e.getMessage());
        }
    }
}
