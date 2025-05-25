package com.smarterfit.modules.ai.tools.traininggroup;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.smarterfit.common.enums.TrainingGroupType;
import com.smarterfit.modules.traininggroup.dto.request.SearchTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupResponseDTO;
import com.smarterfit.modules.traininggroup.service.TrainingGroupService;

@Component
public class TrainingGroupTools {
   private final TrainingGroupService trainingGroupService;

   public TrainingGroupTools(TrainingGroupService trainingGroupService) {
      this.trainingGroupService = trainingGroupService;
   }

   @Tool(description = "Buscar grupos de treinamento. Só preencha os parâmetros que forem explicitamente informados pelo usuário.")
   public List<TrainingGroupResponseDTO> searchTrainingGroups(
         @ToolParam(required = false, description = "Termo presente no nome") String nameTerm) {

      SearchTrainingGroupRequestDTO request = new SearchTrainingGroupRequestDTO();
      request.setNameTerm(nameTerm);
      request.setTypes(List.of(TrainingGroupType.PUBLIC));

      return trainingGroupService.searchTrainingGroups(request, Pageable.unpaged()).getContent();
   }
}
