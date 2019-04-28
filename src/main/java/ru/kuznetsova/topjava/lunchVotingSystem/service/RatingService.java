package ru.kuznetsova.topjava.lunchVotingSystem.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Rating;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Vote;
import ru.kuznetsova.topjava.lunchVotingSystem.repository.RatingRepository;
import ru.kuznetsova.topjava.lunchVotingSystem.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.kuznetsova.topjava.lunchVotingSystem.model.Vote.DECISION_TIME;
import static ru.kuznetsova.topjava.lunchVotingSystem.util.ValidationUtil.checkNotFound;

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
    public Vote voteForRestaurant(Integer restaurantId, Integer userId) {
        return voteForRestaurant(restaurantId, userId, LocalDate.now(), LocalTime.now());
    }

    @Transactional
    Vote voteForRestaurant(Integer newRestaurantId, Integer userId, LocalDate date, LocalTime time) {
        Assert.notNull(newRestaurantId, "newRestaurantId must not be null");
        Vote vote = null;
        if (time.isBefore(DECISION_TIME)) {
            vote = getVoteForUserForDate(userId, date);
            if (vote != null) {
                voteRepository.deleteVote(vote);
                Integer oldRestaurantId = vote.getRestaurant().getId();
                ratingRepository.decreaseRating(oldRestaurantId, date);
            }
            vote = new Vote(date);
            voteRepository.save(vote, userId, newRestaurantId);
            ratingRepository.addNewVote(newRestaurantId, date);
        }
        return vote;
    }

    public Rating getRatingForRestaurantForToday(Integer restaurantId) throws NotFoundException {
        Assert.notNull(restaurantId, "newRestaurantId must not be null");
        return checkNotFound(ratingRepository.ratingForRestaurantForDate(restaurantId, LocalDate.now()),
                "restaurant=" + restaurantId);
    }

    public Rating getRatingForRestaurantForDate(Integer restaurantId, LocalDate date) throws NotFoundException {
        Assert.notNull(restaurantId, "newRestaurantId must not be null");
        return checkNotFound(ratingRepository.ratingForRestaurantForDate(restaurantId, date),
                "restaurant=" + restaurantId + " date=" + date.toString());
    }

    public Vote getVoteForUserForDate(Integer userId, LocalDate date) {
        return voteRepository.getByUserAndDate(userId, date);
    }

    public List<Vote> getAllVotes() {
        return voteRepository.getAllVotes();
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
    public List<Rating> getRatingForToday() throws NotFoundException {
        return checkNotFound(ratingRepository.ratingForDate(LocalDate.now()), "rating for today");
    }

    public List<Rating> getRatingForDate(LocalDate date) throws NotFoundException {
        return checkNotFound(ratingRepository.ratingForDate(date), "rating for day=" + date.toString());
    }

    public List<Rating> getRatingForRestaurantName(Integer restaurantId) throws NotFoundException {
        Assert.notNull(restaurantId, "newRestaurantId must not be null");
        return checkNotFound(ratingRepository.ratingForRestaurantName(restaurantId),
                "restaurant=" + restaurantId);
    }

}
