package ru.kuznetsova.topjava.graduation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kuznetsova.topjava.graduation.model.Rating;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RatingRepository {

    private final CrudRatingRepository crudRatingRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public RatingRepository(CrudRatingRepository crudRatingRepository,
                            CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRatingRepository = crudRatingRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Rating save(Rating rating, Integer restaurantId) {
        rating.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudRatingRepository.save(rating);
    }

    public List<Rating> ratingForToday() {
        return crudRatingRepository.getForDate(LocalDate.now());
    }

    public List<Rating> ratingForDate(LocalDate date) {
        return crudRatingRepository.getForDate(date);
    }

    public Rating ratingForRestaurantForToday(Integer restaurantId) {
        return crudRatingRepository.getForRestaurantForDate(restaurantId, LocalDate.now()).orElse(null);
    }

    public Rating ratingForRestaurantForDate(Integer restaurantId, LocalDate date) {
        return crudRatingRepository.getForRestaurantForDate(restaurantId, date).orElse(null);
    }

    public List<Rating> ratingForRestaurant(Integer restaurantId) {
        return crudRatingRepository.getForRestaurant(restaurantId);
    }

}
