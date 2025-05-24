package com.smarterfit.modules.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class TrainingClientConfig {

    @Value("classpath:prompts/smarterfit-training-system.txt")
    private Resource trainingPrompt;

    public TrainingClientConfig(){}

    private String loadPrompt(Resource resource) throws IOException {
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }


    @Bean
    public ChatClient trainingChatClient(ChatClient.Builder builder) throws IOException {
        return builder
                .defaultSystem(loadPrompt(trainingPrompt))
                .build();
    }
}
