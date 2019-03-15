package ru.kuznetsova.topjava.graduation.model;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
@NamedQuery(name = Dish.GET_FOR_DATE, query = "SELECT d FROM Dish d " +
        "WHERE d.restaurant.id =: restaurantId AND d.restaurant.date =: date ORDER BY d.name ASC")
public class Dish extends AbstractEntity {

    public static final String GET_FOR_DATE = "Dish.getForDate";

    @Column(name = "price", nullable = false)
    @NotBlank
    @Range(min = 1, max = 10000)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @BatchSize(size = 200)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, @NotBlank @Range(min = 1, max = 10000) int price,
                @NotNull Restaurant restaurant) {
        super(id);
        this.price = price;
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
