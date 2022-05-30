package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoHibernateImpl();
    }

    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        userDao.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        userDao.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        userDao.cleanUsersTable();
    }
}
