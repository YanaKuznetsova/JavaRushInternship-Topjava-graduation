package ru.kuznetsova.topjava.graduation.service;

import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.kuznetsova.topjava.graduation.model.Dish;
import ru.kuznetsova.topjava.graduation.model.Restaurant;
import ru.kuznetsova.topjava.graduation.util.JpaUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.kuznetsova.topjava.graduation.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    protected RestaurantService restaurantService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        cacheManager.getCache("restaurants").clear();
        cacheManager.getCache("restaurantsForToday").clear();
        cacheManager.getCache("menus").clear();
        cacheManager.getCache("menuForToday").clear();
        jpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void addRestaurant() {
        Restaurant newRestaurant = new Restaurant(null, "New restaurant", LocalDate.now());
        restaurantService.addRestaurant(newRestaurant);
        assertMatch(restaurantService.getAllRestaurants(),
                RESTAURANT_1, RESTAURANT_2, RESTAURANT_4, newRestaurant, RESTAURANT_3, RESTAURANT_5);
    }

    @Test
    public void getRestaurantById() {
        Restaurant restaurant = restaurantService.getRestaurantById(RESTAURANT_ID);
        assertMatch(restaurant, RESTAURANT_1);
        assertMatchDishes(restaurant.getDishes(), DISHES_R1);
    }

    @Test
    public void getRestaurantForDate() throws NotFoundException {
        List<Restaurant> restaurants = restaurantService.getAllRestaurantsForDate(MAY_30_2015);
        assertMatch(restaurants, RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void addDish() {
        Dish newDish = new Dish(null, "New dish", 200);
        restaurantService.addDish(RESTAURANT_ID, newDish);
        Restaurant restaurant = restaurantService.getRestaurantById(RESTAURANT_ID);
        assertMatchDishes(restaurant.getDishes(), DISH_R1_1, DISH_R1_2, DISH_R1_3, DISH_R1_4, newDish);
    }

    @Test
    public void getDishesForDateAndRestaurant() throws NotFoundException {
        List<Dish> dishes = restaurantService.getDishesForDateAndRestaurant(RESTAURANT_ID, MAY_30_2015);
        assertMatchDishes(dishes, DISH_R1_4, DISH_R1_3, DISH_R1_2, DISH_R1_1);
    }

    @Test(expected = NotFoundException.class)
    public void getIncorrectDishesForDateAndRestaurant() throws NotFoundException {
        restaurantService.getDishesForDateAndRestaurant(RESTAURANT_ID, MAY_31_2015);
    }

    @Test
    public void getMenuForDate() throws NotFoundException {
        List<Dish> menuForDate = restaurantService.getMenuForDate(MAY_30_2015);
        assertMatchDishes(menuForDate, DISH_R1_4, DISH_R1_3, DISH_R1_2, DISH_R1_1,
                DISH_R2_4, DISH_R2_3, DISH_R2_2, DISH_R2_1, DISH_R3_4, DISH_R3_3, DISH_R3_2, DISH_R3_1);
    }

    @Test
    public void getAllRestaurantsForDate() throws NotFoundException {
        List<Restaurant> allRestaurantsForDate = restaurantService.getAllRestaurantsForDate(MAY_30_2015);
        assertMatch(allRestaurantsForDate, RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);
    }

    @Test
    public void getAllRestaurants() {
        assertMatch(restaurantService.getAllRestaurants(),
                RESTAURANT_1, RESTAURANT_2, RESTAURANT_4, RESTAURANT_3, RESTAURANT_5);
    }

}
