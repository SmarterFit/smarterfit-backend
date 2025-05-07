package com.smarterfit.modules.ai.service;

import com.smarterfit.modules.ai.tools.ClassTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smarterfit.modules.ai.tools.PlanTools;

import reactor.core.publisher.Flux;

@Service
public class ChatService {
   private ChatClient chatClient;

   @Autowired
   public ChatService(ChatClient chatClient, ClassTools classTools) {
      this.chatClient = chatClient;
   }

   public Flux<String> askGroq(String userInput) {
      return chatClient.prompt()
            .user(userInput)
            .stream()
            .content();
   }
}
