package com.smarterfit.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.dto.request.SubscriptionByStatusRequestDTO;
import com.smarterfit.dto.request.SubscriptionRequestDTO;
import com.smarterfit.dto.request.SubscriptionUserRequestDTO;
import com.smarterfit.dto.response.SubscriptionResponseDTO;
import com.smarterfit.enums.SubscriptionStatus;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Plan;
import com.smarterfit.model.SubscriptionUser.Subscription;
import com.smarterfit.model.SubscriptionUser.SubscriptionUser;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.SubscriptionRepository;
import com.smarterfit.util.mapper.SubscriptionMapper;
import com.smarterfit.util.validation.PlanValidation;
import com.smarterfit.util.validation.UserValidation;

@Service
public class SubscriptionService {
   private final SubscriptionRepository subscriptionRepository;
   private final PlanValidation planValidation;
   private final UserValidation userValidation;

   @Autowired
   public SubscriptionService(SubscriptionRepository subscriptionRepository, PlanValidation planValidation,
         UserValidation userValidation) {
      this.subscriptionRepository = subscriptionRepository;
      this.planValidation = planValidation;
      this.userValidation = userValidation;
   }

   @Transactional
   public SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO subscriptionRequestDTO) {
      Plan plan = planValidation.findPlanById(subscriptionRequestDTO.planId());
      User user = userValidation.validateUserById(subscriptionRequestDTO.ownerId());

      Subscription subscription = new Subscription();
      subscription.setPlan(plan);
      subscription.setOwner(user);
      subscription.setStatus(SubscriptionStatus.PENDING);
      subscription.setAvailableMembers(plan.getMaxUsers() - 1);

      SubscriptionUser subscriptionUser = new SubscriptionUser();
      subscriptionUser.setUser(user);
      subscriptionUser.setSubscription(subscription);
      subscription.getParticipants().add(subscriptionUser);

      subscriptionRepository.save(subscription);

      return SubscriptionMapper.toResponse(subscription);
   }

   @Transactional(readOnly = true)
   public SubscriptionResponseDTO getSubscriptionById(UUID id) {
      Subscription subscription = findSubscriptionById(id);
      return SubscriptionMapper.toResponse(subscription);
   }

   @Transactional(readOnly = true)
   public List<SubscriptionResponseDTO> getSubscriptionsByStatus(SubscriptionByStatusRequestDTO statusRequestDTO) {
      List<Subscription> subscriptions = subscriptionRepository.findByStatusIn(statusRequestDTO.status());
      return subscriptions.stream()
            .map(SubscriptionMapper::toResponse)
            .collect(Collectors.toList());
   }

   @Transactional
   public SubscriptionResponseDTO addMemberToSubscription(UUID id,
         SubscriptionUserRequestDTO subscriptionUserRequestDTO) {
      Subscription subscription = findSubscriptionById(id);

      if (subscription.getAvailableMembers() <= 0) {
         throw new BusinessException("Not enough available members in the subscription.");
      }

      User user = userValidation.validateUserById(subscriptionUserRequestDTO.userId());

      if (subscription.getParticipants().stream()
            .anyMatch(participant -> participant.getUser().getId().equals(user.getId()))) {
         throw new BusinessException("User already in the subscription.");
      }

      SubscriptionUser subscriptionUser = new SubscriptionUser();
      subscriptionUser.setUser(user);
      subscriptionUser.setSubscription(subscription);
      subscription.getParticipants().add(subscriptionUser);
      subscription.setAvailableMembers(subscription.getAvailableMembers() - 1);

      subscriptionRepository.save(subscription);

      return SubscriptionMapper.toResponse(subscription);
   }

   @Transactional
   public SubscriptionResponseDTO removeMemberFromSubscription(UUID id,
         SubscriptionUserRequestDTO subscriptionUserRequestDTO) {
      Subscription subscription = findSubscriptionById(id);

      SubscriptionUser subscriptionUser = subscription.getParticipants().stream()
            .filter(participant -> participant.getUser().getId().equals(subscriptionUserRequestDTO.userId()))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("User not found in the subscription."));

      subscription.getParticipants().remove(subscriptionUser);
      subscription.setAvailableMembers(subscription.getAvailableMembers() + 1);

      subscriptionRepository.save(subscription);

      return SubscriptionMapper.toResponse(subscription);
   }

   @Transactional
   public void cancelSubscription(UUID id) {
      Subscription subscription = findSubscriptionById(id);
      subscription.setStatus(SubscriptionStatus.CANCELED);
      subscriptionRepository.save(subscription);
   }

   @Transactional
   public void expireSubscriptionsIfNeeded() {
      List<Subscription> subscriptions = subscriptionRepository
            .findByStatusIn(List.of(SubscriptionStatus.ACTIVE));

      for (Subscription subscription : subscriptions) {
         if (subscription.getEndedIn().isBefore(LocalDateTime.now())) {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
            subscriptionRepository.save(subscription);
         }
      }
   }

   public void renewSubscription(UUID id) {
      Subscription subscription = findSubscriptionById(id);

      LocalDateTime now = LocalDateTime.now();
      LocalDateTime endedIn = subscription.getEndedIn();
      Integer duration = subscription.getPlan().getDuration();
      LocalDateTime newEndDate = endedIn.isAfter(now) ? endedIn.plusDays(duration) : now.plusDays(duration);
      SubscriptionStatus status = subscription.getStatus();

      if (status == SubscriptionStatus.CANCELED) {
         throw new BusinessException("Assinatura não pode ser renovada, pois está cancelada.");
      }

      if (status == SubscriptionStatus.PENDING) {
         subscription.setStartedIn(now);
      }

      subscription.setStatus(SubscriptionStatus.ACTIVE);
      subscription.setRenewedIn(now);
      subscription.setEndedIn(newEndDate);
   }

   private Subscription findSubscriptionById(UUID id) {
      return subscriptionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Assinatura não encontrada com o ID: " + id));
   }
}
