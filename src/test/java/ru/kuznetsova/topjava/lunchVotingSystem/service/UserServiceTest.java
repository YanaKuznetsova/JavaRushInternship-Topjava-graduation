package ru.kuznetsova.topjava.lunchVotingSystem.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Role;
import ru.kuznetsova.topjava.lunchVotingSystem.model.User;
import ru.kuznetsova.topjava.lunchVotingSystem.util.JpaUtil;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.kuznetsova.topjava.lunchVotingSystem.UserTestData.*;
import static ru.kuznetsova.topjava.lunchVotingSystem.model.Role.ROLE_USER;


public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void create() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", ROLE_USER);
        User created = userService.create(newUser);
        newUser.setId(created.getId());
        assertMatchUsers(userService.getAll(), ADMIN, newUser, USER_1, USER_2, USER_3, USER_4, USER_5);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                userService.create(new User(null, "Duplicate", "user-1@yandex.ru", "newPass", ROLE_USER)));

    }

    @Test
    void delete() throws Exception {
        userService.delete(USER_ID);
        assertMatchUsers(userService.getAll(), ADMIN, USER_2, USER_3, USER_4, USER_5);
    }

    @Test
    void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.delete(1));
    }

    @Test
    void get() throws Exception {
        User user = userService.get(USER_ID);
        assertMatchUsers(user, USER_1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                userService.get(1));
    }

    @Test
    void getByEmail() throws NotFoundException {
        User user = userService.getByEmail("user-1@yandex.ru");
        assertMatchUsers(user, USER_1);
    }

    @Test
    void update() throws NotFoundException {
        User updated = new User(USER_1);
        updated.setName("UpdatedName");
        userService.update(updated);
        assertMatchUsers(userService.get(USER_ID), updated);
    }

    @Test
    void getAll() {
        assertMatchUsers(userService.getAll(), ADMIN, USER_1, USER_2, USER_3, USER_4, USER_5);
    }

    @Test
    void testValidation() {
        validateRootCause(() -> userService.create(new User(null, "  ", "mail@yandex.ru", "password",
                Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "  ", "password",
                Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "  ",
                Role.ROLE_USER)), ConstraintViolationException.class);
    }

}