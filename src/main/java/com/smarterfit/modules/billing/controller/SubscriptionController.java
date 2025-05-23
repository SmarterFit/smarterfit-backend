package com.smarterfit.modules.billing.controller;

import java.util.List;
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

import com.smarterfit.modules.billing.dto.request.subscription.SearchSubscriptionRequestDTO;
import com.smarterfit.modules.billing.dto.request.subscription.SubscriptionStatusCountRequestDTO;
import com.smarterfit.modules.billing.dto.response.subscription.SubscriptionResponseDTO;
import com.smarterfit.modules.billing.dto.response.subscription.SubscriptionStatusCountResponseDTO;
import com.smarterfit.modules.billing.dto.request.subscription.CreateSubscriptionRequestDTO;
import com.smarterfit.modules.billing.service.SubscriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/assinaturas")
@CrossOrigin
public class SubscriptionController {
   private final SubscriptionService subscriptionService;

   @Autowired
   public SubscriptionController(SubscriptionService subscriptionService) {
      this.subscriptionService = subscriptionService;
   }

   /// Acesso: Usuários comum e funcionários
   @PostMapping
   public ResponseEntity<SubscriptionResponseDTO> createSubscription(
         @RequestBody @Valid CreateSubscriptionRequestDTO requestDTO) {
      SubscriptionResponseDTO responseDTO = subscriptionService.createSubscription(requestDTO);
      return ResponseEntity.status(201).body(responseDTO);
   }

   /// Acesso: Usuário dono e Funcionários
   @GetMapping("/{id}")
   public ResponseEntity<SubscriptionResponseDTO> getSubscriptionById(
         @PathVariable("id") UUID id) {
      return ResponseEntity.ok(subscriptionService.getSubscriptionById(id));
   }

   /// Acesso: Funcionários
   @GetMapping
   public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptions() {
      return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
   }

   /// Acesso: Usuário dono e Funcionários
   @GetMapping("/usuario/{subscriptionOwnerId}")
   public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptionsByOwnerId(
         @PathVariable("subscriptionOwnerId") UUID subscriptionOwnerId) {
      return ResponseEntity.ok(subscriptionService.getAllSubscriptionsByOwnerId(subscriptionOwnerId));
   }

   /// Acesso: Usuário dono e Funcionários
   @GetMapping("/buscar")
   public ResponseEntity<Page<SubscriptionResponseDTO>> searchSubscriptions(
         @ModelAttribute SearchSubscriptionRequestDTO requestDTO, Pageable pageable) {
      return ResponseEntity.ok(subscriptionService.searchSubscriptions(requestDTO, pageable));
   }

   @GetMapping("/possui-assinatura/{participantId}")
   public ResponseEntity<Boolean> existsCurrentSubscriptionByParticipantId(
         @PathVariable("participantId") UUID participantId) {
      Boolean exists = subscriptionService.existsCurrentSubscriptionByParticipantId(participantId);
      return ResponseEntity.ok(exists);
   }

   /// Acesso: Usuário dono e Funcionários
   @PatchMapping("/{id}/cancelar")
   public ResponseEntity<Void> cancelSubscription(@PathVariable("id") UUID id) {
      subscriptionService.cancelSubscription(id);
      return ResponseEntity.noContent().build();
   }

   /// Acesso: Usuário dono e Funcionários
   @GetMapping("/turma/{id}/usuario/{userId}")
   public ResponseEntity<List<SubscriptionResponseDTO>> getAvailableSubscriptionsByClassGroupAndUser(
         @PathVariable("id") UUID classGroupId,
         @PathVariable("userId") UUID userId) {
      List<SubscriptionResponseDTO> subscriptions = subscriptionService
            .getAvailableSubscriptionsByClassGroupAndUser(classGroupId, userId);
      return ResponseEntity.ok(subscriptions);
   }

   @PostMapping("/contagem-por-status")
   public ResponseEntity<SubscriptionStatusCountResponseDTO> getStatusCounts(
         @Valid @RequestBody SubscriptionStatusCountRequestDTO request) {
      return ResponseEntity.ok(subscriptionService.getStatusCounts(request));
   }
}
