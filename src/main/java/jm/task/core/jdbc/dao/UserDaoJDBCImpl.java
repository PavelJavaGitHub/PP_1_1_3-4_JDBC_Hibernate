package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        try (Connection conn = Util.getMySQLConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS User (" +
                    "id BIGINT(255) AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "lastName VARCHAR(255)," +
                    "age TINYINT(8))";

            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    public void dropUsersTable() {

        try (Connection conn = Util.getMySQLConnection()) {

            String sql = "DROP TABLE IF EXISTS User";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getMySQLConnection()) {

            String sql = "INSERT User (name, lastName, age) " +
                    "VALUES ('" + name + "', '" + lastName + "', " + age + ")";

            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getMySQLConnection()) {

            String sql = "DELETE FROM User WHERE id=" + id;
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (Connection conn = Util.getMySQLConnection()) {

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

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getMySQLConnection()) {

            String sql = "DELETE FROM User";
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
