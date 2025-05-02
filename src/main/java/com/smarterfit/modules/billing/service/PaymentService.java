package com.smarterfit.modules.billing.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.common.config.BusinessRules;
import com.smarterfit.common.enums.PaymentMethod;
import com.smarterfit.common.enums.PaymentStatus;
import com.smarterfit.common.exceptions.BusinessException;
import com.smarterfit.modules.billing.dto.request.payment.CreatePaymentRequestDTO;
import com.smarterfit.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.smarterfit.modules.billing.dto.request.payment.SearchPaymentRequestDTO;
import com.smarterfit.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;
import com.smarterfit.modules.billing.dto.response.payment.PaymentResponseDTO;
import com.smarterfit.modules.billing.dto.response.payment.PaymentWithSubscriptionResponseDTO;
import com.smarterfit.modules.billing.entity.Payment;
import com.smarterfit.modules.billing.entity.Subscription;
import com.smarterfit.modules.billing.event.PaymentConfirmedEvent;
import com.smarterfit.modules.billing.mapper.PaymentMapper;
import com.smarterfit.modules.billing.processor.PaymentProcessor;
import com.smarterfit.modules.billing.repository.PaymentRepository;
import com.smarterfit.modules.billing.specification.PaymentSpecifications;
import com.smarterfit.modules.billing.validation.PaymentValidation;
import com.smarterfit.modules.billing.validation.SubscriptionValidation;

@Service
public class PaymentService {
   private final PaymentRepository paymentRepository;
   private final PaymentValidation paymentValidation;
   private final SubscriptionValidation subscriptionValidation;
   private final Map<PaymentMethod, PaymentProcessor> paymentProcessors;
   private final ApplicationEventPublisher publisher;

   @Autowired
   public PaymentService(PaymentRepository paymentRepository,
         PaymentValidation paymentValidation,
         List<PaymentProcessor> paymentProcessors,
         SubscriptionValidation subscriptionValidation, ApplicationEventPublisher publisher) {
      this.paymentRepository = paymentRepository;
      this.paymentValidation = paymentValidation;
      this.subscriptionValidation = subscriptionValidation;
      this.publisher = publisher;

      this.paymentProcessors = paymentProcessors.stream()
            .collect(Collectors.toMap(PaymentProcessor::getPaymentMethod, processor -> processor));
   }

   @Transactional
   public PaymentResponseDTO createPayment(CreatePaymentRequestDTO requestDTO) {
      Subscription subscription = subscriptionValidation
            .validateSubscriptionById(requestDTO.getSubscriptionId());

      paymentValidation.validateNotHasPendingPaymentForSubscription(subscription);

      Payment payment = PaymentMapper.toEntity(requestDTO, subscription);
      payment.setExpirationIn(LocalDateTime.now().plusDays(BusinessRules.PAYMENT_EXPIRATION_DAYS));

      paymentRepository.save(payment);

      return PaymentMapper.toResponse(payment);
   }

   @Transactional(readOnly = true)
   public PaymentResponseDTO getPaymentById(UUID id) {
      Payment payment = paymentValidation.validatePaymentById(id);

      return PaymentMapper.toResponse(payment);
   }

   @Transactional(readOnly = true)
   public List<PaymentResponseDTO> getAll() {
      return paymentRepository.findAll().stream().map(PaymentMapper::toResponse).toList();
   }

   @Transactional(readOnly = true)
   public List<PaymentWithSubscriptionResponseDTO> getAllBySubscriptionOwnerId(UUID subscriptionOwnerId) {
      List<Payment> payments = paymentRepository.findBySubscriptionOwnerId(subscriptionOwnerId);

      return payments.stream().map(PaymentMapper::toResponseWithSubscription).toList();
   }

   @Transactional(readOnly = true)
   public Page<PaymentResponseDTO> searchPayments(SearchPaymentRequestDTO requestDTO, Pageable pageable) {
      Specification<Payment> specification = PaymentSpecifications.searchByFilters(requestDTO);

      Page<Payment> payments = paymentRepository.findAll(specification, pageable);

      return payments.map(PaymentMapper::toResponse);
   }

   @Transactional
   public PaymentProcessorResponseDTO processPayment(UUID id, ProcessorPaymentRequestDTO requestDTO) {
      Payment payment = paymentValidation.validatePaymentById(id);
      Subscription subscription = payment.getSubscription();

      paymentValidation.validatePaymentIsPending(payment);
      paymentValidation.validatePaymentNotExpired(payment);
      subscriptionValidation.validateSubscriptionNotIsCanceled(subscription);

      PaymentProcessor paymentProcessor = paymentProcessors.get(payment.getMethod());
      PaymentProcessorResponseDTO response = paymentProcessor.processPayment(requestDTO);

      if (response.getSuccess()) {
         payment.setStatus(PaymentStatus.PAID);
         payment.setPaymentDate(LocalDateTime.now());
         paymentRepository.save(payment);

         publisher.publishEvent(new PaymentConfirmedEvent(subscription));

         return response;
      } else {
         payment.setStatus(PaymentStatus.FAILED);
         paymentRepository.save(payment);
         throw new BusinessException("Payment failed: " + response.getMessage());
      }
   }

   @Transactional
   public void cancelPayment(UUID id) {
      Payment payment = paymentValidation.validatePaymentById(id);

      paymentValidation.validatePaymentIsPending(payment);

      payment.setStatus(PaymentStatus.CANCELED);
      paymentRepository.save(payment);
   }

   @Transactional
   public void expirePaymentsIfNeeded() {
      List<Payment> payments = paymentRepository.findByStatusAndExpirationInBefore(PaymentStatus.PENDING,
            LocalDateTime.now());

      payments.forEach(payment -> payment.setStatus(PaymentStatus.EXPIRED));

      paymentRepository.saveAll(payments);
   }

   @Transactional
   public void cancelPaymentsBySubscription(UUID subscriptionId) {
      paymentRepository.updateStatusBySubscriptionId(subscriptionId, PaymentStatus.CANCELED, PaymentStatus.PENDING);
   }

   @Transactional
   public void cancelPaymentsByPlan(UUID planId) {
      paymentRepository.updateStatusByPlanId(planId, PaymentStatus.CANCELED, PaymentStatus.PENDING);
   }
}
