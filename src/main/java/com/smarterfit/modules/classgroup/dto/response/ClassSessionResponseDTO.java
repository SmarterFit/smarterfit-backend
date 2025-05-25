package com.smarterfit.modules.classgroup.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.smarterfit.common.enums.SessionStatus;
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
public class ClassSessionResponseDTO {
        private UUID id;
        private UUID classGroupId;
        private SessionStatus status;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
}
