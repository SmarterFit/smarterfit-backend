package com.smarterfit.modules.traininggroup.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.traininggroup.dto.response.TrainingGroupUserResponseDTO;
import com.smarterfit.modules.traininggroup.entity.TrainingGroupUser;
import com.smarterfit.modules.useraccess.mapper.UserMapper;

public class TrainingGroupUserMapper {
   private TrainingGroupUserMapper() {
      // Private constructor to prevent instantiation
   }

   public static TrainingGroupUserResponseDTO toResponse(TrainingGroupUser trainingGroupUser) {
      if (trainingGroupUser == null) {
         throw new ResourceNotFoundException("TrainingGroupUser not found.");
      }

      TrainingGroupUserResponseDTO response = GenericMapper.map(trainingGroupUser, TrainingGroupUserResponseDTO.class);
      response = response.toBuilder().user(UserMapper.toResponse(trainingGroupUser.getUser())).build();

      return response;
   }
}
