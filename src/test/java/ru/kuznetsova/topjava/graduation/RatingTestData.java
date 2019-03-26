package ru.kuznetsova.topjava.graduation;

import org.assertj.core.api.Assertions;
import ru.kuznetsova.topjava.graduation.model.Rating;
import ru.kuznetsova.topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static ru.kuznetsova.topjava.graduation.RestaurantTestData.*;
import static ru.kuznetsova.topjava.graduation.UserTestData.*;
import static ru.kuznetsova.topjava.graduation.model.AbstractEntity.START_SEQ;

public class RatingTestData {

    public static final int VOTES_ID = START_SEQ + 31;
    public static final int RATING_ID = START_SEQ + 41;

    public static final Vote VOTE_U1_D30 = new Vote(VOTES_ID, USER_1, RESTAURANT_1, LocalDate.of(2015, 5, 30));
    public static final Vote VOTE_U2_D30 = new Vote(VOTES_ID + 1, USER_2, RESTAURANT_1, LocalDate.of(2015, 5, 30));
    public static final Vote VOTE_U3_D30 = new Vote(VOTES_ID + 2, USER_3, RESTAURANT_1, LocalDate.of(2015, 5, 30));
    public static final Vote VOTE_U4_D30 = new Vote(VOTES_ID + 3, USER_4, RESTAURANT_2, LocalDate.of(2015, 5, 30));
    public static final Vote VOTE_U5_D30 = new Vote(VOTES_ID + 4, USER_5, RESTAURANT_3, LocalDate.of(2015, 5, 30));

    public static final Vote VOTE_U1_D31 = new Vote(VOTES_ID + 5, USER_1, RESTAURANT_2, LocalDate.of(2015, 5, 31));
    public static final Vote VOTE_U2_D31 = new Vote(VOTES_ID + 6, USER_2, RESTAURANT_2, LocalDate.of(2015, 5, 31));
    public static final Vote VOTE_U3_D31 = new Vote(VOTES_ID + 7, USER_3, RESTAURANT_2, LocalDate.of(2015, 5, 31));
    public static final Vote VOTE_U4_D31 = new Vote(VOTES_ID + 8, USER_4, RESTAURANT_2, LocalDate.of(2015, 5, 31));
    public static final Vote VOTE_U5_D31 = new Vote(VOTES_ID + 9, USER_5, RESTAURANT_3, LocalDate.of(2015, 5, 31));

    public static final Rating RATING_R1_D30 = new Rating(RATING_ID, RESTAURANT_1, 3, LocalDate.of(2015, 5, 30));
    public static final Rating RATING_R2_D30 = new Rating(RATING_ID + 1, RESTAURANT_2, 1, LocalDate.of(2015, 5, 30));
    public static final Rating RATING_R3_D30 = new Rating(RATING_ID + 2, RESTAURANT_3, 1, LocalDate.of(2015, 5, 30));

    public static final Rating RATING_R2_D31 = new Rating(RATING_ID + 3, RESTAURANT_2, 4, LocalDate.of(2015, 5, 31));
    public static final Rating RATING_R3_D31 = new Rating(RATING_ID + 4, RESTAURANT_3, 1, LocalDate.of(2015, 5, 31));

    public static void assertMatch(Rating actual, Rating expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "restaurant");
    }

    public static void assertMatch(Iterable<Rating> actual, Rating... expected) {
        assertMatch(actual, List.of(expected));
    }

    private static void assertMatch(Iterable<Rating> actual, Iterable<Rating> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }


}