package com.smarterfit.modules.traininggroup.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.traininggroup.dto.request.CreateTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.request.UpdateTrainingGroupRequestDTO;
import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupResponseDTO;
import com.smarterfit.modules.traininggroup.entity.TrainingGroup;
import com.smarterfit.modules.traininggroup.entity.TrainingGroupUser;
import com.smarterfit.modules.useraccess.entity.User;

public class TrainingGroupMapper {
   private TrainingGroupMapper() {
      // Private constructor to prevent instantiation
   }

   public static TrainingGroup toEntity(CreateTrainingGroupRequestDTO trainingGroupDTO, User user) {
      return toEntity(trainingGroupDTO, user, new TrainingGroup());
   }

   public static TrainingGroup toEntity(CreateTrainingGroupRequestDTO dto, User user, TrainingGroup trainingGroup) {
      if (trainingGroup == null) {
         throw new ResourceNotFoundException("TrainingGroup not found.");
      }

      trainingGroup = GenericMapper.map(dto, trainingGroup);

      TrainingGroupUser trainingGroupUser = new TrainingGroupUser();
      trainingGroupUser.setUser(user);
      trainingGroupUser.setTrainingGroup(trainingGroup);
      trainingGroupUser.setIsAdmin(true);

      trainingGroup.getParticipants().add(trainingGroupUser);

      return trainingGroup;
   }

   public static TrainingGroup toEntity(UpdateTrainingGroupRequestDTO dto, TrainingGroup trainingGroup) {
      if (trainingGroup == null) {
         throw new ResourceNotFoundException("TrainingGroup not found.");
      }

      return GenericMapper.map(dto, trainingGroup);
   }

   public static TrainingGroupResponseDTO toResponse(TrainingGroup trainingGroup) {
      if (trainingGroup == null) {
         throw new ResourceNotFoundException("TrainingGroup not found.");
      }

      return GenericMapper.map(trainingGroup, TrainingGroupResponseDTO.class);
   }
}
