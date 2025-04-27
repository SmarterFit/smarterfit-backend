package com.smarterfit.modules.useraccess.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.smarterfit.modules.useraccess.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
