package ru.kuznetsova.topjava.graduation.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurants",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
@NamedQueries({
        @NamedQuery(name = Restaurant.ALL_SORTED, query = "SELECT r FROM Restaurant r ORDER BY r.name"),
        @NamedQuery(name = Restaurant.FOR_DATE, query = "SELECT r FROM Restaurant r WHERE r.date=:date ORDER BY r.name")
})
public class Restaurant extends AbstractEntity {

    public static final String ALL_SORTED = "Restaurants.getAllSorted";
    public static final String FOR_DATE = "Restaurants.getForDate";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @BatchSize(size = 200)
    private Set<Dish> dishes;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    public Restaurant() {
    }

    public Restaurant(Integer id) {
        super(id);
        this.dishes = new HashSet<>();
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

}
