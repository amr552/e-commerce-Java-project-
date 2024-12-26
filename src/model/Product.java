package model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private Category category;
    private String name;
    private double price;
    private double discount;

    public Product(int id, Category category, String name, double price, double discount) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public int getId() { return id; }
    public Category getCategory() { return category; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public double getDiscount() { return discount; }

    public void setPrice(double price) { this.price = price; }
    public void setDiscount(double discount) { this.discount = discount; }
    public void setName(String name) { this.name = name; }
    public void setCategory(Category category) { this.category = category; }

    public double getDiscountedPrice() {
        return price - (price * discount / 100.0);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (ID:%d) - $%.2f (%2.0f%% off)",
                category.getName(), name, id, price, discount);
    }
}
