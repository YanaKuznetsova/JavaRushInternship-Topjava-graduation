package ru.kuznetsova.topjava.lunchVotingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.kuznetsova.topjava.lunchVotingSystem.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish dish);

    @Transactional
    @Modifying
    int deleteById(int id);

    Optional<Dish> findById(int id);

    @Query(name = Dish.GET_FOR_DATE)
    List<Dish> getMenuForDate(@Param("date") LocalDate date, @Param("restaurantId") int restaurantId);

    @Query(name = Dish.GET_MENUS_FOR_DATE)
    List<Dish> getAllMenusForDate(@Param("date") LocalDate date);

}
