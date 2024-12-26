package ui;

import data.FileDataStore;
import data.ProductRepository;
import data.UserRepository;
import model.Product;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OwnerFrame extends JFrame {
    private ProductRepository productRepo;
    private UserRepository userRepo;
    private JList<Product> productList;
    private DefaultListModel<Product> listModel;

    private JButton addButton, editButton, removeButton, createOwnerButton;
    private User ownerUser;

    public OwnerFrame(User ownerUser) {
        this.ownerUser = ownerUser;

        FileDataStore ds = new FileDataStore("products.txt", "orders.txt");
        productRepo = new ProductRepository(ds);
        userRepo = new UserRepository("users.txt");

        setTitle("Owner Panel - " + ownerUser.getUsername());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        reloadProducts();

        productList = new JList<>(listModel);
        add(new JScrollPane(productList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Product");
        editButton = new JButton("Edit Product");
        removeButton = new JButton("Remove Product");
        createOwnerButton = new JButton("Create New Owner");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(createOwnerButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addProduct());
        editButton.addActionListener(e -> editProduct());
        removeButton.addActionListener(e -> removeProduct());
        createOwnerButton.addActionListener(e -> createNewOwner());

        setLocationRelativeTo(null);
    }

    private void reloadProducts() {
        listModel.clear();
        List<Product> products = productRepo.getAllProducts();
        for (Product p : products) {
            listModel.addElement(p);
        }
    }

    private void addProduct() {
        Product p = showProductDialog(null);
        if (p != null) {
            productRepo.addProduct(p);
            reloadProducts();
        }
    }

    private void editProduct() {
        Product selected = productList.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "No product selected!");
            return;
        }
        Product updated = showProductDialog(selected);
        if (updated != null) {
            productRepo.updateProduct(selected, updated);
            reloadProducts();
        }
    }

    private void removeProduct() {
        Product selected = productList.getSelectedValue();
        if (selected != null) {
            productRepo.removeProduct(selected);
            reloadProducts();
        } else {
            JOptionPane.showMessageDialog(this, "No product selected!");
        }
    }

    private Product showProductDialog(Product original) {
        JTextField idField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField discountField = new JTextField();

        if (original != null) {
            idField.setText(String.valueOf(original.getId()));
            categoryField.setText(original.getCategory().getName());
            nameField.setText(original.getName());
            priceField.setText(String.valueOf(original.getPrice()));
            discountField.setText(String.valueOf(original.getDiscount()));
        }

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Product ID:"));
        panel.add(idField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Discount(%):"));
        panel.add(discountField);

        int result = JOptionPane.showConfirmDialog(this, panel,
                (original == null ? "Add Product" : "Edit Product"),
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String catName = categoryField.getText().trim();
                String prodName = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                double discount = Double.parseDouble(discountField.getText().trim());

                return new Product(id, new model.Category(catName), prodName, price, discount);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        }
        return null;
    }

    private void createNewOwner() {
        OwnerCreateUserDialog dialog = new OwnerCreateUserDialog(this, userRepo);
        dialog.setVisible(true);
    }
}
