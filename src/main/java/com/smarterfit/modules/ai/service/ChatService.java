package com.smarterfit.modules.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smarterfit.modules.ai.tools.PlanTools;

import reactor.core.publisher.Flux;

@Service
public class ChatService {
   private ChatClient chatClient;
   private PlanTools planTools;

   @Autowired
   public ChatService(ChatClient chatClient, PlanTools planTools) {
      this.chatClient = chatClient;
      this.planTools = planTools;
   }

   public Flux<String> askGroq(String userInput) {
      return chatClient.prompt()
            .user(userInput)
            .tools(planTools)
            .stream()
            .content();
   }
}
