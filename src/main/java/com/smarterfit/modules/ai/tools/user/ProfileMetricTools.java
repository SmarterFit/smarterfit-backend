package com.smarterfit.modules.ai.tools.user;

import java.util.List;
import java.util.UUID;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.smarterfit.common.enums.ProfileMetricType;
import com.smarterfit.modules.useraccess.dto.response.ProfileMetricResponseDTO;
import com.smarterfit.modules.useraccess.service.ProfileMetricService;

@Component
public class ProfileMetricTools {
   private final ProfileMetricService profileMetricService;

   public ProfileMetricTools(ProfileMetricService profileMetricService) {
      this.profileMetricService = profileMetricService;
   }

   @Tool(description = "Pegar as últimas métricas de um perfil.")
   public List<ProfileMetricResponseDTO> getLastsProfileMetricByProfileId(
         @ToolParam(description = "ID do perfil.") UUID profileId) {
      return profileMetricService.getLastsProfileMetricByProfileId(profileId);
   }

   @Tool(description = "Pegar todas as métricas de um tipo e de um perfil.")
   public List<ProfileMetricResponseDTO> getAllProfileMetricByProfileIdAndType(
         @ToolParam(description = "ID do perfil.") UUID profileId,
         @ToolParam(description = "Tipo da métrica.") ProfileMetricType type) {
      return profileMetricService.getProfileMetricsByProfileIdAndType(profileId, type);
   }

   @Tool(description = "Pegar todas as métricas de um perfil.")
   public List<ProfileMetricResponseDTO> getAllProfileMetricByProfileId(
         @ToolParam(description = "ID do perfil.") UUID profileId) {
      return profileMetricService.getProfileMetricsByProfileId(profileId);
   }
}
