package entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class Bucket {
    private int id;
    private Timestamp purchaseData;
    private List<Product> products;

    public Bucket(Timestamp purchaseData) {
        this.purchaseData = purchaseData;
    }

    public Bucket( int id,Timestamp timestamp) {
        this(timestamp);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Bucket{"
                + "id=" + id
                + ", purchaseData=" + purchaseData
                + ", bucketProducts=" + products
                + '}';
    }
}
