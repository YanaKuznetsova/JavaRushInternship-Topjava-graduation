package ru.kuznetsova.topjava.graduation;

import org.assertj.core.api.Assertions;
import ru.kuznetsova.topjava.graduation.model.Dish;
import ru.kuznetsova.topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

import static ru.kuznetsova.topjava.graduation.model.AbstractEntity.START_SEQ;

public class RestaurantTestData {

    public static final int RESTAURANT_ID = START_SEQ + 6;
    public static final int DISH_ID = START_SEQ + 11;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_ID, "Dinner In The Sky", LocalDate.of(2015, 05, 30));
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_ID + 1, "Funky Gourmet", LocalDate.of(2015, 05, 30));
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_ID + 2, "Scala Vinoteca", LocalDate.of(2015, 05, 30));
    public static final Restaurant RESTAURANT_4 = new Restaurant(RESTAURANT_ID + 3, "Funky Gourmet", LocalDate.of(2015, 05, 31));
    public static final Restaurant RESTAURANT_5 = new Restaurant(RESTAURANT_ID + 4, "Scala Vinoteca", LocalDate.of(2015, 05, 31));

    public static final Dish DISH_R1_1 = new Dish(DISH_ID, "Soup-1", 100, RESTAURANT_1);
    public static final Dish DISH_R1_2 = new Dish(DISH_ID + 1, "Salad-1", 100, RESTAURANT_1);
    public static final Dish DISH_R1_3 = new Dish(DISH_ID + 2, "Main course-1", 100, RESTAURANT_1);
    public static final Dish DISH_R1_4 = new Dish(DISH_ID + 3, "Desert-1", 100, RESTAURANT_1);

    public static final Dish DISH_R2_1 = new Dish(DISH_ID + 4, "Soup-2", 100, RESTAURANT_2);
    public static final Dish DISH_R2_2 = new Dish(DISH_ID + 5, "Salad-2", 100, RESTAURANT_2);
    public static final Dish DISH_R2_3 = new Dish(DISH_ID + 6, "Main course-2", 100, RESTAURANT_2);
    public static final Dish DISH_R2_4 = new Dish(DISH_ID + 7, "Desert-2", 100, RESTAURANT_2);

    public static final Dish DISH_R3_1 = new Dish(DISH_ID + 8, "Soup-3", 100, RESTAURANT_3);
    public static final Dish DISH_R3_2 = new Dish(DISH_ID + 9, "Salad-3", 100, RESTAURANT_3);
    public static final Dish DISH_R3_3 = new Dish(DISH_ID + 10, "Main course-3", 100, RESTAURANT_3);
    public static final Dish DISH_R3_4 = new Dish(DISH_ID + 11, "Desert-3", 100, RESTAURANT_3);

    public static final Dish DISH_R4_1 = new Dish(DISH_ID + 12, "Soup-4", 100, RESTAURANT_4);
    public static final Dish DISH_R4_2 = new Dish(DISH_ID + 13, "Salad-4", 100, RESTAURANT_4);
    public static final Dish DISH_R4_3 = new Dish(DISH_ID + 14, "Main course-4", 100, RESTAURANT_4);
    public static final Dish DISH_R4_4 = new Dish(DISH_ID + 15, "Desert-4", 100, RESTAURANT_4);

    public static final Dish DISH_R5_1 = new Dish(DISH_ID + 16, "Soup-5", 100, RESTAURANT_5);
    public static final Dish DISH_R5_2 = new Dish(DISH_ID + 17, "Salad-5", 100, RESTAURANT_5);
    public static final Dish DISH_R5_3 = new Dish(DISH_ID + 18, "Main course-5", 100, RESTAURANT_5);
    public static final Dish DISH_R5_4 = new Dish(DISH_ID + 19, "Desert-5", 100, RESTAURANT_5);

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        Assertions.assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }


}