package ru.kuznetsova.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.kuznetsova.topjava.graduation.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Override
    @Transactional
    Vote save(Vote vote);

    @Transactional
    @Modifying
    @Query(name = Vote.DELETE_FOR_DATE)
    boolean deleteForDate(@Param("date") LocalDate date);

    @Query(name = Vote.GET)
    Optional<Vote> findByUserId(@Param("userId") Integer userId);

}
