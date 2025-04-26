package com.smarterfit.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.dto.request.payment.PaymentDTO;
import com.smarterfit.dto.request.payment.ProcessorDTO;
import com.smarterfit.dto.request.payment.SearchDTO;
import com.smarterfit.dto.response.payment.PaymentProcessorResponseDTO;
import com.smarterfit.dto.response.payment.PaymentResponseDTO;
import com.smarterfit.dto.response.payment.PaymentWithSubscriptionResponseDTO;
import com.smarterfit.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin
public class PaymentController {
   private final PaymentService paymentService;

   @Autowired
   public PaymentController(PaymentService paymentService) {
      this.paymentService = paymentService;
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PostMapping
   public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody @Valid PaymentDTO paymentDTO) {
      PaymentResponseDTO paymentResponseDTO = paymentService.createPayment(paymentDTO);
      return ResponseEntity.status(201).body(paymentResponseDTO);
   }

   /// Acesso: Dono da assinatura ou funcionários
   @GetMapping("/{id}")
   public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable("id") UUID id) {
      return ResponseEntity.ok(paymentService.getPaymentById(id));
   }

   /// Acesso: Dono da assinatura ou funcionários
   @GetMapping("/usuario/{subscriptionOwnerId}")
   public ResponseEntity<Page<PaymentWithSubscriptionResponseDTO>> getAllBySubscriptionOwnerId(
         @PathVariable("subscriptionOwnerId") UUID subscriptionOwnerId,
         Pageable pageable) {
      Page<PaymentWithSubscriptionResponseDTO> page = paymentService
            .getAllBySubscriptionOwnerId(subscriptionOwnerId, pageable);
      return ResponseEntity.ok(page);
   }

   /// Dono da assinatura ou funcionários
   @GetMapping("/search")
   public ResponseEntity<Page<PaymentResponseDTO>> searchPayments(@ModelAttribute SearchDTO searchDTO, Pageable pageable) {
      return ResponseEntity.ok(paymentService.searchPayments(searchDTO, pageable));
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PatchMapping("/{id}/processar")
   public ResponseEntity<PaymentProcessorResponseDTO> processPayment(@PathVariable UUID id,
         @RequestBody @Valid ProcessorDTO processorDTO) {
      PaymentProcessorResponseDTO paymentProcessorResponseDTO = paymentService.processPayment(id,
            processorDTO);
      return ResponseEntity.ok(paymentProcessorResponseDTO);
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PatchMapping("/{id}/cancelar")
   public ResponseEntity<Void> cancelPayment(@PathVariable UUID id) {
      paymentService.cancelPayment(id);
      return ResponseEntity.noContent().build();
   }
}
