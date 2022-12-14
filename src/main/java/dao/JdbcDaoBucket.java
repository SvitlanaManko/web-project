package dao;

import entity.Bucket;
import helper.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JdbcDaoBucket implements Dao<Bucket, Integer> {

    public static final String INSERT_BUCKET = "INSERT INTO bucket (purchase_data) VALUES (?);";
    public static final String SELECT_BUCKET = "SELECT * FROM bucket WHERE id = ?;";
    public static final String SELECT_ALL_BUCKET = "SELECT * FROM bucket;";
    public static final String UPDATE_BUCKET = "UPDATE bucket SET purchase_data = ? WHERE id = ?;";
    public static final String DELETE_BUCKET = "DELETE FROM bucket WHERE id = ?;";

    @Override
    public void create(Bucket bucket) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(INSERT_BUCKET);
            statement.setTimestamp(1, bucket.getPurchaseData());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Bucket read(Integer id) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(SELECT_BUCKET);
            ResultSet resultSet = statement.getResultSet();
            int idFromDb = resultSet.getInt("id");
            Timestamp purchaseData = resultSet.getTimestamp("purchaseData");
            statement.execute();
            return new Bucket(idFromDb, purchaseData);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Bucket> readAll() {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(SELECT_ALL_BUCKET);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            List<Bucket> buckets = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Timestamp purchaseData = resultSet.getTimestamp("purchaseData");
                Bucket bucket = new Bucket(id, purchaseData);
                buckets.add(bucket);
            }
            return buckets;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Bucket bucket) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(UPDATE_BUCKET);
            statement.setTimestamp(1, bucket.getPurchaseData());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(DELETE_BUCKET);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
