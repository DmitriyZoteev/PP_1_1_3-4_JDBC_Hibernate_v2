package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        util = new Util();
        Statement statement = null;
        try {
            statement = util.getConnection().createStatement();
            statement.executeUpdate("CREATE TABLE users (id int not null primary key auto_increment, name varchar(25) not null, lastName varchar(30) not null, age int not null)");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Таблица с таким именем уже существует");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                util.getConnection().close();
            } catch (SQLException  e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        util = new Util();
        Statement statement = null;
        try {
            statement = util.getConnection().createStatement();
            statement.executeUpdate("DROP TABLE users");
        } catch (SQLSyntaxErrorException e) {
            System.out.println("Удаляемая таблица отсутствует в базе данных");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                util.getConnection().close();
            } catch (SQLException  e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        util = new Util();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = util.getConnection().prepareStatement("INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        util = new Util();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = util.getConnection().prepareStatement("DELETE FROM users " + "WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        util = new Util();
        Statement statement = null;
        List<User> list = new ArrayList<>();
        try {
            statement = util.getConnection().createStatement();
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
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void cleanUsersTable() {
        util = new Util();
        Statement statement = null;
        try {
            statement = util.getConnection().createStatement();
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
