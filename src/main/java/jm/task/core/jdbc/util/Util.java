package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private Connection connection = null;
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db", "root", "springcourse");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Ошибка установления соединения с базой данных");
            e.printStackTrace();
        }
        return connection;
    }


    public Util() {

    }
}
