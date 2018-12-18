package by.bsuir.server.base;

import by.bsuir.entity.Interview;
import by.bsuir.entity.User;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBase {
    public User findUserByLoginAndPassword(Connection connection, String login, String password) {
        User user = new User("login", "");
        try {
            Statement statement = connection.createStatement();
            String sql = "select id, role from user where login='" + login + "' and password='" + password + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            String role = null;
            while (resultSet.next() && role == null) {
                role = resultSet.getString("role");
                if (role != null) {
                    user = new User(login, role);
                    user.setId(resultSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsers(Connection connection, String userLogin) {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select login, role, email from user where login!='" + userLogin + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User newUser = new User(resultSet.getString("login"), resultSet.getString("role"));
                newUser.setEmail(resultSet.getString("email"));
                users.add(newUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void removeUser(Connection connection, String login) {
        Optional<Integer> userId = findUserIdByName(connection, login);
        if (userId.isPresent()) {
            try {
                Statement statement = connection.createStatement();
                String sql = "delete from user where id='" + userId.get() + "';";
                statement.execute(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Optional<Integer> findUserIdByName(Connection connection, String name) {
        Optional<Integer> id = Optional.empty();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select  id from user where login='" + name + "'");
            while (resultSet.next()) {
                id = Optional.of(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean addUser(Connection connection, User user) {
        try {
            Statement statement = connection.createStatement();
            String login = user.getLogin();
            String password = user.getPassword();
            String role = user.getRole();
            String email = user.getEmail();
            String sql = "insert  into user (login,password,role,email) values ('" + login + "','" + password + "','" + role + "','" + email + "')";
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateUser(Connection connection, String oldName, User user) {
        try {
            Optional<Integer> userId = findUserIdByName(connection, oldName);
            if (userId.isPresent()) {
                Statement statement = connection.createStatement();
                int userIdValue = userId.get();
                String sql = "update   user set  login ='" + user.getLogin() + "', password ='" + user.getPassword() +
                        "', role='" + user.getRole() + "', email='" + user.getEmail() + "' where id=" + userIdValue + ";";
                statement.execute(sql);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public List<User> getAllUsersByRole(Connection connection, String admin) {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select login, role, email from user where role='" + admin + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String role = resultSet.getString("role");
                String login = resultSet.getString("login");
                User newUser = new User(login, role);
                newUser.setEmail(resultSet.getString("email"));
                users.add(newUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveAllData(Connection connection, FileWriter writer) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from course.user;");
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String email = resultSet.getString("email");
                writer.write("insert into user user (login,password,role,email) values ('" + login + "','" + password + "','" + role + "','" + email + "');");
           writer.write('\n');
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
