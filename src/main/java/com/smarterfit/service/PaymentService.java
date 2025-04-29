package com.smarterfit.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.dto.request.PaymentProcessorRequestDTO;
import com.smarterfit.dto.request.PaymentRequestDTO;
import com.smarterfit.dto.response.PaymentProcessorResponseDTO;
import com.smarterfit.dto.response.PaymentResponseDTO;
import com.smarterfit.enums.PaymentMethod;
import com.smarterfit.enums.PaymentStatus;
import com.smarterfit.enums.SubscriptionStatus;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.exception.ResourceNotFoundException;
import com.smarterfit.model.SubscriptionUser.Subscription;
import com.smarterfit.processor.PaymentProcessor;
import com.smarterfit.model.SubscriptionUser.Payment;
import com.smarterfit.repository.SubscriptionPaymentRepository;
import com.smarterfit.util.mapper.PaymentMapper;
import com.smarterfit.util.validation.SubscriptionValidation;

@Service
public class PaymentService {
   private final SubscriptionPaymentRepository subscriptionPaymentRepository;
   private final SubscriptionValidation subscriptionValidation;
   private final SubscriptionService subscriptionService;
   private final Map<PaymentMethod, PaymentProcessor> paymentProcessors;

   @Autowired
   public PaymentService(SubscriptionPaymentRepository subscriptionPaymentRepository,
         SubscriptionValidation subscriptionValidation, SubscriptionService subscriptionService,
         List<PaymentProcessor> paymentProcessors) {
      this.subscriptionPaymentRepository = subscriptionPaymentRepository;
      this.subscriptionValidation = subscriptionValidation;
      this.subscriptionService = subscriptionService;
      this.paymentProcessors = paymentProcessors.stream()
            .collect(Collectors.toMap(PaymentProcessor::getPaymentMethod, processor -> processor));
   }

   @Transactional
   public PaymentResponseDTO createPayment(PaymentRequestDTO subscriptionPaymentRequestDTO) {
      Subscription subscription = subscriptionValidation
            .findSubscriptionById(subscriptionPaymentRequestDTO.subscriptionId());

      Double amount = subscription.getPlan().getPrice();

      Payment subscriptionPayment = Payment.builder()
            .subscription(subscription)
            .amount(amount)
            .paymentMethod(subscriptionPaymentRequestDTO.paymentMethod())
            .status(PaymentStatus.PENDING)
            .build();

      subscriptionPaymentRepository.save(subscriptionPayment);

      return PaymentMapper.toResponse(subscriptionPayment);
   }

   public PaymentProcessorResponseDTO processPayment(UUID id, PaymentProcessorRequestDTO paymentProcessorRequestDTO) {
      Payment subscriptionPayment = findPaymentById(id);

      if (subscriptionPayment.getStatus() != PaymentStatus.PENDING) {
         throw new BusinessException("Pagamento não pode ser processado, pois já foi processado");
      }

      PaymentProcessor paymentProcessor = paymentProcessors.get(subscriptionPayment.getPaymentMethod());
      PaymentProcessorResponseDTO response = paymentProcessor.processPayment(paymentProcessorRequestDTO);

      if (response.success()) {
         subscriptionPayment.setStatus(PaymentStatus.PAID);
         subscriptionPayment.setPaymentDate(LocalDateTime.now());
         subscriptionPaymentRepository.save(subscriptionPayment);

         subscriptionService.renewSubscription(subscriptionPayment.getSubscription().getId());

         return response;
      } else {
         subscriptionPayment.setStatus(PaymentStatus.FAILED);
         subscriptionPaymentRepository.save(subscriptionPayment);
         throw new BusinessException("Pagamento não processado: " + response.message());
      }
   }

   @Transactional
   public void cancelPayment(UUID id) {
      Payment subscriptionPayment = findPaymentById(id);

      if (subscriptionPayment.getStatus() != PaymentStatus.PENDING) {
         throw new BusinessException("Pagamento não pode ser cancelado, pois já foi processado");
      }

      subscriptionPayment.setStatus(PaymentStatus.CANCELED);
      subscriptionPaymentRepository.save(subscriptionPayment);
   }

   private Payment findPaymentById(UUID id) {
      return subscriptionPaymentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado"));
   }
}
