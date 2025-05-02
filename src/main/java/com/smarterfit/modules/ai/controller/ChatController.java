package com.smarterfit.modules.ai.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.ai.dto.request.ChatRequestDTO;
import com.smarterfit.modules.ai.service.ChatService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/chat")
@CrossOrigin
public class ChatController {
   private final ChatService chatService;

   @Autowired
   public ChatController(ChatService chatService) {
      this.chatService = chatService;
   }

   @PostMapping("/ask")
   public ResponseEntity<ChatResponse> askGroq(@RequestBody ChatRequestDTO requestDTO) {
      System.out.println("Recebido: " + requestDTO.prompt());
      return ResponseEntity.ok().body(chatService.askGroq(requestDTO.prompt()));
   }
}
