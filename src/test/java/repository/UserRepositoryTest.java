package repository;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private UserRepository userRepository;

    private final User user1 = new User("login", "password");

    private final User user2 = new User("DeafMist", "12345");

    private final User user3 = new User("LOGIN", "password");

    private final User user4 = new User("login", "PASSWORD");


    @BeforeEach
    public void init() {
        userRepository = new UserRepository();
    }

    @Test
    public void shouldReturnEmptyListIfUsersListIsEmpty() {
        assertEquals(userRepository.getAllUsers(), new ArrayList<>());
    }

    @Test
    public void shouldReturnFilledUsersList() {
        userRepository.addUser(user1);
        userRepository.addUser(user2);

        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);

        assertEquals(expected, userRepository.getAllUsers());
    }

    @Test
    public void findUserByLoginIfUserExists() {
        userRepository.addUser(user1);

        Optional<User> expected = Optional.of(user1);

        assertEquals(expected, userRepository.getUserByLogin(user1.getLogin()));
    }

    @Test
    public void findUserByLoginIfUserDoesntExist() {
        userRepository.addUser(user1);

        Optional<User> expected = Optional.of(user2);

        assertNotEquals(expected, userRepository.getUserByLogin(user1.getLogin()));
    }

    @Test
    public void findUserByLoginAndPasswordIfUserExists() {
        userRepository.addUser(user1);

        Optional<User> expected = Optional.of(user1);

        assertEquals(expected, userRepository.getUserByLoginAndPassword(user1.getLogin(), user1.getPassword()));
    }

    @Test
    public void findUserByLoginAndPasswordIfOnlyPasswordExist() {
        userRepository.addUser(user1);

        Optional<User> expected = Optional.of(user3);

        assertNotEquals(expected, userRepository.getUserByLoginAndPassword(user1.getLogin(), user1.getPassword()));
    }

    @Test
    public void findUserByLoginAndPasswordIfOnlyLoginExist() {
        userRepository.addUser(user1);

        Optional<User> expected = Optional.of(user4);

        assertEquals(expected, userRepository.getUserByLoginAndPassword(user1.getLogin(), user1.getPassword()));
    }
}