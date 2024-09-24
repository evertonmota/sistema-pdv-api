package com.sistemas.pdv.repository;

import com.sistemas.pdv.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u left join fetch u.sales where u.username = :username")
    User findUserByUserName(@Param("username") String username);
}
