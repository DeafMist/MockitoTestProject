package service;

import java.util.List;

public interface UserService {
    List<String> getUserLogins();

    void addUser(String login, String password);

    boolean userWithSuchLoginAndPasswordIsExists(String login, String password);
}
