package com.smarterfit.util.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.smarterfit.dto.response.PlanResponseDTO;
import com.smarterfit.dto.response.SubscriptionResponseDTO;
import com.smarterfit.dto.response.SubscriptionShortResponseDTO;
import com.smarterfit.dto.response.UserShortResponseDTO;
import com.smarterfit.model.SubscriptionUser.Subscription;

public class SubscriptionMapper {
   public static SubscriptionShortResponseDTO toShortResponse(Subscription subscription) {
      if (subscription == null) {
         return null;
      }

      PlanResponseDTO plan = PlanMapper.toResponse(subscription.getPlan());

      return new SubscriptionShortResponseDTO(
            subscription.getId(),
            plan,
            subscription.getStartedIn(),
            subscription.getRenewedIn(),
            subscription.getEndedIn(),
            subscription.getStatus().toString(),
            subscription.getAvailableMembers());
   }

   public static SubscriptionResponseDTO toResponse(Subscription subscription) {
      if (subscription == null) {
         return null;
      }

      PlanResponseDTO plan = PlanMapper.toResponse(subscription.getPlan());
      UserShortResponseDTO owner = UserMapper.toShortResponse(subscription.getOwner());
      Set<UserShortResponseDTO> participants = subscription.getParticipants().stream().map(
            participant -> UserMapper.toShortResponse(participant.getUser())).collect(Collectors.toSet());

      return new SubscriptionResponseDTO(
            subscription.getId(),
            owner,
            participants,
            plan,
            subscription.getStartedIn(),
            subscription.getRenewedIn(),
            subscription.getEndedIn(),
            subscription.getStatus().toString(),
            subscription.getAvailableMembers());
   }
}