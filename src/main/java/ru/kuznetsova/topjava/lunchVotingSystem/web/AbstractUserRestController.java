package ru.kuznetsova.topjava.lunchVotingSystem.web;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.kuznetsova.topjava.lunchVotingSystem.model.User;
import ru.kuznetsova.topjava.lunchVotingSystem.service.UserService;

import java.util.List;

import static ru.kuznetsova.topjava.lunchVotingSystem.util.ValidationUtil.assureIdConsistent;
import static ru.kuznetsova.topjava.lunchVotingSystem.util.ValidationUtil.checkNew;

public abstract class AbstractUserRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    public List<User> getAll() {
        log.info("get all users");
        return userService.getAll();
    }

    public User get(int id) throws NotFoundException {
        log.info("get user {}", id);
        return userService.get(id);
    }

    public User create(User user) {
        log.info("create user {}", user);
        checkNew(user);
        return userService.create(user);
    }

    public void delete(int id) throws NotFoundException {
        log.info("delete user {}", id);
        userService.delete(id);
    }

    public void update(User user, int id) throws NotFoundException {
        log.info("update user {} with id={}", user, id);
        assureIdConsistent(user, id);
        userService.update(user);
    }

    public User getByMail(String email) throws NotFoundException {
        log.info("get user by email {}", email);
        return userService.getByEmail(email);
    }

}