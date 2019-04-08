package ru.kuznetsova.topjava.graduation.service;

import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataAccessException;
import ru.kuznetsova.topjava.graduation.model.Role;
import ru.kuznetsova.topjava.graduation.model.User;
import ru.kuznetsova.topjava.graduation.util.JpaUtil;

import javax.validation.ConstraintViolationException;

import static ru.kuznetsova.topjava.graduation.UserTestData.*;
import static ru.kuznetsova.topjava.graduation.model.Role.ROLE_USER;


public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void create() {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", ROLE_USER);
        User created = userService.create(newUser);
        newUser.setId(created.getId());
        assertMatch(userService.getAll(), ADMIN, newUser, USER_1, USER_2, USER_3, USER_4, USER_5);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateMailCreate() {
        userService.create(new User(null, "Duplicate", "user-1@yandex.ru", "newPass", ROLE_USER));
    }

    @Test
    public void delete() throws Exception {
        userService.delete(USER_ID);
        assertMatch(userService.getAll(), ADMIN, USER_2, USER_3, USER_4, USER_5);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        userService.delete(1);
    }

    @Test
    public void get() throws Exception {
        User user = userService.get(USER_ID);
        assertMatch(user, USER_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        userService.get(1);
    }

    @Test
    public void getByEmail() throws NotFoundException {
        User user = userService.getByEmail("user-1@yandex.ru");
        assertMatch(user, USER_1);
    }

    @Test
    public void update() throws NotFoundException {
        User updated = new User(USER_1);
        updated.setName("UpdatedName");
        userService.update(updated);
        assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    public void getAll() {
        assertMatch(userService.getAll(), ADMIN, USER_1, USER_2, USER_3, USER_4, USER_5);
    }

    @Test
    public void testValidation() {
        validateRootCause(() -> userService.create(new User(null, "  ", "mail@yandex.ru", "password",
                Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "  ", "password",
                Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@yandex.ru", "  ",
                Role.ROLE_USER)), ConstraintViolationException.class);
    }

}