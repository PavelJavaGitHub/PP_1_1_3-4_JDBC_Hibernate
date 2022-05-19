package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();

        us.dropUsersTable();
        us.createUsersTable();
        us.saveUser("Ivan", "Ivanov", (byte) 27);
        us.saveUser("Alex", "Ovechkin", (byte) 36);
        us.saveUser("Just", "Me", (byte) 28);

        List<User> list = us.getAllUsers();

        System.out.println(list);

        us.removeUserById(2);
        list = us.getAllUsers();
        System.out.println(list);

        us.cleanUsersTable();

        list = us.getAllUsers();
        System.out.println(list);

        us.dropUsersTable();
        list = us.getAllUsers();
        System.out.println(list);


    }
}
