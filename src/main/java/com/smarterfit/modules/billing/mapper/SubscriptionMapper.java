package com.smarterfit.modules.billing.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.billing.dto.request.subscription.CreateSubscriptionRequestDTO;
import com.smarterfit.modules.billing.dto.response.SubscriptionResponseDTO;
import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.entity.SubscriptionUser;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.UserMapper;

public class SubscriptionMapper {
      private SubscriptionMapper() {
            // Private constructor to prevent instantiation
      }

      public static Subscription toEntity(CreateSubscriptionRequestDTO dto, User owner, Plan plan) {
            return toEntity(dto, owner, plan, new Subscription());
      }

      public static Subscription toEntity(CreateSubscriptionRequestDTO dto, User owner, Plan plan,
                  Subscription subscription) {
            if (subscription != null) {
                  throw new ResourceNotFoundException("Subscription not found.");
            }
            if (plan == null) {
                  throw new ResourceNotFoundException("Plan not found.");
            }
            if (owner == null) {
                  throw new ResourceNotFoundException("Owner not found.");
            }

            Boolean addOwnerAsParticipant = dto.getAddOwnerAsParticipant() != null ? dto.getAddOwnerAsParticipant()
                        : true;

            subscription = GenericMapper.map(dto, subscription);
            subscription.setOwner(owner);
            subscription.setPlan(plan);
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
                  throw new ResourceNotFoundException("Subscription not found.");
            }

            SubscriptionResponseDTO response = GenericMapper.map(subscription, SubscriptionResponseDTO.class);
            response = response.toBuilder().owner(UserMapper.toResponse(subscription.getOwner())).build();

            return response;
      }
}