package com.smarterfit.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.smarterfit.dto.request.SubscriptionRequestDTO;
import com.smarterfit.dto.response.SubscriptionResponseDTO;
import com.smarterfit.model.SubscriptionUser.Subscription;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
   public Subscription toEntity(SubscriptionRequestDTO subscriptionRequestDTO, @MappingTarget Subscription subscription);
   public Subscription toEntity(SubscriptionRequestDTO subscriptionRequestDTO);
   public SubscriptionResponseDTO toResponse(Subscription subscription);
}
