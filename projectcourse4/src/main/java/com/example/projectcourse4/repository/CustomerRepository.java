package com.example.projectcourse4.repository;

import com.example.projectcourse4.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // тут jpa под капотом создас за вас запрос кидающий в БД
    // то есть вы просто пишите имя метода и jpa понимает какой запрос строить
    Customer findByCustomerName(String customerName);


    //    // для проверки email username
//    @Query("select case when count(u)>0 then true else false end from User u where lower(u.email) = lower(:email)")
//    boolean existsByEmail(@Param("email") String email);
//
//    @Query("select case when count(u)> 0 then true else false end from Group u where lower(u.groupName) = lower(:group_name)")
//    boolean existsBygroupName(@Param("group_name") String group_name);


}
