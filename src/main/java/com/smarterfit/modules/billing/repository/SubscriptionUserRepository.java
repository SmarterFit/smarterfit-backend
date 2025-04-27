package com.smarterfit.modules.billing.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarterfit.modules.billing.entity.SubscriptionUser;
import com.smarterfit.modules.billing.entity.id.SubscriptionUserId;

@Repository
public interface SubscriptionUserRepository extends JpaRepository<SubscriptionUser, SubscriptionUserId> {
   // TODO: Retornar Subscriptions e Users diretamente
   List<SubscriptionUser> findBySubscriptionId(UUID subscriptionId);
   List<SubscriptionUser> findByUserId(UUID userId);
   Boolean existsBySubscriptionIdAndUserId(UUID subscriptionId, UUID userId);
}
