package nl.inholland.javafx.services.user;

import nl.inholland.javafx.models.User;

import java.util.List;

public interface IUserService {
    public User GetUserByCredentials(List<User> users, String userName, String password);
}
