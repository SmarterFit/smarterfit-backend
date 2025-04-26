package com.smarterfit.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smarterfit.dto.request.payment.PaymentDTO;
import com.smarterfit.dto.request.payment.ProcessorDTO;
import com.smarterfit.dto.request.payment.SearchDTO;
import com.smarterfit.dto.response.payment.PaymentProcessorResponseDTO;
import com.smarterfit.dto.response.payment.PaymentResponseDTO;
import com.smarterfit.dto.response.payment.PaymentWithSubscriptionResponseDTO;
import com.smarterfit.enums.PaymentMethod;
import com.smarterfit.enums.PaymentStatus;
import com.smarterfit.exception.BusinessException;
import com.smarterfit.model.SubscriptionUser.Subscription;
import com.smarterfit.processor.PaymentProcessor;
import com.smarterfit.repository.PaymentRepository;
import com.smarterfit.specification.PaymentSpecification;
import com.smarterfit.model.SubscriptionUser.Payment;
import com.smarterfit.util.mapper.PaymentMapper;
import com.smarterfit.util.validation.PaymentValidation;
import com.smarterfit.util.validation.SubscriptionValidation;

@Service
public class PaymentService {
   private final PaymentRepository paymentRepository;
   private final PaymentValidation paymentValidation;
   private final SubscriptionValidation subscriptionValidation;
   private final SubscriptionService subscriptionService;
   private final Map<PaymentMethod, PaymentProcessor> paymentProcessors;

   @Autowired
   public PaymentService(PaymentRepository paymentRepository, PaymentValidation paymentValidation,
         SubscriptionValidation subscriptionValidation, SubscriptionService subscriptionService,
         List<PaymentProcessor> paymentProcessors) {
      this.paymentRepository = paymentRepository;
      this.paymentValidation = paymentValidation;
      this.subscriptionValidation = subscriptionValidation;
      this.subscriptionService = subscriptionService;
      this.paymentProcessors = paymentProcessors.stream()
            .collect(Collectors.toMap(PaymentProcessor::getPaymentMethod, processor -> processor));
   }

   @Transactional
   public PaymentResponseDTO createPayment(PaymentDTO paymentDTO) {
      Subscription subscription = subscriptionValidation
            .findSubscriptionById(paymentDTO.subscriptionId());

      Payment payment = PaymentMapper.toEntity(subscription, paymentDTO);

      paymentRepository.save(payment);

      return PaymentMapper.toResponse(payment);
   }

   @Transactional(readOnly = true)
   public PaymentResponseDTO getPaymentById(UUID id) {
      Payment payment = paymentValidation.findPaymentById(id);

      return PaymentMapper.toResponse(payment);
   }

   @Transactional(readOnly = true)
   public Page<PaymentWithSubscriptionResponseDTO> getAllBySubscriptionOwnerId(UUID subscriptionOwnerId,
         Pageable pageable) {
      Page<Payment> payments = paymentRepository.findBySubscriptionOwnerId(subscriptionOwnerId, pageable);

      return payments.map(PaymentMapper::toResponseWithSubscription);
   }

   @Transactional(readOnly = true)
   public Page<PaymentResponseDTO> searchPayments(SearchDTO searchDTO, Pageable pageable) {
      Specification<Payment> specification = PaymentSpecification.searchByFilters(searchDTO);

      Page<Payment> payments = paymentRepository.findAll(specification, pageable);

      return payments.map(PaymentMapper::toResponse);
   }

   public PaymentProcessorResponseDTO processPayment(UUID id, ProcessorDTO processorDTO) {
      Payment payment = paymentValidation.findPaymentById(id);

      if (payment.getStatus() != PaymentStatus.PENDING) {
         throw new BusinessException("Payment does not have a pending status");
      }

      PaymentProcessor paymentProcessor = paymentProcessors.get(payment.getPaymentMethod());
      PaymentProcessorResponseDTO response = paymentProcessor.processPayment(processorDTO);

      if (response.success()) {
         payment.setStatus(PaymentStatus.PAID);
         payment.setPaymentDate(LocalDateTime.now());
         paymentRepository.save(payment);

         subscriptionService.renewSubscription(payment.getSubscription().getId());

         return response;
      } else {
         payment.setStatus(PaymentStatus.FAILED);
         paymentRepository.save(payment);
         throw new BusinessException("Payment failed: " + response.message());
      }
   }

   @Transactional
   public void cancelPayment(UUID id) {
      Payment payment = paymentValidation.findPaymentById(id);

      if (payment.getStatus() != PaymentStatus.PENDING) {
         throw new BusinessException("Pagamento não pode ser cancelado, pois já foi processado");
      }

      payment.setStatus(PaymentStatus.CANCELED);
      paymentRepository.save(payment);
   }
}
