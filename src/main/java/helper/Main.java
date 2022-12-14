package helper;

import dao.Dao;
import dao.DaoFactory;
import entity.Bucket;
import entity.Product;
import entity.User;

import static entity.Role.ADMIN;

public class Main {


    public static void main(String[] args) {
        Dao<User, Integer> daoUser = DaoFactory.getInstance().getDaoUser();
        daoUser.create(new User("manko.svitlanka@gmail.com", "password12345!",
                "Svitlana", "Manko", ADMIN));
//        daoUser.create(new User("manko.veronika@gmail.com", "password12345abc@",
//                "Veronika", "Manko", CUSTOMER));
        Dao<Product, Integer> daoProduct = DaoFactory.getInstance().getDaoProduct();
        //   daoProduct.create(new Product());
        Dao<Bucket, Integer> daoBucket = DaoFactory.getInstance().getDaoBucket();
        //  daoBucket.create(new Bucket());
        System.out.println("Works!");
    }
}
