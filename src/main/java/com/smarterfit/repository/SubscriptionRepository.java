package com.smarterfit.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.model.SubscriptionUser.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
}
