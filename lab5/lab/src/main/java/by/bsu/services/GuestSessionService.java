package by.bsu.services;


import by.bsu.entities.authentication.User;
import by.bsu.entities.authentication.UserRole;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static by.bsu.services.JwtService.createJwtToken;

public class GuestSessionService {
    public static ConcurrentHashMap<String, User> guestUsers = new ConcurrentHashMap<>();

    public User addGuestUser() {
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword(null);
        user.setRole(UserRole.GUEST);
        guestUsers.put(user.getUsername(), user);
        return user;
    }

    public String getGuestToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long oneYearMillis = 365L * 24 * 60 * 60 * 1000;
        Date expiryDate = new Date(nowMillis + oneYearMillis);
        return createJwtToken(user.getUsername(), expiryDate);
    }
}