package com.smarterfit.modules.ai.tools.user;

import java.util.UUID;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;
import com.smarterfit.modules.useraccess.service.UserService;

@Component
public class UserTools {
   private final UserService userService;

   public UserTools(UserService userService) {
      this.userService = userService;
   }

   @Tool(description = "Buscar usuário com base no ID.")
   public UserResponseDTO getUserById(@ToolParam(description = "ID do usuário.") UUID id) {
      return userService.getUserById(id);
   }
}
