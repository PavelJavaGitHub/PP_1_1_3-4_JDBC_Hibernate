package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;
    public UserDaoHibernateImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS User (" +
                "id BIGINT(255) AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(32)," +
                "lastName VARCHAR(32)," +
                "age TINYINT(8))";

        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS User";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(new User(name, lastName, age));

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User user = session.get(User.class, id);
        session.delete(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {

        Session session = sessionFactory.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        Query query = session.createQuery(cq);
        List<User> userList = query.getResultList();

        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String sql = "DELETE FROM User";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}
