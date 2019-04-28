package ru.kuznetsova.topjava.lunchVotingSystem.web.user;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.kuznetsova.topjava.lunchVotingSystem.model.User;
import ru.kuznetsova.topjava.lunchVotingSystem.web.AbstractUserRestController;
import ru.kuznetsova.topjava.lunchVotingSystem.web.SecurityUtil;

@RestController
@RequestMapping(UserProfileRestController.REST_URL)

public class UserProfileRestController extends AbstractUserRestController {

    static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() throws NotFoundException {
        return super.get(SecurityUtil.authUserId());
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() throws NotFoundException {
        super.delete(SecurityUtil.authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) throws NotFoundException {
        super.update(user, SecurityUtil.authUserId());
    }

}