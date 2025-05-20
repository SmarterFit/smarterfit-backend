package com.smarterfit.modules.checkin.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.checkin.dto.response.PresenceSnapshotResponseDTO;
import com.smarterfit.modules.checkin.entity.PresenceSnapshot;

public class PresenceSnapshotMapper {
   private PresenceSnapshotMapper() {
      // Private constructor to prevent instantiation
   }

   public static PresenceSnapshotResponseDTO toResponse(PresenceSnapshot presenceSnapshot) {
      if (presenceSnapshot == null) {
         throw new ResourceNotFoundException("PresenceSnapshot cannot be null");
      }

      return GenericMapper.map(presenceSnapshot, PresenceSnapshotResponseDTO.class);
   }
}
