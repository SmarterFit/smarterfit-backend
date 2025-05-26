package com.smarterfit.modules.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class ChatService {
   private final ChatClient chatClient;

   @Autowired
   public ChatService(@Qualifier("gymChatClient") ChatClient chatClient) {
      this.chatClient = chatClient;
   }
//
//   public Flux<String> askGroq(String userInput, UUID requesterId) {
//      return chatClient.prompt()
//            .user(userInput)
//              .advisors(a -> {
//                 a.param("userId", requesterId.toString());
//              })
//            .stream()
//            .content();
//   }

    public Flux<String> askGroq(String userInput, UUID requesterId) {
        String userContext = String.format("""
        O usuário que está interagindo tem o ID: %s.
        Use este ID apenas se a pergunta estiver relacionada a dados pessoais dele.
        Caso contrário, ignore essa informação.
        """, requesterId);

        return chatClient.prompt()
                .system(userContext.concat(userContext))
                .user(userInput)
                .stream()
                .content();
    }
}
