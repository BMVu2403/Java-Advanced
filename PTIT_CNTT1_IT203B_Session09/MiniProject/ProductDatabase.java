package PTIT_CNTT1_IT203B_Session09.MiniProject;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    private static ProductDatabase instance;
    private List<Product> productList;

    private ProductDatabase() {
        productList = new ArrayList<>();
    }

    public static ProductDatabase getInstance() {
        if (instance == null) {
            instance = new ProductDatabase();
        }
        return instance;
    }

    public void addProduct(Product p) {
        productList.add(p);
    }

    public List<Product> getAll() {
        return productList;
    }

    public Product findById(String id) {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean delete(String id) {
        Product p = findById(id);
        if (p != null) {
            productList.remove(p);
            return true;
        }
        return false;
    }
}
