package com.smarterfit.modules.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
   private ChatClient chatClient;

   @Autowired
   public ChatService(ChatClient.Builder chatClient) {
      this.chatClient = chatClient.build();
   }

   public ChatResponse askGroq(String userInput) {
      String systemMessage = """
            Você é a LirIA, uma assistente virtual da academia SmarterFit.
            Sua missão é ajudar os seus clientes a resolver suas dúvidas sobre seus planos de treino.
            A SmarterFit é uma academia fundada em 1999 pelo professor de Educação Física Bombom, ela oferece planos de treinamento mensais e anuais, com opções individuais e para a familia.
            Qualquer pergunta que não seja sobre a SmarterFit você deve responder com: "Não posso responder perguntar que não sejam da SmarterFit."
            """;

      System.out.println(systemMessage);
      System.out.println(userInput);

      return chatClient.prompt()
            .system(systemMessage)
            .user(userInput)
            .call()
            .chatResponse();
   }

}
