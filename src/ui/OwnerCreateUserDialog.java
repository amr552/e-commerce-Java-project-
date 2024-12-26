package ui;

import data.UserRepository;
import model.User;

import javax.swing.*;
import java.awt.*;

public class OwnerCreateUserDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createButton;
    private UserRepository userRepo;

    public OwnerCreateUserDialog(Frame owner, UserRepository userRepo) {
        super(owner, "Create New Owner", true);
        this.userRepo = userRepo;

        setSize(300, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        createButton = new JButton("Create");
        add(new JLabel(""));
        add(createButton);

        createButton.addActionListener(e -> createOwnerAccount());
        setLocationRelativeTo(owner);
    }

    private void createOwnerAccount() {
        String usr = usernameField.getText().trim();
        String pwd = new String(passwordField.getPassword()).trim();
        if (usr.isEmpty() || pwd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty!");
            return;
        }
        // role = owner
        User newOwner = new User(usr, pwd, "owner");
        userRepo.addUser(newOwner);
        JOptionPane.showMessageDialog(this, "Owner account created successfully!");
        dispose();
    }
}
