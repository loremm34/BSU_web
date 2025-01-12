package by.bsu.services;

import by.bsu.dao.UserDAO;
import by.bsu.entities.authentication.User;
import by.bsu.entities.authentication.UserRole;

import static by.bsu.services.PasswordUtils.hashPassword;

public class UserService {
    public UserDAO userDao = new UserDAO();

    public User getUserByUsername(String username) {
        return userDao.findByUsername(username).orElse(null);
    }

    public boolean createUser(String username, String password, UserRole userRole) {
        if (userDao.findByUsername(username).isEmpty()) {
            return userDao.addUser(new User(username, hashPassword(password), userRole));
        }
        return false;
    }
}