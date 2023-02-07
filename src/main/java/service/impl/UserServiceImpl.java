package service.impl;

import exception.UserNonUniqueException;
import model.User;
import repository.UserRepository;
import service.UserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<String> getUserLogins() {
        return userRepository.getAllUsers().stream()
                .map(User::getLogin)
                .collect(Collectors.toList());
    }

    @Override
    public void addUser(String login, String password) {
        if (login == null || login.isBlank() || login.isEmpty()) {
            throw new IllegalArgumentException("Логин не должен быть пустым");
        } else if (password == null || password.isBlank() || password.isEmpty()) {
            throw new IllegalArgumentException("Пароль не должен быть пустым");
        }

        User user = new User(login, password);

        boolean isUserExists = userRepository.getAllUsers().stream()
                .anyMatch(e -> e.equals(user));

        if (isUserExists) {
            throw new UserNonUniqueException("Пользователь с таким логином уже существует");
        }

        userRepository.addUser(user);
    }

    @Override
    public boolean userWithSuchLoginAndPasswordIsExists(String login, String password) {
        return userRepository.getUserByLoginAndPassword(login, password).isPresent();
    }
}
