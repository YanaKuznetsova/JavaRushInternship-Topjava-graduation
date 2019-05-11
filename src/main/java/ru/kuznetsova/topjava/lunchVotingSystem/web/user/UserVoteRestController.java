package ru.kuznetsova.topjava.lunchVotingSystem.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Rating;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Vote;
import ru.kuznetsova.topjava.lunchVotingSystem.service.RatingService;
import ru.kuznetsova.topjava.lunchVotingSystem.util.exception.NotFoundException;
import ru.kuznetsova.topjava.lunchVotingSystem.web.SecurityUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(UserVoteRestController.REST_URL)
public class UserVoteRestController {

    static final String REST_URL = "/rest/rating";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RatingService ratingService;

    @GetMapping(value = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Rating> getRatingForToday() throws NotFoundException {
        log.info("get rating for today");
        return ratingService.getRatingForToday();
    }

    @GetMapping(value = "/rating/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rating getRatingForRestaurantForToday(@PathVariable("id") int id) throws NotFoundException {
        log.info("get rating for restaurant {} for today", id);
        return ratingService.getRatingForRestaurantForToday(id);
    }

    @PostMapping(value = "/vote", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> voteForRestaurant(@RequestBody Vote vote) {
        int userId = SecurityUtil.authUserId();
        Integer restaurantId = vote.getRestaurant().getId();
        log.info("add vote for restaurant {} by user {}", restaurantId, userId);
        ratingService.voteForRestaurant(restaurantId, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/rating/{restaurantId}")
                .buildAndExpand(restaurantId).toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurantId);
    }

}


