package com.smarterfit.modules.classgroup.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/// TODO: Retornar horários agendados?

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ClassGroupResponseDTO {
    private UUID id;
    private String title;
    private Integer capacity;
    private Integer totalMembers;
    private String description;
    private ModalityResponseDTO modalityDTO;
    private LocalDate startDate;
    private LocalDate endDate;
    private String nameCreator;
    private List<ClassGroupScheduleResponseDTO> schedulesDTO;
    
}
