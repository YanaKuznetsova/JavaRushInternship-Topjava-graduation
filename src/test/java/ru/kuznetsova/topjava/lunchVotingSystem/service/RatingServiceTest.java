package ru.kuznetsova.topjava.lunchVotingSystem.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.kuznetsova.topjava.lunchVotingSystem.RatingTestData;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Rating;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Vote;
import ru.kuznetsova.topjava.lunchVotingSystem.util.JpaUtil;

import java.util.List;

import static ru.kuznetsova.topjava.lunchVotingSystem.RatingTestData.*;
import static ru.kuznetsova.topjava.lunchVotingSystem.RestaurantTestData.*;
import static ru.kuznetsova.topjava.lunchVotingSystem.UserTestData.USER_ID;
import static ru.kuznetsova.topjava.lunchVotingSystem.model.Vote.DECISION_TIME;

public class RatingServiceTest extends AbstractServiceTest {

    @Autowired
    protected RatingService ratingService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @BeforeEach
    void setUp() throws Exception {
        cacheManager.getCache("rating").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    void voteForRestaurant() throws NotFoundException {
        ratingService.voteForRestaurant(RESTAURANT_ID, USER_ID, MAY_31_2015, DECISION_TIME.minusHours(1));
        Rating newRating = new Rating(null, RESTAURANT_1, 1, MAY_31_2015);
        assertMatchNewRatings(ratingService.ratingForDate(MAY_31_2015),
                List.of(newRating, RATING_R2_D31, RATING_R3_D31));
    }

    @Test
    void voteForRestaurantChangeDecisionBeforeDecisionTime() throws NotFoundException {
        ratingService.voteForRestaurant(RESTAURANT_ID + 2, USER_ID, MAY_30_2015, DECISION_TIME.minusHours(1));
        Rating newRatingForRestaurant1 = new Rating(null, RESTAURANT_1, 2, MAY_30_2015);
        Rating newRatingForRestaurant3 = new Rating(null, RESTAURANT_3, 2, MAY_30_2015);
        assertMatchNewRatings(ratingService.ratingForDate(MAY_30_2015),
                List.of(newRatingForRestaurant1, RATING_R2_D30, newRatingForRestaurant3));
    }

    @Test
    void voteForRestaurantAfterDecisionTime() throws NotFoundException {
        ratingService.voteForRestaurant(RESTAURANT_ID + 2, USER_ID, MAY_30_2015, DECISION_TIME.plusHours(1));
        assertMatchNewRatings(ratingService.ratingForDate(MAY_30_2015),
                List.of(RATING_R1_D30, RATING_R2_D30, RATING_R3_D30));
    }

    @Test
    void ratingForRestaurantForDate() throws NotFoundException {
        Rating rating = ratingService.ratingForRestaurantForDate(RESTAURANT_ID, MAY_30_2015);
        RatingTestData.assertMatchRatings(rating, RATING_R1_D30);
    }

    @Test
    void deleteOldVotes() {
        ratingService.deleteOldVotes();
        List<Vote> voteList = ratingService.getAllVotes();
        Assertions.assertEquals(0, voteList.size());
    }

    @Test
    void deleteOldVotesForDate() throws NotFoundException {
        ratingService.deleteOldVotesForDate(MAY_31_2015);
        List<Vote> voteList = ratingService.getAllVotes();
        assertMatchVotes(voteList, VOTE_U2_D31, VOTE_U3_D31, VOTE_U4_D31, VOTE_U5_D31);
    }

    @Test
    void ratingForDate() throws NotFoundException {
        List<Rating> ratingList = ratingService.ratingForDate(MAY_30_2015);
        assertMatchRatings(ratingList, RATING_R1_D30, RATING_R2_D30, RATING_R3_D30);
    }

    @Test
    void ratingForRestaurant() throws NotFoundException {
        List<Rating> raitingList = ratingService.ratingForRestaurantName(RESTAURANT_ID + 1);
        assertMatchRatings(raitingList, RATING_R2_D31, RATING_R2_D30);
    }

    @Test
    void getVoteForUserForDate() {
        Vote vote = ratingService.getVoteForUserForDate(USER_ID, MAY_30_2015);
        assertMatchVotes(vote, VOTE_U1_D30);
    }

}
