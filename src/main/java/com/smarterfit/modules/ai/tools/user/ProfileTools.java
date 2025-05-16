package com.smarterfit.modules.ai.tools.user;

import java.util.UUID;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.useraccess.dto.response.ProfileResponseDTO;
import com.smarterfit.modules.useraccess.service.ProfileService;

@Component
public class ProfileTools {
   private final ProfileService profileService;

   public ProfileTools(ProfileService profileService) {
      this.profileService = profileService;
   }

   @Tool(description = "Buscar perfil com base no ID.")
   public ProfileResponseDTO getProfileById(@ToolParam(description = "ID do perfil.") UUID id) {      
      return profileService.getProfileById(id);
   }
}
