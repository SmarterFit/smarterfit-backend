package com.smarterfit.modules.useraccess.mapper;

import com.smarterfit.common.exceptions.ResourceNotFoundException;
import com.smarterfit.common.mapper.GenericMapper;
import com.smarterfit.modules.useraccess.dto.request.profilemetric.CreateProfileMetricRequestDTO;
import com.smarterfit.modules.useraccess.dto.response.ProfileMetricResponseDTO;
import com.smarterfit.modules.useraccess.entity.ProfileMetric;
import com.smarterfit.modules.useraccess.entity.Profile;

public class ProfileMetricMapper {
   private ProfileMetricMapper() {
      // Private constructor to prevent instantiation
   }

   public static ProfileMetric toEntity(CreateProfileMetricRequestDTO dto, Profile profile) {
      return toEntity(dto, profile, new ProfileMetric());
   }

   public static ProfileMetric toEntity(CreateProfileMetricRequestDTO dto, Profile profile,
         ProfileMetric profileMetric) {
      if (profileMetric == null) {
         throw new ResourceNotFoundException("ProfileMetric not found.");
      }
      if (profile == null) {
         throw new ResourceNotFoundException("Profile not found.");
      }

      profileMetric = GenericMapper.map(dto, profileMetric);
      profileMetric.setProfile(profile);

      return profileMetric;
   }

   public static ProfileMetricResponseDTO toResponse(ProfileMetric profileMetric) {
      if (profileMetric == null) {
         throw new ResourceNotFoundException("ProfileMetric not found.");
      }

      return GenericMapper.map(profileMetric, ProfileMetricResponseDTO.class);
   }
}
