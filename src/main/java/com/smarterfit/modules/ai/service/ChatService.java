package com.smarterfit.modules.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class ChatService {
   private final ChatClient chatClient;

   @Autowired
   public ChatService(ChatClient chatClient) {
      this.chatClient = chatClient;
   }

   public Flux<String> askGroq(String userInput, UUID requesterId) {
      return chatClient.prompt()
            .user(userInput)
            .stream()
            .content();
   }
}
