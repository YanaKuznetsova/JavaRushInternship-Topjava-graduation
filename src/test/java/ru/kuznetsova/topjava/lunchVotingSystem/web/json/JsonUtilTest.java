package ru.kuznetsova.topjava.lunchVotingSystem.web.json;

import org.junit.jupiter.api.Test;
import ru.kuznetsova.topjava.lunchVotingSystem.UserTestData;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Dish;
import ru.kuznetsova.topjava.lunchVotingSystem.model.User;

import java.util.List;

import static ru.kuznetsova.topjava.lunchVotingSystem.RestaurantTestData.DISHES_R1;
import static ru.kuznetsova.topjava.lunchVotingSystem.RestaurantTestData.assertMatchDishes;
import static ru.kuznetsova.topjava.lunchVotingSystem.UserTestData.USER_1;

class JsonUtilTest {

   @Test
    void testReadWriteValue() {
        String json = JsonUtil.writeValue(USER_1);
        System.out.println(json);
        User user = JsonUtil.readValue(json, User.class);
        UserTestData.assertMatchUsers(user, USER_1);
    }

    @Test
    void testReadWriteValues() {
        String json = JsonUtil.writeValue(DISHES_R1);
        System.out.println(json);
        List<Dish> dishes = JsonUtil.readValues(json, Dish.class);
        assertMatchDishes(dishes, DISHES_R1);
    }
}