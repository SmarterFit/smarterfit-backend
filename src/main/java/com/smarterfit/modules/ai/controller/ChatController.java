package com.smarterfit.modules.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.smarterfit.modules.ai.service.ChatService;

import reactor.core.publisher.Flux;

import java.util.UUID;

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
   public Flux<String> askGroq(@RequestBody String prompt,
                              @RequestHeader("X-User-Id") UUID requesterId) {
      return chatService.askGroq(prompt , requesterId);
   }
}
