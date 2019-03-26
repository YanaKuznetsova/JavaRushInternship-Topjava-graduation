package ru.kuznetsova.topjava.graduation.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.kuznetsova.topjava.graduation.model.Rating;
import ru.kuznetsova.topjava.graduation.model.Vote;
import ru.kuznetsova.topjava.graduation.repository.RatingRepository;
import ru.kuznetsova.topjava.graduation.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.kuznetsova.topjava.graduation.model.Vote.DECISION_TIME;
import static ru.kuznetsova.topjava.graduation.util.ValidationUtil.checkNotFound;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, VoteRepository voteRepository) {
        this.ratingRepository = ratingRepository;
        this.voteRepository = voteRepository;
    }

    @CacheEvict(value = "rating", allEntries = true)
    public void voteForRestaurant(Integer restaurantId, Integer userId) {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        if (LocalTime.now().isBefore(DECISION_TIME)) {
            Vote vote = voteRepository.getByUserAndDate(userId, LocalDate.now());
            if (vote == null) {
                vote = new Vote(LocalDate.now());
            }
            voteRepository.save(vote, userId, restaurantId);
            ratingRepository.addNewVote(restaurantId, LocalDate.now());
        }
    }

    public Rating ratingForRestaurantForToday(Integer restaurantId) throws NotFoundException {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        return checkNotFound(ratingRepository.ratingForRestaurantForDate(restaurantId, LocalDate.now()),
                "restaurant=" + restaurantId);
    }

    public Rating ratingForRestaurantForDate(Integer restaurantId, LocalDate date) throws NotFoundException {
        Assert.notNull(restaurantId, "restaurantId must not be null");
        return checkNotFound(ratingRepository.ratingForRestaurantForDate(restaurantId, date),
                "restaurant=" + restaurantId + " date=" + date.toString());
    }

    @CacheEvict(value = "rating", allEntries = true)
    public void deleteOldVotes() {
        voteRepository.deleteOldVotesFromDate(LocalDate.now());
    }

    @CacheEvict(value = "rating", allEntries = true)
    public void deleteOldVotesForDate(LocalDate date) {
        voteRepository.deleteOldVotesFromDate(date);
    }

    @Cacheable("rating")
    public List<Rating> ratingForToday() throws NotFoundException {
        return checkNotFound(ratingRepository.ratingForDate(LocalDate.now()), "rating for today");
    }

    public List<Rating> ratingForDate(LocalDate date) throws NotFoundException {
        return checkNotFound(ratingRepository.ratingForDate(date), "rating for day=" + date.toString());
    }

}
