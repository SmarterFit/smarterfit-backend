package com.smarterfit.modules.billing.validation;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.entity.SubscriptionUser;
import com.smarterfit.modules.billing.entity.id.SubscriptionUserId;
import com.smarterfit.modules.billing.repository.SubscriptionUserRepository;
import com.smarterfit.modules.useraccess.entity.User;

@Component
public class SubscriptionUserValidation {
   private final SubscriptionUserRepository subscriptionUserRepository;

   public SubscriptionUserValidation(SubscriptionUserRepository subscriptionUserRepository) {
      this.subscriptionUserRepository = subscriptionUserRepository;
   }

   public SubscriptionUser validateSubscriptionUserById(SubscriptionUserId subscriptionUserId) {
      return subscriptionUserRepository
            .findById(subscriptionUserId)
            .orElseThrow(() -> new ResourceNotFoundException("Subscription user not found."));
   }

   public void validateAvailableMembers(Subscription subscription) {
      if (subscription.getAvailableMembers() <= 0) {
         throw new BusinessException("Not enough available members in the subscription.");
      }
   }

   public void validateUserNotInSubscription(Subscription subscription, User user) {
      boolean participantExists = subscriptionUserRepository.existsBySubscriptionIdAndUserId(subscription.getId(),
            user.getId());

      if (participantExists) {
         throw new BusinessException("User already in the subscription.");
      }
   }

   public void validateUserJoinedMoreThanDaysAgo(SubscriptionUser subscriptionUser, Integer days) {
      if (subscriptionUser.getCreatedAt().isBefore(LocalDateTime.now().minusDays(days))) {
         throw new BusinessException("User joined more than " + days + " days ago.");
      }
   }
}
