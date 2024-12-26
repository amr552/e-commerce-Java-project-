package service;

import model.Product;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the shopping cart in memory
 */
public class CartService {
    private List<Product> cart = new ArrayList<>();

    public void addToCart(Product product) {
        cart.add(product);
    }

    public List<Product> getCartItems() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}
