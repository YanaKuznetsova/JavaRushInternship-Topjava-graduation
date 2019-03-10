package ru.kuznetsova.topjava.graduation.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes")
@NamedQueries({
        @NamedQuery(name = Vote.DELETE_FOR_DATE, query = "DELETE FROM Vote v WHERE v.date >: date"),
        @NamedQuery(name = Vote.GET, query = "SELECT v FROM Vote v WHERE v.user.id =: userId ORDER BY v.restaurant.name")
})
public class Vote {

    public static final String DELETE_FOR_DATE = "Vote.deleteForDate";
    public static final String GET = "Vote.get";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    public Vote() {
    }

    public Vote(@NotNull User user, @NotNull Restaurant restaurant, @NotNull LocalDate date) {
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
