package data;

import model.Category;
import model.Order;
import model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataStore {
    private File productFile;
    private File orderFile;

    public FileDataStore(String productFilePath, String orderFilePath) {
        this.productFile = new File(productFilePath);
        this.orderFile = new File(orderFilePath);
    }

    // Load products from a text file
    public List<Product> loadProducts() {
        List<Product> products = new ArrayList<>();
        if (!productFile.exists()) return products;

        try (BufferedReader br = new BufferedReader(new FileReader(productFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                // Format: ID,CategoryName,ProductName,Price,Discount
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                int id = Integer.parseInt(parts[0].trim());
                Category category = new Category(parts[1].trim());
                String productName = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());
                double discount = Double.parseDouble(parts[4].trim());

                Product product = new Product(id, category, productName, price, discount);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    // Overwrite the entire product file when we update products
    public void saveAllProducts(List<Product> products) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(productFile))) {
            for (Product p : products) {
                pw.printf("%d,%s,%s,%.2f,%.2f%n",
                        p.getId(),
                        p.getCategory().getName(),
                        p.getName(),
                        p.getPrice(),
                        p.getDiscount());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Append an Order object to orders.txt
    public void saveOrder(Order order) {
        try (FileOutputStream fos = new FileOutputStream(orderFile, true);
             AppendableObjectOutputStream oos = new AppendableObjectOutputStream(fos, orderFile)) {
            oos.writeObject(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper class to append objects in a file without rewriting the stream header each time
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        private final File file;

        public AppendableObjectOutputStream(FileOutputStream fos, File file) throws IOException {
            super(fos);
            this.file = file;
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            if (file.length() == 0) {
                super.writeStreamHeader();
            } else {
                reset();
            }
        }
    }
}
