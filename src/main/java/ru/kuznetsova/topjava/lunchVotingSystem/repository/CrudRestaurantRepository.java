package ru.kuznetsova.topjava.lunchVotingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    int deleteById(int id);

    Optional<Restaurant> findById(Integer id);

    @Query(name = Restaurant.ALL_DISTINCT_NAMES_SORTED)
    List<String> getAllDistinctNames();

    @Query(name = Restaurant.ALL_FOR_DATE)
    List<Restaurant> getAllForDate(@Param("date") LocalDate date);

    @Query(name = Restaurant.FOR_DATE)
    Optional<Restaurant> getForDate(@Param("date") LocalDate date, @Param("id") Integer id);

    @Query(name = Restaurant.ALL_SORTED)
    List<Restaurant> getAll();
}