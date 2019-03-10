package ru.kuznetsova.topjava.graduation.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
public class Vote {

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

    @Column(name = "datetime", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime datetimeOfVote = LocalDateTime.now();


    public Vote() {
    }

    public Vote(User user, Restaurant restaurant, @NotNull LocalDateTime datetimeOfVote) {
        this.user = user;
        this.restaurant = restaurant;
        this.datetimeOfVote = datetimeOfVote;
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

    public LocalDateTime getDatetimeOfVote() {
        return datetimeOfVote;
    }

    public void setDatetimeOfVote(LocalDateTime datetimeOfVote) {
        this.datetimeOfVote = datetimeOfVote;
    }
}
