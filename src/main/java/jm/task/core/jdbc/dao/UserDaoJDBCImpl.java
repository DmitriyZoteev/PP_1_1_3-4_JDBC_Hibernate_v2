package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = new Util().getConnection();
    public UserDaoJDBCImpl() {
    }

    public void disconnect() {
        try {
            connection.close();
            System.out.println("Соединение с базой данных закрыто");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement();) {
            connection.setAutoCommit(false);
            statement.executeUpdate("CREATE TABLE users (id int not null primary key auto_increment, name varchar(25) not null, lastName varchar(30) not null, age int not null)");
            connection.commit();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблица с таким именем уже существует");
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DROP TABLE users");
            connection.commit();
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Удаляемая таблица отсутствует в базе данных");
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users " + "WHERE id = ?")) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT id, name,lastName, age from users");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM users");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
