package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection conn;
    public UserDaoJDBCImpl() {
        try {
            conn = Util.getMySQLConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS User (" +
                    "id BIGINT(255) AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(32)," +
                    "lastName VARCHAR(32)," +
                    "age TINYINT(8))";

            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        try {
            String sql = "DROP TABLE IF EXISTS User";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

            System.out.println("User с именем – " + name + " добавлен в базу данных");

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }
    }

    public void removeUserById(long id) throws SQLException {
        try {
            conn.setAutoCommit(false);

            String sql = "DELETE FROM User WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();

        try {
            conn.setAutoCommit(false);

            String sql = "SELECT id, name, lastName, age FROM User";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                long id = rs.getLong("id");
                User user = new User(rs.getString("name"),
                        rs.getString("lastName"), rs.getByte("age"));
                user.setId(id);
                list.add(user);
            }

            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }

        return list;
    }

    public void cleanUsersTable() throws SQLException {
        try {
            conn.setAutoCommit(false);
            String sql = "DELETE FROM User";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }
    }
}
