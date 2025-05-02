package com.smarterfit.modules.ai.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.smarterfit.modules.ai.tools.PlanTools;
import com.smarterfit.modules.billing.service.PlanService;

@Configuration
public class ChatClientConfig {

   @Value("classpath:prompts/smarterfit-system.txt")
   private Resource systemPrompt;

   @Bean
   public String systemPrompt() throws IOException {
      return new String(systemPrompt.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
   }

   @Bean
   public ChatClient chatClient(ChatClient.Builder chatClient, PlanTools planTools) {
      try {
         chatClient = chatClient.defaultSystem(systemPrompt());
      } catch (IOException e) {
         System.out.println("Error loading system prompt: " + e.getMessage());
      }

      return chatClient
            .build();
   }
}
