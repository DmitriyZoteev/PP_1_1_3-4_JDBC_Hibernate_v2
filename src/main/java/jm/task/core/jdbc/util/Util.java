package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public Util() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db", "root", "springcourse");
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }
}
