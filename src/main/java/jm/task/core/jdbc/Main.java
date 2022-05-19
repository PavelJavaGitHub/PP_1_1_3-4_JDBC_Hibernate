package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.*;

import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserService us = new UserServiceImpl();
        us.createUsersTable();

        us.saveUser("Ivan", "Ivanov", (byte) 30);
        us.saveUser("Just", "Me", (byte) 28);
        us.saveUser("Alex", "Ovechkin", (byte) 36);
        us.saveUser("Wayne", "Gretzky", (byte) 61);

        List<User> list = us.getAllUsers();
        System.out.println(list);

        us.cleanUsersTable();
        us.dropUsersTable();
    }
}
