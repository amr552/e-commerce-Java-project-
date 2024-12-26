package service;

import data.UserRepository;
import model.User;

public class AuthService {
    private UserRepository userRepo;

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User loginOwner(String username, String password) {
        User user = userRepo.findUserByUsername(username);
        if (user != null
                && user.getPassword().equals(password)
                && user.getRole().equalsIgnoreCase("owner")) {
            return user;
        }
        return null; // invalid credentials or not an owner
    }
}
