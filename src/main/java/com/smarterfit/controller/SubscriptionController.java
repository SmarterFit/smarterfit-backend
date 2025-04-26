package com.smarterfit.controller;

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

import com.smarterfit.dto.request.subscription.SearchDTO;
import com.smarterfit.dto.request.subscription.SubscriptionDTO;
import com.smarterfit.dto.response.SubscriptionResponseDTO;
import com.smarterfit.service.SubscriptionService;

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
   @PostMapping()
   public ResponseEntity<SubscriptionResponseDTO> createSubscription(
         @RequestBody @Valid SubscriptionDTO subscriptionDTO) {
      SubscriptionResponseDTO responseDTO = subscriptionService.createSubscription(subscriptionDTO);
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
   @GetMapping("/search")
   public ResponseEntity<Page<SubscriptionResponseDTO>> searchSubscriptions(@ModelAttribute SearchDTO searchDTO, Pageable pageable) {
      return ResponseEntity.ok(subscriptionService.searchSubscriptions(searchDTO, pageable));
   }

   /// Acesso: Usuário dono e Funcionários
   @PatchMapping("/{id}/adicionar-usuario/{userId}")
   public ResponseEntity<SubscriptionResponseDTO> addMemberToSubscription(
         @PathVariable("id") UUID id,
         @PathVariable("userId") UUID userId) {
      SubscriptionResponseDTO responseDTO = subscriptionService.addMemberToSubscription(id, userId);
      return ResponseEntity.ok(responseDTO);
   }

   /// Acesso: Usuário dono e Funcionários
   @PatchMapping("/{id}/remover-usuario/{userId}")
   public ResponseEntity<SubscriptionResponseDTO> removeMemberFromSubscription(
         @PathVariable("id") UUID id,
         @PathVariable("userId") UUID userId) {
      SubscriptionResponseDTO responseDTO = subscriptionService.removeMemberFromSubscription(id,
            userId);
      return ResponseEntity.ok(responseDTO);
   }

   /// Acesso: Usuário dono e Funcionários
   @PatchMapping("/{id}/cancelar")
   public ResponseEntity<Void> cancelSubscription(@PathVariable("id") UUID id) {
      subscriptionService.cancelSubscription(id);
      return ResponseEntity.noContent().build();
   }
}
