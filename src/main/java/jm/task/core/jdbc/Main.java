package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();
        userServiceImpl.createUsersTable();
        userServiceImpl.saveUser("Oleg", "Petrov", (byte) 24);
        userServiceImpl.saveUser("Vasiliy", "Blinov", (byte) 32);
        userServiceImpl.saveUser("Elena", "Fedorova", (byte) 25);
        userServiceImpl.saveUser("Igor", "Ivanov", (byte) 36);
        List<User> list = userServiceImpl.getAllUsers();
        for (User user : list) {
            System.out.println(user);
        }
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
        userServiceImpl.dropUsersTable();
    }
}
