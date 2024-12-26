package ui;

import data.FileDataStore;
import data.ProductRepository;
import model.Product;
import model.User;
import service.CartService;
import service.OrderService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CustomerFrame extends JFrame {
    private CartService cartService;
    private OrderService orderService;
    private ProductRepository productRepo;

    private User customer;
    private JList<Product> productList;
    private JTextArea cartArea;
    private JButton addToCartBtn, viewCartBtn, placeOrderBtn;
    private DefaultListModel<Product> productModel;

    public CustomerFrame(User customer) {
        this.customer = customer;

        // Setup data
        FileDataStore ds = new FileDataStore("products.txt", "orders.txt");
        productRepo = new ProductRepository(ds);
        cartService = new CartService();
        orderService = new OrderService(ds);

        setTitle("Welcome, " + customer.getUsername());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        productModel = new DefaultListModel<>();
        reloadProducts();

        productList = new JList<>(productModel);
        add(new JScrollPane(productList), BorderLayout.WEST);

        cartArea = new JTextArea();
        cartArea.setEditable(false);
        add(new JScrollPane(cartArea), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        addToCartBtn = new JButton("Add to Cart");
        viewCartBtn = new JButton("View Cart");
        placeOrderBtn = new JButton("Place Order");

        btnPanel.add(addToCartBtn);
        btnPanel.add(viewCartBtn);
        btnPanel.add(placeOrderBtn);
        add(btnPanel, BorderLayout.SOUTH);

        addToCartBtn.addActionListener(e -> addToCart());
        viewCartBtn.addActionListener(e -> viewCart());
        placeOrderBtn.addActionListener(e -> placeOrder());

        setLocationRelativeTo(null);
    }

    private void reloadProducts() {
        productModel.clear();
        List<Product> products = productRepo.getAllProducts();
        for (Product p : products) {
            productModel.addElement(p);
        }
    }

    private void addToCart() {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "No product selected!");
            return;
        }
        cartService.addToCart(selected);
        JOptionPane.showMessageDialog(this, "Added to cart: " + selected.getName());
    }

    private void viewCart() {
        StringBuilder sb = new StringBuilder("Your cart:\n");
        for (Product p : cartService.getCartItems()) {
            sb.append(" - ")
                    .append(p.getName())
                    .append(" ($")
                    .append(String.format("%.2f", p.getDiscountedPrice()))
                    .append(")\n");
        }
        cartArea.setText(sb.toString());
    }

    private void placeOrder() {
        if (cartService.getCartItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your cart is empty!");
            return;
        }
        orderService.placeOrder(customer, cartService.getCartItems());
        cartService.clearCart();
        cartArea.setText("Order placed successfully!\n");
        JOptionPane.showMessageDialog(this, "Order placed!");
    }
}
