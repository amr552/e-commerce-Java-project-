import ui.CustomerFrame;
import ui.OwnerFrame;
import ui.LoginFrame;
import ui.OwnerCreateUserDialog;

import data.UserRepository;
import service.AuthService;

import javax.swing.*;

public class ECommerceApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Create user repository for owners
        UserRepository userRepo = new UserRepository("users.txt");
        AuthService authService = new AuthService(userRepo);

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame(authService);
            login.setVisible(true);
        });
    }
}
