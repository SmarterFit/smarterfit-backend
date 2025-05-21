package com.smarterfit.modules.ai.config;

import com.smarterfit.modules.ai.tools.ToolsFaced;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class ChatClientConfig {

   @Value("classpath:prompts/smarterfit-system.txt")
   private Resource systemPrompt;
   private final MessageChatMemoryAdvisor memoryAdvisor;
   private final ToolsFaced toolFaced;

   @Autowired
   public ChatClientConfig(ToolsFaced toolFaced,
                           MessageChatMemoryAdvisor memoryAdvisor) {
      this.toolFaced = toolFaced;
      this.memoryAdvisor = memoryAdvisor;

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
              .defaultTools(toolFaced)
              .defaultAdvisors(memoryAdvisor)
              .build();
   }


}
