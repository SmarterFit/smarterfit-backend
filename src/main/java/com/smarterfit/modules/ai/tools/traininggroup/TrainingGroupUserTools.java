package com.smarterfit.modules.ai.tools.traininggroup;

import java.util.List;
import java.util.UUID;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupResponseDTO;
import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupUserResponseDTO;
import com.smarterfit.modules.traininggroup.service.TrainingGroupUserService;

@Component
public class TrainingGroupUserTools {
   private final TrainingGroupUserService trainingGroupUserService;

   public TrainingGroupUserTools(TrainingGroupUserService trainingGroupUserService) {
      this.trainingGroupUserService = trainingGroupUserService;
   }

   @Tool(description = "Pegar todas os participantes de um grupo de treinamento.")
   public List<TrainingGroupUserResponseDTO> getAllUsersByTrainingGroupId(
         @ToolParam(description = "ID do grupo de treinamento.") UUID groupId) {
      return trainingGroupUserService.getAllUsersByTrainingGroupId(groupId);
   }

   @Tool(description = "Pegar um participante de um grupo de treinamento.")
   public TrainingGroupUserResponseDTO getTrainingGroupUser(
         @ToolParam(description = "ID do grupo de treinamento.") UUID groupId,
         @ToolParam(description = "ID do usuário.") UUID userId) {
      return trainingGroupUserService.getTrainingGroupUser(groupId, userId);
   }

   @Tool(description = "Pegar todos os grupos de treinamento de um usuário.")
   public List<TrainingGroupResponseDTO> getAllTrainingGroupsByUserId(
         @ToolParam(description = "ID do usuário.") UUID userId) {
      return trainingGroupUserService.getAllTrainingGroupsByUserId(userId);
   }

   @Tool(description = "Pegar o rank de um grupo de treinamento.")
   public List<TrainingGroupUserResponseDTO> getRankByTrainingGroupId(
         @ToolParam(description = "ID do grupo de treinamento.") UUID groupId) {
      return trainingGroupUserService.getRankByTrainingGroupId(groupId);
   }
}
