package com.smarterfit.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.dto.request.PaymentProcessorRequestDTO;
import com.smarterfit.dto.request.PaymentRequestDTO;
import com.smarterfit.dto.response.PaymentProcessorResponseDTO;
import com.smarterfit.dto.response.PaymentResponseDTO;
import com.smarterfit.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagamentos")
public class PaymentController {
   private final PaymentService paymentService;

   @Autowired
   public PaymentController(PaymentService paymentService) {
      this.paymentService = paymentService;
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PostMapping
   public ResponseEntity<PaymentResponseDTO> createPayment(@RequestBody @Valid PaymentRequestDTO paymentRequestDTO) {
      PaymentResponseDTO paymentResponseDTO = paymentService.createPayment(paymentRequestDTO);
      return ResponseEntity.status(201).body(paymentResponseDTO);
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PatchMapping("/{id}/confirmar")
   public ResponseEntity<PaymentProcessorResponseDTO> confirmPayment(@PathVariable UUID id,
         @RequestBody @Valid PaymentProcessorRequestDTO paymentProcessorRequestDTO) {
      PaymentProcessorResponseDTO paymentProcessorResponseDTO = paymentService.confirmPayment(id,
            paymentProcessorRequestDTO);
      return ResponseEntity.ok(paymentProcessorResponseDTO);
   }

   /// Acesso: Dono da assinatura ou funcionários
   @PatchMapping("/{id}/cancelar")
   public ResponseEntity<Void> cancelPayment(@PathVariable UUID id) {
      paymentService.cancelPayment(id);
      return ResponseEntity.noContent().build();
   }
}
