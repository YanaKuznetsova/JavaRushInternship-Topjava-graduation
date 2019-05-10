package ru.kuznetsova.topjava.lunchVotingSystem.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kuznetsova.topjava.lunchVotingSystem.TestUtil;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Rating;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Vote;
import ru.kuznetsova.topjava.lunchVotingSystem.service.RatingService;
import ru.kuznetsova.topjava.lunchVotingSystem.web.AbstractControllerTest;
import ru.kuznetsova.topjava.lunchVotingSystem.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kuznetsova.topjava.lunchVotingSystem.RatingTestData.*;
import static ru.kuznetsova.topjava.lunchVotingSystem.RestaurantTestData.*;
import static ru.kuznetsova.topjava.lunchVotingSystem.TestUtil.readFromJsonResultActions;
import static ru.kuznetsova.topjava.lunchVotingSystem.TestUtil.userAuth;
import static ru.kuznetsova.topjava.lunchVotingSystem.UserTestData.USER_1;
import static ru.kuznetsova.topjava.lunchVotingSystem.UserTestData.USER_ID;
import static ru.kuznetsova.topjava.lunchVotingSystem.model.Vote.DECISION_TIME;

class UserVoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserVoteRestController.REST_URL + '/';

    @Autowired
    private RatingService ratingService;

    @Test
    void getMenuForRestaurant() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "menu/" + RESTAURANT_ID)
                .with(userAuth(USER_1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH_R1_4, DISH_R1_3, DISH_R1_2, DISH_R1_1)));
    }

    @Test
    void voteForRestaurant() throws Exception {
        Vote newVote = ratingService.voteForRestaurant(RESTAURANT_ID, USER_ID, MAY_31_2015, DECISION_TIME.minusHours(1));

        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + "vote")
                .with(userAuth(USER_1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote returnedVote = readFromJsonResultActions(action, Vote.class);
        newVote.setId(returnedVote.getId());

        assertMatchVotes(returnedVote, newVote);
        Rating newRating = new Rating(null, RESTAURANT_1, 1, MAY_31_2015);
        assertMatchNewRatings(ratingService.getRatingForDate(MAY_31_2015),
                List.of(newRating, RATING_R2_D31, RATING_R3_D31));
    }

}