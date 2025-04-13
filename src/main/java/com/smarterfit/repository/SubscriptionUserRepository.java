package com.smarterfit.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.model.SubscriptionUser.SubscriptionUser;

@Repository
public interface SubscriptionUserRepository extends JpaRepository<SubscriptionUser, UUID> {
}
