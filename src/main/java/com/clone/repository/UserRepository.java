package com.clone.repository;

import com.clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query("SELECT u FROM User WHERE u.full_name LIKE %:name% OR u.email LIKE %:name%")
    List<User> searchUsers(@Param("name") String name);

}
