package com.smarterfit.util.mapper;

import com.smarterfit.dto.request.training_group.TrainingGroupDTO;
import com.smarterfit.dto.request.training_group.UpdateDTO;
import com.smarterfit.dto.response.training_group.TrainingGroupResponseDTO;
import com.smarterfit.dto.response.training_group.TrainingGroupUserResponseDTO;
import com.smarterfit.model.TrainingGroup.TrainingGroup;
import com.smarterfit.model.TrainingGroup.TrainingGroupUser;
import com.smarterfit.model.UserRole.User;

public class TrainingGroupMapper {
   public static TrainingGroup toEntity(User user, TrainingGroupDTO trainingGroupDTO) {
      TrainingGroup trainingGroup = new TrainingGroup();

      trainingGroup.setName(trainingGroupDTO.name());
      trainingGroup.setGroupType(trainingGroupDTO.groupType());
      trainingGroup.setStartDate(trainingGroupDTO.startDate());
      trainingGroup.setEndDate(trainingGroupDTO.endDate());

      TrainingGroupUser trainingGroupUser = new TrainingGroupUser();
      trainingGroupUser.setUser(user);
      trainingGroupUser.setTrainingGroup(trainingGroup);
      trainingGroupUser.setIsAdmin(true);

      trainingGroup.getParticipants().add(trainingGroupUser);

      return trainingGroup;
   }

   public static TrainingGroup toEntity(TrainingGroup trainingGroup, UpdateDTO updateDTO) {
      if (trainingGroup == null) {
         return null;
      }
      trainingGroup.setName(updateDTO.name());
      trainingGroup.setGroupType(updateDTO.groupType());
      trainingGroup.setStartDate(updateDTO.startDate());
      trainingGroup.setEndDate(updateDTO.endDate());

      return trainingGroup;
   }

   public static TrainingGroupResponseDTO toResponse(TrainingGroup trainingGroup) {
      if (trainingGroup == null) {
         return null;
      }

      return new TrainingGroupResponseDTO(
            trainingGroup.getId(),
            trainingGroup.getName(),
            trainingGroup.getGroupType(),
            trainingGroup.getStartDate(),
            trainingGroup.getEndDate());
   }

   public static TrainingGroupUserResponseDTO toResponse(TrainingGroupUser trainingGroupUser) {
      if (trainingGroupUser == null) {
         return null;
      }

      return new TrainingGroupUserResponseDTO(
            UserMapper.toResponse(trainingGroupUser.getUser()),
            trainingGroupUser.getIsAdmin(),
            trainingGroupUser.getPoints());
   }
}
