package com.smarterfit.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.dto.request.SubscriptionByStatusRequestDTO;
import com.smarterfit.dto.request.SubscriptionRequestDTO;
import com.smarterfit.dto.request.SubscriptionUserRequestDTO;
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
         @RequestBody @Valid SubscriptionRequestDTO subscriptionRequestDTO) {
      SubscriptionResponseDTO responseDTO = subscriptionService.createSubscription(subscriptionRequestDTO);
      return ResponseEntity.status(201).body(responseDTO);
   }

   /// Acesso: Usuário dono e Funcionários
   @GetMapping("/{id}")
   public ResponseEntity<SubscriptionResponseDTO> getSubscriptionById(
         @PathVariable("id") UUID id) {
      SubscriptionResponseDTO responseDTO = subscriptionService.getSubscriptionById(id);
      return ResponseEntity.ok(responseDTO);
   }

   /// Acesso: Funcionários
   @GetMapping("/status")
   public ResponseEntity<List<SubscriptionResponseDTO>> getSubscriptionsByStatus(
         @RequestBody SubscriptionByStatusRequestDTO statusRequestDTO) {
      List<SubscriptionResponseDTO> subscriptions = subscriptionService.getSubscriptionsByStatus(statusRequestDTO);
      return ResponseEntity.ok(subscriptions);
   }

   /// Acesso: Usuário dono e Funcionários
   @PatchMapping("/{id}/adicionar-usuario")
   public ResponseEntity<SubscriptionResponseDTO> addMemberToSubscription(
         @PathVariable("id") UUID id,
         @RequestBody @Valid SubscriptionUserRequestDTO subscriptionUserRequestDTO) {
      SubscriptionResponseDTO responseDTO = subscriptionService.addMemberToSubscription(id, subscriptionUserRequestDTO);
      return ResponseEntity.ok(responseDTO);
   }

   /// Acesso: Usuário dono e Funcionários
   @PatchMapping("/{id}/remover-usuario")
   public ResponseEntity<SubscriptionResponseDTO> removeMemberFromSubscription(
         @PathVariable("id") UUID id,
         @RequestBody @Valid SubscriptionUserRequestDTO subscriptionUserRequestDTO) {
      SubscriptionResponseDTO responseDTO = subscriptionService.removeMemberFromSubscription(id,
            subscriptionUserRequestDTO);
      return ResponseEntity.ok(responseDTO);
   }

   /// Acesso: Usuário dono e Funcionários
   @PatchMapping("/{id}/cancelar")
   public ResponseEntity<Void> cancelSubscription(@PathVariable("id") UUID id) {
      subscriptionService.cancelSubscription(id);
      return ResponseEntity.noContent().build();
   }
}
