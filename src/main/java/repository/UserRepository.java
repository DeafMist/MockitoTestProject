package repository;

import model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public Collection<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserByLogin(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findAny();
    }

    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findAny();
    }
}
