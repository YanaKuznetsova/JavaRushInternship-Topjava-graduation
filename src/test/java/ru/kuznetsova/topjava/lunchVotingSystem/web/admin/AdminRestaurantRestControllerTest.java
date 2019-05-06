package ru.kuznetsova.topjava.lunchVotingSystem.web.admin;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.kuznetsova.topjava.lunchVotingSystem.TestUtil;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Dish;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Restaurant;
import ru.kuznetsova.topjava.lunchVotingSystem.service.RestaurantService;
import ru.kuznetsova.topjava.lunchVotingSystem.web.AbstractControllerTest;
import ru.kuznetsova.topjava.lunchVotingSystem.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.kuznetsova.topjava.lunchVotingSystem.RestaurantTestData.*;
import static ru.kuznetsova.topjava.lunchVotingSystem.TestUtil.readFromJsonResultActions;
import static ru.kuznetsova.topjava.lunchVotingSystem.TestUtil.readListFromJsonMvcResult;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.REST_URL + '/';

    @Autowired
    protected RestaurantService restaurantService;

    @Test
    void getAllRestaurants() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(RESTAURANT_1, RESTAURANT_2, RESTAURANT_4, RESTAURANT_3, RESTAURANT_5)));
    }

    @Test
    void getAllDistinctRestaurantNames() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "names"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertThat((readListFromJsonMvcResult(result, String.class))).
                        isEqualTo(List.of(R1_NAME, R2_NAME, R3_NAME))));
    }

    @Test
    void getRestaurantById() throws Exception {
        mockMvc.perform(get(REST_URL + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(RESTAURANT_1));
    }

    @Test
    void getAllRestaurantsForDate() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "all/" + MAY_31_2015))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getRestaurantMatcher(RESTAURANT_4, RESTAURANT_5)));
    }

    @Test
    void getMenuForRestaurant() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "menu/" + RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH_R1_4, DISH_R1_3, DISH_R1_2, DISH_R1_1)));
    }

    @Test
    void getMenuForDate() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL + "menu/date/" + MAY_30_2015))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(getDishMatcher(DISH_R1_4, DISH_R1_3, DISH_R1_2, DISH_R1_1,
                        DISH_R2_4, DISH_R2_3, DISH_R2_2, DISH_R2_1, DISH_R3_4, DISH_R3_3, DISH_R3_2, DISH_R3_1)));
    }

    @Test
    void addRestaurant() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant", LocalDate.now());
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant returnedRestaurant = readFromJsonResultActions(action, Restaurant.class);
        newRestaurant.setId(returnedRestaurant.getId());

        assertMatchRestaurants(returnedRestaurant, newRestaurant);
        assertMatchRestaurants(restaurantService.getAllRestaurants(),
                RESTAURANT_1, RESTAURANT_2, RESTAURANT_4, newRestaurant, RESTAURANT_3, RESTAURANT_5);
    }

    @Test
    void addDish() throws Exception {
        Dish newDish = new Dish(null, "New dish", 200);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish returnedDish = readFromJsonResultActions(action, Dish.class);
        newDish.setId(returnedDish.getId());

        assertMatchDishes(returnedDish, newDish);
        Restaurant restaurant = restaurantService.getRestaurantById(RESTAURANT_ID);
        assertMatchDishes(restaurant.getDishes(), DISH_R1_1, DISH_R1_2, DISH_R1_3, DISH_R1_4, newDish);
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatchRestaurants(restaurantService.getAllRestaurants(),
                RESTAURANT_2, RESTAURANT_4, RESTAURANT_3, RESTAURANT_5);
    }

    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(delete(REST_URL + RESTAURANT_ID + "/" + DISH_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Dish> dishesForRestaurant = restaurantService.getMenuForRestaurant(RESTAURANT_ID);
        assertMatchDishes(dishesForRestaurant, DISH_R1_4, DISH_R1_3, DISH_R1_2);
    }

    @Test
    void updateRestaurant() throws Exception {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatchRestaurants(restaurantService.getRestaurantById(RESTAURANT_ID), updated);
    }

    @Test
    void updateDish() throws Exception {
        Dish updated = new Dish(DISH_R1_1);
        updated.setName("UpdatedName");
        mockMvc.perform(put(REST_URL + RESTAURANT_ID + "/" + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        Restaurant restaurant = restaurantService.getRestaurantById(RESTAURANT_ID);
        assertMatchDishes(restaurant.getDishes(), updated, DISH_R1_2, DISH_R1_3, DISH_R1_4);
    }

}