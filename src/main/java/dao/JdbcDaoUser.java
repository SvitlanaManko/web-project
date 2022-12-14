package dao;

import entity.Role;
import entity.User;
import helper.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcDaoUser implements Dao<User, Integer> {

    public static final String INSERT_USER = "INSERT INTO user (email, password, first_name, last_name, role);" +
            "VALUES (?, ?, ?, ?, ?);";
    public static final String SELECT_USER = "SELECT * FROM user WHERE id = ?;";
    public static final String SELECT_ALL_USERS = "SELECT * FROM user limit 10000;";
    public static final String UPDATE_USER = "UPDATE user SET email = ?, password = ?, first_name = ?, last_name = ?, " +
            "role = ? WHERE id = ?;";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = ?;";

    @Override
    public void create(User user) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getRole().toString());
            statement.execute();
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("User with email '" + user.getEmail() + "' already exists!");
                return;
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public User read(Integer id) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(SELECT_USER);
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            Integer idFromDb = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("lastName");
            Role role = (Role) resultSet.getObject("role");
            return new User(idFromDb, email, password, firstName, lastName, role);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> readAll() {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(SELECT_ALL_USERS);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("lastName");
                Role role = (Role) resultSet.getObject("role");
                User user = new User(id, email, password, firstName, lastName, role);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(User user) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getRole().toString());
            statement.setInt(6, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(DELETE_USER);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
