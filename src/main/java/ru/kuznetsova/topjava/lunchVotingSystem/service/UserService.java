package ru.kuznetsova.topjava.lunchVotingSystem.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.kuznetsova.topjava.lunchVotingSystem.model.User;
import ru.kuznetsova.topjava.lunchVotingSystem.repository.UserRepository;

import java.util.List;

import static ru.kuznetsova.topjava.lunchVotingSystem.util.ValidationUtil.checkNotFound;
import static ru.kuznetsova.topjava.lunchVotingSystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(userRepository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(userRepository.get(id), id);
    }

    public User getByEmail(String email) throws NotFoundException {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) throws NotFoundException {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(userRepository.save(user), user.getId());
    }

}