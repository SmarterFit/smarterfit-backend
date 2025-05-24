package com.smarterfit.modules.billing.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.common.config.BusinessRules;
import com.smarterfit.modules.billing.dto.response.subscription.SubscriptionResponseDTO;
import com.smarterfit.modules.billing.dto.response.subscriptionuser.SubscriptionUserResponseDTO;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.entity.SubscriptionUser;
import com.smarterfit.modules.billing.entity.id.SubscriptionUserId;
import com.smarterfit.modules.billing.mapper.SubscriptionMapper;
import com.smarterfit.modules.billing.mapper.SubscriptionUserMapper;
import com.smarterfit.modules.billing.repository.SubscriptionRepository;
import com.smarterfit.modules.billing.repository.SubscriptionUserRepository;
import com.smarterfit.modules.billing.util.SensitiveBillingDataDecryptor;
import com.smarterfit.modules.billing.validation.SubscriptionUserValidation;
import com.smarterfit.modules.billing.validation.SubscriptionValidation;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.mapper.UserMapper;
import com.smarterfit.modules.useraccess.validation.UserValidation;

@Service
public class SubscriptionUserService {
   private final SubscriptionRepository subscriptionRepository;
   private final SubscriptionUserRepository subscriptionUserRepository;
   private final SubscriptionUserValidation subscriptionUserValidation;
   private final SubscriptionValidation subscriptionValidation;
   private final UserValidation userValidation;
   private final SensitiveBillingDataDecryptor sensitiveBillingDataDecryptor;

   @Autowired
   public SubscriptionUserService(SubscriptionRepository subscriptionRepository,
         SubscriptionUserRepository subscriptionUserRepository,
         SubscriptionUserValidation subscriptionUserValidation,
         SubscriptionValidation subscriptionValidation, UserValidation userValidation,
         SensitiveBillingDataDecryptor sensitiveBillingDataDecryptor) {
      this.subscriptionRepository = subscriptionRepository;
      this.subscriptionUserRepository = subscriptionUserRepository;
      this.subscriptionUserValidation = subscriptionUserValidation;
      this.subscriptionValidation = subscriptionValidation;
      this.userValidation = userValidation;
      this.sensitiveBillingDataDecryptor = sensitiveBillingDataDecryptor;
   }

   @Transactional
   public SubscriptionUserResponseDTO addMemberToSubscription(UUID subscriptionId, UUID userId) {
      Subscription subscription = subscriptionValidation.validateSubscriptionById(subscriptionId);
      subscriptionUserValidation.validateAvailableMembers(subscription);
      User user = userValidation.validateUserById(userId);
      subscriptionUserValidation.validateUserNotInSubscription(subscription, user);

      SubscriptionUser subscriptionUser = new SubscriptionUser();
      subscriptionUser.setUser(user);
      subscriptionUser.setSubscription(subscription);
      subscription.getParticipants().add(subscriptionUser);
      subscription.setAvailableMembers(subscription.getAvailableMembers() - 1);

      subscription = subscriptionRepository.save(subscription);

      return sensitiveBillingDataDecryptor
            .decrypt(SubscriptionUserMapper.toResponse(subscriptionUser));
   }

   @Transactional
   public void removeMemberFromSubscription(UUID subscriptionId,
         UUID userId) {
      SubscriptionUser subscriptionUser = subscriptionUserValidation
            .validateSubscriptionUserById(new SubscriptionUserId(userId, subscriptionId));

      subscriptionUserValidation.validateUserJoinedMoreThanDaysAgo(subscriptionUser,
            BusinessRules.PARTICIPATION_MINIMUM_DAYS);

      Subscription subscription = subscriptionUser.getSubscription();
      subscription.setAvailableMembers(subscription.getAvailableMembers() + 1);
      subscription.getParticipants().remove(subscriptionUser);

      subscription = subscriptionRepository.save(subscription);
   }

   @Transactional(readOnly = true)
   public SubscriptionUserResponseDTO getSubscriptionUser(UUID subscriptionId, UUID userId) {
      SubscriptionUser subscriptionUser = subscriptionUserValidation
            .validateSubscriptionUserById(new SubscriptionUserId(subscriptionId, userId));
      return sensitiveBillingDataDecryptor
            .decrypt(SubscriptionUserMapper.toResponse(subscriptionUser));
   }

   @Transactional(readOnly = true)
   public List<SubscriptionUserResponseDTO> getAllSubscriptionUsers() {
      List<SubscriptionUser> subscriptionUsers = subscriptionUserRepository.findAll();
      return subscriptionUsers.stream().map(subscriptionUser -> sensitiveBillingDataDecryptor
            .decrypt(SubscriptionUserMapper.toResponse(subscriptionUser))).toList();
   }

   @Transactional(readOnly = true)
   public List<UserResponseDTO> getAllUsersBySubscriptionId(UUID subscriptionId) {
      List<SubscriptionUser> subscriptionUsers = subscriptionUserRepository
            .findBySubscriptionId(subscriptionId);

      return subscriptionUsers.stream()
            .map(subscriptionUser -> sensitiveBillingDataDecryptor
                  .decrypt(UserMapper.toResponse(subscriptionUser.getUser())))
            .toList();
   }

   @Transactional(readOnly = true)
   public List<SubscriptionResponseDTO> getAllSubscriptionsByUserId(UUID userId) {
      List<SubscriptionUser> subscriptionUsers = subscriptionUserRepository.findByUserId(userId);
      return subscriptionUsers.stream()
            .map(subscriptionUser -> sensitiveBillingDataDecryptor
                  .decrypt(SubscriptionMapper.toResponse(subscriptionUser.getSubscription())))
            .toList();
   }
}
