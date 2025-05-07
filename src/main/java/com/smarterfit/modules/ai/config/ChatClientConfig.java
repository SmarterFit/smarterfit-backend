package com.smarterfit.modules.ai.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.smarterfit.modules.ai.tools.ClassTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.smarterfit.modules.ai.tools.PlanTools;

@Configuration
public class ChatClientConfig {

   @Value("classpath:prompts/smarterfit-system.txt")
   private Resource systemPrompt;

   private PlanTools planTools;
   private ClassTools classTools;

   @Autowired
   public ChatClientConfig(PlanTools planTools, ClassTools classTools) {
      this.planTools = planTools;
      this.classTools = classTools;
   }

   @Bean
   public String systemPrompt() throws IOException {
      return new String(systemPrompt.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
   }

   @Bean
   public ChatClient chatClient(ChatClient.Builder chatClient) {
      try {
         chatClient = chatClient.defaultSystem(systemPrompt());
      } catch (IOException e) {
         System.out.println("Error loading system prompt: " + e.getMessage());
      }

      return chatClient
            .defaultTools(classTools)
            .build();
   }
}
