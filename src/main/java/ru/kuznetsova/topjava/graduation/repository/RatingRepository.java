package ru.kuznetsova.topjava.graduation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Rating save(Rating rating, Integer restaurantId) {
        rating.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudRatingRepository.save(rating);
    }

    public List<Rating> ratingForDate(LocalDate date) {
        return crudRatingRepository.getForDate(date);
    }

    public Rating ratingForRestaurantForDate(Integer restaurantId, LocalDate date) {
        return crudRatingRepository.getForRestaurantForDate(restaurantId, date).orElse(null);
    }

    public List<Rating> ratingForRestaurant(Integer restaurantId) {
        return crudRatingRepository.getForRestaurant(restaurantId);
    }

    @Transactional
    public void addNewVote(Integer restaurantId, LocalDate date) {
        Rating rating = crudRatingRepository.getForRestaurantForDate(restaurantId, date).orElse(null);
        if (rating != null) {
            Integer updatedVotes = rating.getSummaryVotes() + 1;
            rating.setSummaryVotes(updatedVotes);
        } else {
            rating = new Rating(crudRestaurantRepository.getOne(restaurantId), 1, date);
        }
        crudRatingRepository.save(rating);
    }

    public void decreaseRating(Integer restaurantId, LocalDate date) {
        Rating rating = crudRatingRepository.getForRestaurantForDate(restaurantId, date).orElse(null);
        if (rating != null && rating.getSummaryVotes() > 1) {
            Integer updatedVotes = rating.getSummaryVotes() - 1;
            rating.setSummaryVotes(updatedVotes);
            crudRatingRepository.save(rating);
        } else if (rating != null && rating.getSummaryVotes() == 1) {
            crudRatingRepository.delete(rating);
        }
    }

}
