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

import com.smarterfit.common.enums.PaymentStatus;
import com.smarterfit.modules.billing.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID>, JpaSpecificationExecutor<Payment> {
      List<Payment> findBySubscriptionOwnerId(UUID subscriptionOwnerId);

      Optional<Payment> findBySubscriptionIdAndStatus(UUID subscriptionId, PaymentStatus status);

      @Modifying
      @Query("UPDATE payment p SET p.status = :status WHERE p.subscription.id = :subscriptionId AND p.status = :originalStatus")
      void updateStatusBySubscriptionId(@Param("subscriptionId") UUID subscriptionId,
                  @Param("status") PaymentStatus status, @Param("originalStatus") PaymentStatus originalStatus);

      @Modifying
      @Query("UPDATE payment p SET p.status = :status WHERE p.subscription.plan.id = :planId AND p.status = :originalStatus")
      void updateStatusByPlanId(@Param("planId") UUID planId, @Param("status") PaymentStatus status,
                  @Param("originalStatus") PaymentStatus originalStatus);

      List<Payment> findByStatusAndExpirationInBefore(PaymentStatus status, LocalDateTime date);
}
