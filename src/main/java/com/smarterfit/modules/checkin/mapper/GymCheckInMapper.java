package com.smarterfit.modules.checkin.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.checkin.dto.request.GymCheckInAndCheckOutRequestDTO;
import com.smarterfit.modules.checkin.dto.response.GymCheckInResponseDTO;
import com.smarterfit.modules.checkin.entity.GymCheckIn;
import com.smarterfit.modules.useraccess.entity.User;

public class GymCheckInMapper {
   private GymCheckInMapper() {
      // Private constructor to prevent instantiation
   }

   public static GymCheckIn toEntity(GymCheckInAndCheckOutRequestDTO dto, User user) {
      return toEntity(dto, user, new GymCheckIn());
   }

   public static GymCheckIn toEntity(GymCheckInAndCheckOutRequestDTO dto, User user, GymCheckIn gymCheckIn) {
      if (dto == null) {
         return null;
      } else if (user == null) {
         throw new ResourceNotFoundException("User cannot be null");
      } else if (gymCheckIn == null) {
         throw new ResourceNotFoundException("GymCheckIn cannot be null");
      }

      gymCheckIn.setUser(user);

      return gymCheckIn;
   }

   public static GymCheckInResponseDTO toResponse(GymCheckIn gymCheckIn) {
      if (gymCheckIn == null) {
         throw new ResourceNotFoundException("GymCheckIn cannot be null");
      }

      return GenericMapper.map(gymCheckIn, GymCheckInResponseDTO.class);
   }
}
