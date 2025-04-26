package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.subscription.SubscriptionDTO;
import com.smarterfit.dto.response.SubscriptionResponseDTO;
import com.smarterfit.enums.SubscriptionStatus;
import com.smarterfit.model.Plan;
import com.smarterfit.model.SubscriptionUser.Subscription;
import com.smarterfit.model.SubscriptionUser.SubscriptionUser;
import com.smarterfit.model.User;

public class SubscriptionMapper {
      public static Subscription toEntity(User owner, Plan plan, SubscriptionDTO dto) {
            Boolean addOwnerAsParticipant = dto.addOwnerAsParticipant() != null ? dto.addOwnerAsParticipant() : true;

            Subscription subscription = new Subscription();
            subscription.setOwner(owner);
            subscription.setPlan(plan);
            subscription.setStatus(SubscriptionStatus.PENDING);
            subscription.setAvailableClasses(plan.getMaxClasses());

            if (addOwnerAsParticipant) {
                  SubscriptionUser subscriptionUser = new SubscriptionUser();
                  subscriptionUser.setUser(owner);
                  subscriptionUser.setSubscription(subscription);
                  subscription.getParticipants().add(subscriptionUser);
                  subscription.setAvailableMembers(plan.getMaxUsers() - 1);
            } else {
                  subscription.setAvailableMembers(plan.getMaxUsers());
            }

            return subscription;
      }

      public static SubscriptionResponseDTO toResponse(Subscription subscription) {
            if (subscription == null) {
                  return null;
            }

            return new SubscriptionResponseDTO(
                        subscription.getId(),
                        UserMapper.toResponse(subscription.getOwner()),
                        subscription.getStartedIn(),
                        subscription.getRenewedIn(),
                        subscription.getEndedIn(),
                        subscription.getStatus(),
                        subscription.getAvailableMembers(),
                        subscription.getAvailableClasses());
      }
}