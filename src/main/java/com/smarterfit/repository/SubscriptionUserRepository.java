package com.smarterfit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.model.SubscriptionUser.SubscriptionUser;
import com.smarterfit.model.SubscriptionUser.SubscriptionUserId;

@Repository
public interface SubscriptionUserRepository extends JpaRepository<SubscriptionUser, SubscriptionUserId> {
}
