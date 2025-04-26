package com.smarterfit.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.dto.request.subscription.SearchDTO;
import com.smarterfit.dto.request.subscription.SubscriptionDTO;
import com.smarterfit.dto.response.SubscriptionResponseDTO;
import com.smarterfit.enums.SubscriptionStatus;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.Plan;
import com.smarterfit.model.SubscriptionUser.Subscription;
import com.smarterfit.model.SubscriptionUser.SubscriptionUser;
import com.smarterfit.model.UserRole.User;
import com.smarterfit.repository.SubscriptionRepository;
import com.smarterfit.specification.SubscriptionSpecifications;
import com.smarterfit.util.mapper.SubscriptionMapper;
import com.smarterfit.util.validation.PlanValidation;
import com.smarterfit.util.validation.SubscriptionValidation;
import com.smarterfit.util.validation.UserValidation;

@Service
public class SubscriptionService {
   private final SubscriptionRepository subscriptionRepository;
   private final PlanValidation planValidation;
   private final UserValidation userValidation;
   private final SubscriptionValidation subscriptionValidation;

   @Autowired
   public SubscriptionService(SubscriptionRepository subscriptionRepository, PlanValidation planValidation,
         UserValidation userValidation, SubscriptionValidation subscriptionValidation) {
      this.subscriptionRepository = subscriptionRepository;
      this.planValidation = planValidation;
      this.userValidation = userValidation;
      this.subscriptionValidation = subscriptionValidation;
   }

   @Transactional
   public SubscriptionResponseDTO createSubscription(SubscriptionDTO subscriptionDTO) {
      Plan plan = planValidation.findPlanById(subscriptionDTO.planId());
      User user = userValidation.validateUserById(subscriptionDTO.ownerId());

      Subscription subscription = SubscriptionMapper.toEntity(user, plan, subscriptionDTO);

      subscriptionRepository.save(subscription);

      return SubscriptionMapper.toResponse(subscription);
   }

   @Transactional(readOnly = true)
   public SubscriptionResponseDTO getSubscriptionById(UUID id) {
      Subscription subscription = subscriptionValidation.findSubscriptionById(id);
      return SubscriptionMapper.toResponse(subscription);
   }

   @Transactional(readOnly = true)
   public List<SubscriptionResponseDTO> getAllSubscriptions() {
      List<Subscription> subscriptions = subscriptionRepository.findAll();
      return subscriptions.stream()
            .map(SubscriptionMapper::toResponse)
            .collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
   public Page<SubscriptionResponseDTO> searchSubscriptions(SearchDTO searchDTO, Pageable pageable) {
      Specification<Subscription> specification = SubscriptionSpecifications.searchByFilters(searchDTO);

      Page<Subscription> subscriptions = subscriptionRepository
            .findAll(specification, pageable);

      return subscriptions.map(SubscriptionMapper::toResponse);
   }

   @Transactional
   public SubscriptionResponseDTO addMemberToSubscription(UUID id, UUID userId) {
      Subscription subscription = subscriptionValidation.findSubscriptionById(id);

      if (subscription.getAvailableMembers() <= 0) {
         throw new BusinessException("Not enough available members in the subscription.");
      }

      User user = userValidation.validateUserById(userId);

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
         UUID userId) {
      Subscription subscription = subscriptionValidation.findSubscriptionById(id);

      SubscriptionUser subscriptionUser = subscription.getParticipants().stream()
            .filter(participant -> participant.getUser().getId().equals(userId))
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("User not found in the subscription."));

      if (subscriptionUser.getCreatedAt().isAfter(LocalDateTime.now().minusDays(7))) {
         throw new BusinessException("User cannot be removed from the subscription within 7 days of joining.");
      }

      subscription.getParticipants().remove(subscriptionUser);
      subscription.setAvailableMembers(subscription.getAvailableMembers() + 1);

      subscriptionRepository.save(subscription);

      return SubscriptionMapper.toResponse(subscription);
   }

   @Transactional
   public void cancelSubscription(UUID id) {
      Subscription subscription = subscriptionValidation.findSubscriptionById(id);
      subscription.setStatus(SubscriptionStatus.CANCELED);
      subscriptionRepository.save(subscription);
   }

   @Transactional
   public void expireSubscriptionsIfNeeded() {
      LocalDateTime now = LocalDateTime.now();

      List<Subscription> subscriptions = subscriptionRepository.findByStatusAndEndedInBefore(
            SubscriptionStatus.ACTIVE,
            now);

      subscriptions.forEach(subscription -> {
         subscription.setStatus(SubscriptionStatus.EXPIRED);
      });

      subscriptionRepository.saveAll(subscriptions);
   }

   public void renewSubscription(UUID id) {
      Subscription subscription = subscriptionValidation.findSubscriptionById(id);

      LocalDateTime now = LocalDateTime.now();
      LocalDateTime endedIn = subscription.getEndedIn() != null ? subscription.getEndedIn() : now;
      Integer duration = subscription.getPlan().getDuration();
      LocalDateTime newEndDate = endedIn.isAfter(now) ? endedIn.plusDays(duration) : now.plusDays(duration);
      SubscriptionStatus status = subscription.getStatus();

      if (status == SubscriptionStatus.CANCELED) {
         throw new BusinessException("Subscription is canceled.");
      }

      if (status == SubscriptionStatus.PENDING) {
         subscription.setStartedIn(now);
      }

      subscription.setStatus(SubscriptionStatus.ACTIVE);
      subscription.setRenewedIn(now);
      subscription.setEndedIn(newEndDate);

      subscriptionRepository.save(subscription);
   }
}
