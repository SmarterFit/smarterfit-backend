package com.smarterfit.modules.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarterfit.modules.ai.service.ChatService;

import reactor.core.publisher.Flux;

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
   public Flux<String> askGroq(@RequestBody String prompt) {
      return chatService.askGroq(prompt);
   }
}
