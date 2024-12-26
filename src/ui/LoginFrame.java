package ui;

import model.User;
import service.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Simplified login:
 *  - Owner: Enter user/pass
 *  - Customer: Just enter name/email
 */
public class LoginFrame extends JFrame {
    private AuthService authService;

    private JTextField ownerUsernameField;
    private JPasswordField ownerPasswordField;
    private JButton ownerLoginButton;

    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JButton customerLoginButton;

    private JLabel statusLabel;

    public LoginFrame(AuthService authService) {
        this.authService = authService;

        setTitle("E-Commerce Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel top: status
        statusLabel = new JLabel("Welcome! Please choose an option below.", SwingConstants.CENTER);
        add(statusLabel, BorderLayout.NORTH);

        // Center panel: Owner login
        JPanel centerPanel = new JPanel(new GridLayout(2,1));

        // Owner sub-panel
        JPanel ownerPanel = new JPanel(new GridLayout(3,2));
        ownerPanel.setBorder(BorderFactory.createTitledBorder("Owner Login"));
        ownerPanel.add(new JLabel("Username:"));
        ownerUsernameField = new JTextField();
        ownerPanel.add(ownerUsernameField);

        ownerPanel.add(new JLabel("Password:"));
        ownerPasswordField = new JPasswordField();
        ownerPanel.add(ownerPasswordField);

        ownerPanel.add(new JLabel(""));
        ownerLoginButton = new JButton("Login as Owner");
        ownerPanel.add(ownerLoginButton);

        centerPanel.add(ownerPanel);

        // Customer sub-panel
        JPanel customerPanel = new JPanel(new GridLayout(3,2));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer Quick Login"));
        customerPanel.add(new JLabel("Name:"));
        customerNameField = new JTextField();
        customerPanel.add(customerNameField);

        customerPanel.add(new JLabel("Email:"));
        customerEmailField = new JTextField();
        customerPanel.add(customerEmailField);

        customerPanel.add(new JLabel(""));
        customerLoginButton = new JButton("Login as Customer");
        customerPanel.add(customerLoginButton);

        centerPanel.add(customerPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Button actions
        ownerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleOwnerLogin();
            }
        });

        customerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCustomerLogin();
            }
        });

        setLocationRelativeTo(null);
    }

    private void handleOwnerLogin() {
        String usr = ownerUsernameField.getText().trim();
        String pwd = new String(ownerPasswordField.getPassword()).trim();
        User user = authService.loginOwner(usr, pwd);
        if (user != null) {
            statusLabel.setText("Owner login successful!");
            openOwnerFrame(user);
        } else {
            statusLabel.setText("Invalid owner credentials.");
        }
    }

    private void handleCustomerLogin() {
        String name = customerNameField.getText().trim();
        String email = customerEmailField.getText().trim();
        if (name.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Name and email can't be empty!");
            return;
        }
        // Create a pseudo-user (no password, role=customer)
        User user = new User(name, "", "customer");
        statusLabel.setText("Customer login successful!");
        openCustomerFrame(user);
    }

    private void openOwnerFrame(User ownerUser) {
        this.setVisible(false);
        new OwnerFrame(ownerUser).setVisible(true);
    }

    private void openCustomerFrame(User customerUser) {
        this.setVisible(false);
        new CustomerFrame(customerUser).setVisible(true);
    }
}
