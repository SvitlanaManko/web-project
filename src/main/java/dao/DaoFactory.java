package dao;

import entity.Bucket;
import entity.Product;
import entity.User;

import java.sql.Connection;

public class DaoFactory {
    private static DaoFactory factory;

    private DaoFactory() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loading success!");
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver loading error!");
            throw new RuntimeException(ex);
        }
    }

    public static synchronized DaoFactory getInstance() {
        if (factory == null) {
            factory = new DaoFactory();
        }
        return factory;
    }


    public Dao<User, Integer> getDaoUser() {
        return new JdbcDaoUser();
    }


    public Dao<Bucket, Integer> getDaoBucket() {
        return new JdbcDaoBucket();
    }


    public Dao<Product, Integer> getDaoProduct() {
        return new JdbcDaoProduct();
    }
}
