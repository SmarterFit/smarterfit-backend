package com.smarterfit.modules.billing.service;

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

import com.smarterfit.common.enums.SubscriptionStatus;
import com.smarterfit.modules.billing.dto.request.subscription.SearchSubscriptionRequestDTO;
import com.smarterfit.modules.billing.dto.request.subscription.CreateSubscriptionRequestDTO;
import com.smarterfit.modules.billing.dto.response.SubscriptionResponseDTO;
import com.smarterfit.modules.billing.entity.Plan;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.mapper.SubscriptionMapper;
import com.smarterfit.modules.billing.repository.SubscriptionRepository;
import com.smarterfit.modules.billing.specification.SubscriptionSpecifications;
import com.smarterfit.modules.billing.validation.PlanValidation;
import com.smarterfit.modules.billing.validation.SubscriptionValidation;
import com.smarterfit.modules.useraccess.entity.User;
import com.smarterfit.modules.useraccess.validation.UserValidation;

@Service
public class SubscriptionService {
   private final SubscriptionRepository subscriptionRepository;
   private final PlanValidation planValidation;
   private final UserValidation userValidation;
   private final SubscriptionValidation subscriptionValidation;
   private final PaymentService paymentService;

   @Autowired
   public SubscriptionService(SubscriptionRepository subscriptionRepository, PlanValidation planValidation,
         UserValidation userValidation, SubscriptionValidation subscriptionValidation, PaymentService paymentService) {
      this.subscriptionRepository = subscriptionRepository;
      this.planValidation = planValidation;
      this.userValidation = userValidation;
      this.subscriptionValidation = subscriptionValidation;
      this.paymentService = paymentService;
   }

   @Transactional
   public SubscriptionResponseDTO createSubscription(CreateSubscriptionRequestDTO requestDTO) {
      Plan plan = planValidation.validatePlanById(requestDTO.planId());
      User user = userValidation.validateUserById(requestDTO.ownerId());

      Subscription subscription = SubscriptionMapper.toEntity(requestDTO, user, plan);

      subscriptionRepository.save(subscription);

      return SubscriptionMapper.toResponse(subscription);
   }

   @Transactional(readOnly = true)
   public SubscriptionResponseDTO getSubscriptionById(UUID id) {
      Subscription subscription = subscriptionValidation.validateSubscriptionById(id);
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
   public List<SubscriptionResponseDTO> getAllSubscriptionsByOwnerId(UUID userId) {
      List<Subscription> subscriptions = subscriptionRepository.findByOwnerId(userId);
      return subscriptions.stream()
            .map(SubscriptionMapper::toResponse)
            .collect(Collectors.toList());
   }

   @Transactional(readOnly = true)
   public Page<SubscriptionResponseDTO> searchSubscriptions(SearchSubscriptionRequestDTO searchSubscriptionRequestDTO,
         Pageable pageable) {
      Specification<Subscription> specification = SubscriptionSpecifications
            .searchByFilters(searchSubscriptionRequestDTO);

      Page<Subscription> subscriptions = subscriptionRepository
            .findAll(specification, pageable);

      return subscriptions.map(SubscriptionMapper::toResponse);
   }

   @Transactional
   public void cancelSubscription(UUID id) {
      Subscription subscription = subscriptionValidation.validateSubscriptionById(id);

      paymentService.cancelPaymentsBySubscription(id);

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

   @Transactional
   public void renewSubscription(Subscription subscription) {
      LocalDateTime now = LocalDateTime.now();
      LocalDateTime endedIn = subscription.getEndedIn() != null ? subscription.getEndedIn() : now;
      Integer duration = subscription.getPlan().getDuration();
      LocalDateTime newEndDate = endedIn.isAfter(now) ? endedIn.plusDays(duration) : now.plusDays(duration);
      SubscriptionStatus status = subscription.getStatus();

      subscriptionValidation.validateSubscriptionNotIsCanceled(subscription);

      if (status == SubscriptionStatus.PENDING) {
         subscription.setStartedIn(now);
      }

      subscription.setStatus(SubscriptionStatus.ACTIVE);
      subscription.setRenewedIn(now);
      subscription.setEndedIn(newEndDate);

      subscriptionRepository.save(subscription);
   }

   @Transactional
   public void cancelSubscriptionsByPlan(UUID planId) {
      subscriptionRepository.updateStatusByPlanId(planId, SubscriptionStatus.CANCELED);
   }

   @Transactional
   public void decrementAvailableClasses(Subscription subscription) {
      subscription.setAvailableClasses(subscription.getAvailableClasses() - 1);
      subscriptionRepository.save(subscription);
   }
}
