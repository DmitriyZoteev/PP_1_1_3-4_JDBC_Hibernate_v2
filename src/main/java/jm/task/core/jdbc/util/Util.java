package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String Url = "jdbc:mysql://localhost:3306/my_db";
    private static final String User = "root";
    private static final String Password = "springcourse";

    public Util() {
    }

    private Connection connection = null;

    public Connection getConnection() {
        try {
            Class.forName(Driver);
            connection = DriverManager.getConnection(Url, User, Password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Ошибка установления соединения с базой данных");
            e.printStackTrace();
        }
        return connection;
    }

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, Driver);
                properties.put(Environment.URL, Url);
                properties.put(Environment.USER, User);
                properties.put(Environment.PASS, Password);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        return sessionFactory;
    }
}
