package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void createUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                String sql = "CREATE TABLE IF NOT EXISTS User (" +
                        "id BIGINT(255) AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(32)," +
                        "lastName VARCHAR(32)," +
                        "age TINYINT(8))";
                Query query = session.createSQLQuery(sql).addEntity(User.class);
                query.executeUpdate();

                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                String sql = "DROP TABLE IF EXISTS User";
                Query query = session.createSQLQuery(sql).addEntity(User.class);
                query.executeUpdate();

                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.save(new User(name, lastName, age));
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.delete(session.get(User.class, id));
                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {

        try (Session session = sessionFactory.openSession()) {
//            CriteriaQuery<User> cq = session.getCriteriaBuilder().createQuery(User.class);
//            cq.select(cq.from(User.class));
//            return session.createQuery(cq).getResultList();

            String sql = "SELECT * FROM User";
            Query query = session.createSQLQuery(sql).addEntity(User.class);

            return query.getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = sessionFactory.openSession()) {
            try {
                session.beginTransaction();

                String sql = "DELETE FROM User";
                Query query = session.createSQLQuery(sql).addEntity(User.class);
                query.executeUpdate();

                session.getTransaction().commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                e.printStackTrace();
            }
        }
    }
}
