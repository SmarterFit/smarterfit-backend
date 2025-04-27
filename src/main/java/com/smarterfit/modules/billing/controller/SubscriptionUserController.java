package com.smarterfit.modules.billing.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.billing.dto.response.SubscriptionResponseDTO;
import com.smarterfit.modules.billing.dto.response.subscriptionuser.SubscriptionUserResponseDTO;
import com.smarterfit.modules.billing.service.SubscriptionUserService;
import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

@RestController
@RequestMapping("/assinaturas/usuarios")
@CrossOrigin
public class SubscriptionUserController {
   private final SubscriptionUserService subscriptionUserService;

   @Autowired
   public SubscriptionUserController(SubscriptionUserService subscriptionUserService) {
      this.subscriptionUserService = subscriptionUserService;
   }

   /// Acesso: Usuário dono e Funcionários
   @PostMapping("/{subscriptionId}/usuario/{userId}/adicionar")
   public ResponseEntity<SubscriptionUserResponseDTO> addMemberToSubscription(
         @PathVariable("subscriptionId") UUID subscriptionId,
         @PathVariable("userId") UUID userId) {
      SubscriptionUserResponseDTO responseDTO = subscriptionUserService.addMemberToSubscription(subscriptionId, userId);
      return ResponseEntity.status(201).body(responseDTO);
   }

   /// Acesso: Usuário dono e Funcionários
   @DeleteMapping("/{subscriptionId}/usuario/{userId}/remover")
   public ResponseEntity<Void> removeMemberFromSubscription(
         @PathVariable("subscriptionId") UUID subscriptionId,
         @PathVariable("userId") UUID userId) {
      subscriptionUserService.removeMemberFromSubscription(subscriptionId,
            userId);
      return ResponseEntity.noContent().build();
   }

   /// Acesso: Usuário dono e Funcionários
   @GetMapping("{subscriptionId}/usuario/{userId}")
   public ResponseEntity<SubscriptionUserResponseDTO> getSubscriptionUser(
         @PathVariable("subscriptionId") UUID subscriptionId, @PathVariable("userId") UUID userId) {
      return ResponseEntity.ok(subscriptionUserService.getSubscriptionUser(subscriptionId, userId));
   }

   /// Acesso: Funcionários
   @GetMapping
   public ResponseEntity<List<SubscriptionUserResponseDTO>> getAllSubscriptionUsers() {
      return ResponseEntity.ok(subscriptionUserService.getAllSubscriptionUsers());
   }

   /// Acesso: Usuários participantes e Funcionários
   @GetMapping("/assinatura/{subscriptionId}")
   public ResponseEntity<List<UserResponseDTO>> getAllUsersBySubscriptionId(
         @PathVariable("subscriptionId") UUID subscriptionId) {
      return ResponseEntity.ok(subscriptionUserService.getAllUsersBySubscriptionId(subscriptionId));
   }

   /// Acesso: Usuário logado e Funcionários
   @GetMapping("/usuario/{userId}")
   public ResponseEntity<List<SubscriptionResponseDTO>> getAllSubscriptionsByUserId(
         @PathVariable("userId") UUID userId) {
      return ResponseEntity.ok(subscriptionUserService.getAllSubscriptionsByUserId(userId));
   }
}
