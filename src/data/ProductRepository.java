package data;

import model.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private FileDataStore dataStore;
    private List<Product> cache; // local cache of products

    public ProductRepository(FileDataStore dataStore) {
        this.dataStore = dataStore;
        this.cache = new ArrayList<>(dataStore.loadProducts());
    }

    public List<Product> getAllProducts() {
        return cache;
    }

    public void addProduct(Product product) {
        cache.add(product);
        dataStore.saveAllProducts(cache);
    }

    public void removeProduct(Product product) {
        cache.remove(product);
        dataStore.saveAllProducts(cache);
    }

    public void updateProduct(Product oldProduct, Product newProductData) {
        // find index of old product in the cache
        int index = cache.indexOf(oldProduct);
        if (index != -1) {
            cache.set(index, newProductData);
            dataStore.saveAllProducts(cache);
        }
    }
}
