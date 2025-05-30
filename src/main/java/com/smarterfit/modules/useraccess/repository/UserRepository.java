package com.smarterfit.modules.useraccess.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.useraccess.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :emailPart, '%'))")
    List<User> findByEmailContainingIgnoreCase(@Param("emailPart") String emailPart);

}
