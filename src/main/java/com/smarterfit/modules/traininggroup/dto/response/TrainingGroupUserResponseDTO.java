package com.smarterfit.modules.traininggroup.dto.response;

import com.smarterfit.modules.useraccess.dto.response.UserResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TrainingGroupUserResponseDTO {
      private UserResponseDTO user;
      private Boolean isAdmin;
      private Integer points;
}
