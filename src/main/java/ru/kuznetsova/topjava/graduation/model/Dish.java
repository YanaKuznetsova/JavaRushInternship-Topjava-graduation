package ru.kuznetsova.topjava.graduation.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractEntity {

    @Column(name = "price", nullable = false)
    @NotBlank
    @Range(min = 1, max = 10000)
    private int price;

    @Column(name = "datetime", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, @NotBlank @Range(min = 1, max = 10000) int price, @NotNull LocalDateTime dateTime,
                @NotNull Restaurant restaurant) {
        super(id);
        this.price = price;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
