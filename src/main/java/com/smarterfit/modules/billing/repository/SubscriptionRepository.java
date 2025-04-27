package com.smarterfit.modules.billing.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smarterfit.common.enums.SubscriptionStatus;
import com.smarterfit.modules.billing.entity.Subscription;

@Repository
public interface SubscriptionRepository
      extends JpaRepository<Subscription, UUID>, JpaSpecificationExecutor<Subscription> {
   List<Subscription> findByStatusAndEndedInBefore(SubscriptionStatus status, LocalDateTime date);

   List<Subscription> findByOwnerId(UUID ownerId);

   @Modifying
   @Query("UPDATE Subscription s SET s.status = :status WHERE s.plan.id = :planId")
   void updateStatusByPlanId(@Param("planId") UUID planId, @Param("status") SubscriptionStatus status);

   @Query("""
      SELECT su.subscription
      FROM SubscriptionUser su
      JOIN su.subscription s
      JOIN s.plan p
      JOIN p.classGroupPlans cgp
      JOIN cgp.classGroup cg
      WHERE su.user.id = :participantId
         AND s.status = 'ACTIVE'
         AND s.availableClasses > 0
         AND cg.id = :classGroupId
      """)
   Optional<Subscription> findFirstActiveSubscriptionGivingAccessToClassGroup(
         @Param("classGroupId") UUID classGroupId,
         @Param("participantId") UUID participantId
   );
   
}
