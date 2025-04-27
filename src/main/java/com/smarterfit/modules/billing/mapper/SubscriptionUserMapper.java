package com.smarterfit.modules.billing.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.modules.billing.dto.response.subscriptionuser.SubscriptionUserResponseDTO;
import com.smarterfit.modules.billing.entity.SubscriptionUser;
import com.smarterfit.modules.useraccess.mapper.UserMapper;

public class SubscriptionUserMapper {
   private SubscriptionUserMapper() {
      // Private constructor to prevent instantiation
   }

   public static SubscriptionUserResponseDTO toResponse(SubscriptionUser subscriptionUser) {
      if (subscriptionUser == null) {
         throw new ResourceNotFoundException("SubscriptionUser cannot be null");
      }

      return SubscriptionUserResponseDTO.builder()
            .user(UserMapper.toResponse(subscriptionUser.getUser()))
            .subscription(SubscriptionMapper.toResponse(subscriptionUser.getSubscription()))
            .build();
   }
}
