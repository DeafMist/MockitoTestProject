package service.impl;

import exception.UserNonUniqueException;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.UserRepository;
import service.UserService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void getUserLoginsShouldReturnsCorrectUserList() {
        when(userRepository.getAllUsers())
                .thenReturn(List.of(new User("user1", "password1"),
                        new User("user2", "password2")));

        assertThat(userService.getUserLogins()).isEqualTo(List.of("user1", "user2"));
    }

    @Test
    public void addUserWithEmptyLoginShouldThrowsException() {
        assertThatThrownBy(() -> userService.addUser("", "password"))
                .isInstanceOf(IllegalArgumentException.class);

        verify(userRepository, never()).addUser(any(User.class));
    }

    @Test
    public void addUserWithNullPasswordShouldThrowsException() {
        assertThatThrownBy(() -> userService.addUser("login", null))
                .isInstanceOf(IllegalArgumentException.class);

        verify(userRepository, never()).addUser(any(User.class));
    }

    @Test
    public void addNotUniqueUserShouldThrowsException() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("login", "password")));

        assertThatThrownBy(() -> userService.addUser("login", "password"))
                .isInstanceOf(UserNonUniqueException.class);

        verify(userRepository, never()).addUser(any(User.class));
    }

    @Test
    void addUniqueUser() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("login1", "password1")));

        userService.addUser("login2", "password2");

        verify(userRepository).addUser(any());
    }

    @Test
    void searchUserByNotValidLoginAndPasswordShouldReturnsFalse() {
        when(userRepository.getUserByLoginAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        assertThat(userService.userWithSuchLoginAndPasswordIsExists(anyString(), anyString())).isFalse();

        verify(userRepository, atMostOnce()).getUserByLoginAndPassword(anyString(), anyString());
    }

    @Test
    void searchUserByValidLoginPasswordShouldReturnsTrue() {
        when(userRepository.getUserByLoginAndPassword("user", "password"))
                .thenReturn(Optional.of(new User("user", "password")));

        assertThat(userService.userWithSuchLoginAndPasswordIsExists("user", "password")).isTrue();

        verify(userRepository, atMostOnce()).getUserByLoginAndPassword(anyString(), anyString());
    }
}