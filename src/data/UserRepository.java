package data;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private File userFile;

    public UserRepository(String userFilePath) {
        userFile = new File(userFilePath);
    }

    public User findUserByUsername(String username) {
        for (User user : loadUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        if (!userFile.exists()) return users;
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                String usr = parts[0].trim();
                String pwd = parts[1].trim();
                String role = parts[2].trim(); // "owner"
                users.add(new User(usr, pwd, role));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void addUser(User user) {
        // Append a single user record to users.txt
        try (FileWriter fw = new FileWriter(userFile, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            // Format: username,password,role
            bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
