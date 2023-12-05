package com.example.sweater.service;

import com.example.sweater.model.Role;
import com.example.sweater.model.User;
import com.example.sweater.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MailService mailService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void whenAddUserThenSuccess() {
        User user = new User();
        user.setEmail("some@mail.ru");

        boolean isUserCreated = userService.addUser(user);

        assertThat(isUserCreated).isTrue();
        assertThat(user.getActivationCode()).isNotNull();
        assertThat(user.getRoles()).anyMatch(role -> role.equals(Role.USER));

        verify(userRepository, times(1)).save(user);
        verify(mailService, times(1))
                .send(
                        eq(user.getEmail()),
                        eq("Код активации"),
                        contains("Добро пожаловать в Sweater!"));
    }

    @Test
    public void whenAddUserThenFail() {
        User user = new User();
        user.setUsername("Денис");

        doReturn(new User())
                .when(userRepository)
                .findByUsername("Денис");

        boolean isUserCreated = userService.addUser(user);

        assertThat(isUserCreated).isFalse();

        verify(userRepository, times(0)).save(any(User.class));
        verify(mailService, times(0))
                .send(
                        anyString(),
                        anyString(),
                        anyString());
    }

    @Test
    void whenActivateUserThanSuccess() {
        User user = new User();
        user.setActivationCode("bingo");

        doReturn(user)
                .when(userRepository)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        assertThat(isUserActivated).isTrue();
        assertThat(user.getActivationCode()).isNull();

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void whenActivateUserThanFail() {
        boolean isUserActivated = userService.activateUser("activate me");

        assertThat(isUserActivated).isFalse();
        verify(userRepository, times(0)).save(any(User.class));
    }
}