package ru.kuznetsova.topjava.graduation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kuznetsova.topjava.graduation.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantRepository {

    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public RestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Restaurant save(Restaurant user) {
        return crudRestaurantRepository.save(user);
    }

    public boolean delete(int id) {
        return crudRestaurantRepository.deleteById(id) != 0;
    }

    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    public Restaurant getForDate(LocalDate date, int id) {
        return crudRestaurantRepository.getForDate(date, id).orElse(null);
    }

    public List<Restaurant> getAllForDate(LocalDate date) {
        return crudRestaurantRepository.getAllForDate(date);
    }

}
