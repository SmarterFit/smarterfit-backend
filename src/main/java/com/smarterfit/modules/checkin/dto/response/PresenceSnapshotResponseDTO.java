package com.smarterfit.modules.checkin.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class PresenceSnapshotResponseDTO {
   private UUID id;
   private Integer presenceCount;
   private LocalDateTime createdAt;
}
