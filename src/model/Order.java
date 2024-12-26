package model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private User user;
    private List<Product> products;

    public Order(User user, List<Product> products) {
        this.user = user;
        this.products = products;
    }

    public User getUser() { return user; }
    public List<Product> getProducts() { return products; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order for ").append(user.getUsername()).append("\n");
        for (Product p : products) {
            sb.append(" - ")
                    .append(p.getName())
                    .append(" ($")
                    .append(String.format("%.2f", p.getDiscountedPrice()))
                    .append(") [")
                    .append(p.getCategory().getName())
                    .append("]\n");
        }
        return sb.toString();
    }
}
