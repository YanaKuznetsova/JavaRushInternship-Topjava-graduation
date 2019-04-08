package ru.kuznetsova.topjava.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.kuznetsova.topjava.graduation.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    @Override
    @Transactional
    User save(User user);

    @Transactional
    @Modifying
    @Query(name = User.DELETE)
    int deleteById(@Param("id") int id);

    @Override
    Optional<User> findById(Integer id);

    @Query(name = User.ALL_SORTED)
    List<User> getAll();

    User getByEmail(String email);

}