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
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) throws NotFoundException {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) throws NotFoundException {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) throws NotFoundException {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public User getByMail(String email) throws NotFoundException {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

}