package com.example.projectcourse4.repository;

import com.example.projectcourse4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    // тут jpa под капотом создас за вас запрос кидающий в БД
    // то есть вы просто пишите имя метода и jpa понимает какой запрос строить
    Optional<User> findUserByUsername(String username);


//    // для проверки email username
//    @Query("select case when count(u)>0 then true else false end from User u where lower(u.email) = lower(:email)")
//    boolean existsByEmail(@Param("email") String email);
//
    @Query("select case when count(u)> 0 then true else false end from User u where lower(u.username) = lower(:username)")
    boolean existsByUsername(@Param("username") String username);


    Optional<User> findByEmail(String email);
}
