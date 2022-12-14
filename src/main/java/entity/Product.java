package entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private boolean isDeleted = false;

    public Product(String name, String description, double price, boolean isDeleted) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isDeleted = isDeleted;
    }

    public Product(int id, String name, String description, double price, boolean isDeleted) {
        this(name, description, price, isDeleted);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", price=" + price
                + '}';
    }
}
