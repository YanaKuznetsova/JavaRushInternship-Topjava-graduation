package ru.kuznetsova.topjava.graduation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kuznetsova.topjava.graduation.model.Vote;

import java.time.LocalDate;

@Repository
public class VoteRepository {

    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public VoteRepository(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository,
                          CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Vote save(Vote vote, Integer userId, Integer restaurantId) {
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudVoteRepository.save(vote);
    }

    public Vote get(Integer userId) {
        return crudVoteRepository.findByUserId(userId).orElse(null);
    }

    public boolean deleteOldVotes() {
        return crudVoteRepository.deleteForDate(LocalDate.now());
    }

    public boolean deleteOldVotesFromDate(LocalDate date) {
        return crudVoteRepository.deleteForDate(date);
    }


}
