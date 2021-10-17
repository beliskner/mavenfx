package nl.inholland.javafx.services.user;

import nl.inholland.javafx.models.User;
import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {
    public User GetUserByCredentials(List<User> users, String userName, String password) {
        Optional<User> queriedUser = users.stream().filter(x -> x.getUserName().equals(userName)).findAny();
        User user = new User();
        if (queriedUser.isPresent()) user = queriedUser.get().getPassword().equals(password) ? queriedUser.get() : null;
        return user;
    }
}