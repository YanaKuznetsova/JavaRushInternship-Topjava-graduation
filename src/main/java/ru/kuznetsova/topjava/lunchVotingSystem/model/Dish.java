package ru.kuznetsova.topjava.lunchVotingSystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
@NamedQueries({
        @NamedQuery(name = Dish.GET_FOR_DATE, query = "SELECT d FROM Dish d " +
                "WHERE d.restaurant.id =: restaurantId AND d.restaurant.date =: date ORDER BY d.name ASC"),
        @NamedQuery(name = Dish.GET_MENUS_FOR_DATE, query = "SELECT d FROM Dish d " +
                "WHERE d.restaurant.date =: date ORDER BY d.restaurant.name, d.name ASC")
})
public class Dish extends AbstractEntity {

    public static final String GET_FOR_DATE = "Dish.getMenuForDate";
    public static final String GET_MENUS_FOR_DATE = "Dish.getMenusForDate";

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1, max = 10000)
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.price);
    }

    public Dish(Integer id, String name, @NotBlank @Range(min = 1, max = 10000) int price) {
        super(id, name);
        this.price = price;
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

    @Override
    public String toString() {
        return "Dish{" +
                "price=" + price +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
