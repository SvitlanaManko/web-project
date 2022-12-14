package dao;

import entity.Product;
import helper.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcDaoProduct implements Dao<Product, Integer> {

    public static final String INSERT_PRODUCT = "INSERT INTO product (name, description, price, is_deleted);" +
            "VALUES (?, ?, ?, ?);";
    public static final String SELECT_PRODUCT = "SELECT * FROM product WHERE id = ?;";
    public static final String SELECT_ALL_PRODUCT = "SELECT * FROM product limit 10000;";
    public static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, description = ?, price =?, " +
            "is_deleted = ? WHERE id = ?;";
    public static final String DELETE_PRODUCT = "DELETE FROM product WHERE id = ?;";

    @Override
    public void create(Product product) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(INSERT_PRODUCT);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setBoolean(4, product.isDeleted());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product read(Integer id) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(SELECT_PRODUCT);
            statement.setInt(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            int idFromDb = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            double price = resultSet.getDouble("price");
            boolean isDeleted = resultSet.getBoolean("is_deleted");

            return new Product(idFromDb, name, description, price, isDeleted);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> readAll() {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(SELECT_ALL_PRODUCT);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                boolean isDeleted = resultSet.getBoolean("is_deleted");
                Product product = new Product(id, name, description, price, isDeleted);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(UPDATE_PRODUCT);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setString(4, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setBoolean(5, product.isDeleted());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(DELETE_PRODUCT);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

