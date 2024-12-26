package service;

import data.FileDataStore;
import model.Order;
import model.Product;
import model.User;

import java.util.List;

public class OrderService {
    private FileDataStore dataStore;

    public OrderService(FileDataStore dataStore) {
        this.dataStore = dataStore;
    }

    public void placeOrder(User user, List<Product> products) {
        Order order = new Order(user, products);
        dataStore.saveOrder(order);
    }
}
