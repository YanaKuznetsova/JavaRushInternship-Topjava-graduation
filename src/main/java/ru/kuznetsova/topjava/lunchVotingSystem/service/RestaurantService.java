package ru.kuznetsova.topjava.lunchVotingSystem.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Dish;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Restaurant;
import ru.kuznetsova.topjava.lunchVotingSystem.repository.DishRepository;
import ru.kuznetsova.topjava.lunchVotingSystem.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.kuznetsova.topjava.lunchVotingSystem.util.ValidationUtil.checkNotFound;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @CacheEvict(value = {"restaurants", "restaurantsForToday"}, allEntries = true)
    public Restaurant addRestaurant(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantById(Integer id) {
        Assert.notNull(id, "id must be not null");
        return restaurantRepository.get(id);
    }

    @CacheEvict(value = {"menus", "menuForToday"}, allEntries = true)
    public Dish addDish(Integer restaurantId, Dish dish) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish, restaurantId);
    }

    public Restaurant getRestaurantForDate(Integer restaurantId, LocalDate date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(restaurantRepository.getForDate(date, restaurantId),
                "restaurant=" + restaurantId + ", date=" + date.toString());
    }

    public Restaurant getRestaurantForToday(Integer restaurantId) throws NotFoundException {
        return checkNotFound(restaurantRepository.getForDate(LocalDate.now(), restaurantId),
                "restaurant=" + restaurantId);
    }

    public List<Dish> getDishesForDateAndRestaurant(Integer restaurantId, LocalDate date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(dishRepository.getMenuForDate(restaurantId, date),
                "restaurant=" + restaurantId + ", date=" + date.toString());
    }

    public List<Dish> getDishesForTodayAndRestaurant(Integer restaurantId) throws NotFoundException {
        return checkNotFound(dishRepository.getMenuForDate(restaurantId, LocalDate.now()),
                "restaurant=" + restaurantId);
    }

    @Cacheable("menus")
    public List<Dish> getMenuForDate(LocalDate date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(dishRepository.getAllMenusForDate(date),
                "date=" + date.toString());
    }

    @Cacheable("menuForToday")
    public List<Dish> getMenuForToday() throws NotFoundException {
        return checkNotFound(dishRepository.getAllMenusForDate(LocalDate.now()),
                "date=" + LocalDate.now().toString());
    }

    public List<String> getAllDistinctRestaurantsNames() {
        return restaurantRepository.getAllDistinctNames();
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.getAll();
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllRestaurantsForDate(LocalDate date) throws NotFoundException {
        Assert.notNull(date, "date must not be null");
        return checkNotFound(restaurantRepository.getAllForDate(date),
                "date=" + date.toString());
    }

    @Cacheable("restaurantsForToday")
    public List<Restaurant> getAllRestaurantsForToday() throws NotFoundException {
        return checkNotFound(restaurantRepository.getAllForDate(LocalDate.now()),
                "date=" + LocalDate.now().toString());
    }

}
